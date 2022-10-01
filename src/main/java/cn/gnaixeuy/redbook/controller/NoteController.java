package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.mapper.NoteMapper;
import cn.gnaixeuy.redbook.service.NoteService;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import cn.gnaixeuy.redbook.vo.NoteVo;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

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

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @Autowired
    public void setNoteMapper(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
}
