package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.NoteMapper;
import cn.gnaixeuy.redbook.service.NoteService;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import cn.gnaixeuy.redbook.vo.NoteVo;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 *
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/10/1
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Slf4j
@RequestMapping(value = {"/notes"})
public class NoteController {

    private NoteService noteService;
    private NoteMapper noteMapper;

    @RolesAllowed(value = {"ROLE_USER"})
    @PostMapping(value = {"/create"})
    public ResponseResult<NoteVo> createNote(@Validated @RequestBody NoteCreateRequest noteCreateRequest) {
        System.out.println(noteCreateRequest);
        return ResponseResult.success(
                this.noteMapper
                        .dto2Vo(
                                this.noteService
                                        .releaseNote(noteCreateRequest)
                        )
        );
    }

    //TODO BUG 无餐默认值错误
    @GetMapping(value = {"/like/{noteId}"})
    public ResponseResult<String> updateNoteLikeStatus(@PathVariable Integer noteId, boolean isLike) {
        //TODO 通知业务
        if (!this.noteService.updateUserNoteIsLike(noteId, isLike)) {
            throw new BizException(ExceptionType.NOTE_LIKE_EXCEPTION);
        }
        return ResponseResult.success("更新成功");
    }

    //TODO BUG 无餐默认值错误
    @GetMapping(value = {"/collect/{noteId}"})
    public ResponseResult<String> updateNoteCollectedStatus(@PathVariable Integer noteId, boolean isCollected) {
        //TODO 通知业务
        if (!this.noteService.updateUserNoteIsCollected(noteId, isCollected)) {
            throw new BizException(ExceptionType.NOTE_COLLECT_EXCEPTION);
        }
        return ResponseResult.success("更新成功");
    }

    @GetMapping(value = {"/collectedNotes"})
    public ResponseResult<List<NoteVo>> getUserCollectedNotes() {
        return ResponseResult.success(
                this.noteService.userCollectedNotes()
                        .stream()
                        .map(this.noteMapper::dto2Vo)
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = {"/likeNotes"})
    public ResponseResult<List<NoteVo>> getUserLikeNotes() {
        return ResponseResult.success(
                this.noteService.userLikeNotes()
                        .stream()
                        .map(this.noteMapper::dto2Vo)
                        .collect(Collectors.toList()));
    }

    @PostMapping(value = {"/comment/{noteId}"})
    public ResponseResult<String> commentNote(@PathVariable(name = "noteId") Integer noteId, @RequestBody String description) {
        log.info("评论:" + description);
        boolean result = this.noteService.addComment2Note(noteId, description);
        if (!result) {
            throw new BizException(ExceptionType.NOTE_CREATE_EXCEPTION);
        }
        return ResponseResult.success("评论成功");
    }


    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @Autowired
    public void setNoteMapper(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
}
