package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.dao.NoteDao;
import cn.gnaixeuy.redbook.dao.NoteResourcesDao;
import cn.gnaixeuy.redbook.dao.UserOthersNotesAssociatedDao;
import cn.gnaixeuy.redbook.dto.NoteDto;
import cn.gnaixeuy.redbook.entity.Note;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.entity.common.NoteResources;
import cn.gnaixeuy.redbook.entity.common.UserOthersNotesAssociated;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.NoteMapper;
import cn.gnaixeuy.redbook.service.NoteService;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Service
public class NoteServiceImpl extends ServiceImpl<NoteDao, Note> implements NoteService {

    private UserService userService;
    private NoteMapper noteMapper;
    private NoteResourcesDao noteResourcesDao;
    private UserOthersNotesAssociatedDao userOthersNotesAssociatedDao;

    /**
     * 创建新的笔记
     *
     * @param noteCreateRequest 笔记创建对象
     * @return NoteDto
     */
    @Override
    @Transactional
    public NoteDto releaseNote(NoteCreateRequest noteCreateRequest) {
        Note note = this.noteMapper.noteCreateRequst2Entity(noteCreateRequest);
        User author = this.userService.getCurrentUser();
        note.setAuthorId(author.getId());
        int insertResult = this.baseMapper.insert(note);
        if (insertResult != 1) {
            throw new BizException(ExceptionType.NOTE_CREATE_EXCEPTION);
        } else {
            Integer noteId = note.getId();
            noteCreateRequest.getResourceIds().forEach(item -> this.noteResourcesDao.insert(new NoteResources(noteId, item)));
            note = this.baseMapper.selectById(noteId);
            if (note != null) {
                NoteDto noteDto = this.noteMapper.entity2Dto(note);
                noteDto.setAuthor(author);
                return noteDto;
            } else {
                throw new BizException(ExceptionType.NOTE_CREATE_EXCEPTION);
            }
        }
    }

    /**
     * 获取当前用户收藏的笔记信息
     *
     * @return list <NoteDto>
     */
    @Override
    public List<NoteDto> userCollectedNotes() {
        User currentUser = this.userService.getCurrentUser();
        List<UserOthersNotesAssociated> userCollectNotes = this.userOthersNotesAssociatedDao.selectList(
                Wrappers
                        .<UserOthersNotesAssociated>lambdaQuery()
                        .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
                        .eq(UserOthersNotesAssociated::getIsCollected, true)
        );
        List<Note> list = new ArrayList<>();
        userCollectNotes.forEach(item -> list.add(this.baseMapper.selectById(item.getNoteId())));
        return list.stream().map(this.noteMapper::entity2Dto).collect(Collectors.toList());
    }

    /**
     * 获取当前用户点赞的笔记信息
     *
     * @return list <NoteDto>
     */
    @Override
    public List<NoteDto> userLikeNotes() {
        User currentUser = this.userService.getCurrentUser();
        List<UserOthersNotesAssociated> userCollectNotes = this.userOthersNotesAssociatedDao.selectList(
                Wrappers
                        .<UserOthersNotesAssociated>lambdaQuery()
                        .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
                        .eq(UserOthersNotesAssociated::getIsLike, true)
        );
        List<Note> list = new ArrayList<>();
        userCollectNotes.forEach(item -> list.add(this.baseMapper.selectById(item.getNoteId())));
        return list.stream().map(this.noteMapper::entity2Dto).collect(Collectors.toList());
    }

    /**
     * 更新用户是否点赞该笔记
     *
     * @param noteId 笔记id
     * @param isLike 是否喜欢
     * @return 返回是否成功
     */
    @Override
    public boolean updateUserNoteIsLike(Integer noteId, boolean isLike) {
        User currentUser = this.userService.getCurrentUser();
        UserOthersNotesAssociated userOthersNotesAssociated = this.userOthersNotesAssociatedDao.selectOne(
                Wrappers
                        .<UserOthersNotesAssociated>lambdaQuery()
                        .eq(UserOthersNotesAssociated::getNoteId, noteId)
                        .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
        );
        int result;
        if (userOthersNotesAssociated == null) {
            result = this.userOthersNotesAssociatedDao.insert(new UserOthersNotesAssociated(currentUser.getId(), noteId, false, isLike));
        } else {
            userOthersNotesAssociated.setIsCollected(isLike);
            result = this.userOthersNotesAssociatedDao.update(
                    null,
                    Wrappers.<UserOthersNotesAssociated>lambdaUpdate()
                            .set(UserOthersNotesAssociated::getIsLike, isLike)
                            .eq(UserOthersNotesAssociated::getNoteId, noteId)
                            .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
            );
        }
        return result == 1;
    }

    /**
     * 更新用户是否点赞该笔记
     *
     * @param noteId      笔记id
     * @param isCollected 是否收藏
     * @return 返回是否成功
     */
    @Override
    public boolean updateUserNoteIsCollected(Integer noteId, boolean isCollected) {
        User currentUser = this.userService.getCurrentUser();
        UserOthersNotesAssociated userOthersNotesAssociated = this.userOthersNotesAssociatedDao.selectOne(
                Wrappers
                        .<UserOthersNotesAssociated>lambdaQuery()
                        .eq(UserOthersNotesAssociated::getNoteId, noteId)
                        .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
        );
        int result;
        if (userOthersNotesAssociated == null) {
            result = this.userOthersNotesAssociatedDao.insert(new UserOthersNotesAssociated(currentUser.getId(), noteId, isCollected, false));
        } else {
            userOthersNotesAssociated.setIsCollected(isCollected);
            result = this.userOthersNotesAssociatedDao.update(
                    null,
                    Wrappers.<UserOthersNotesAssociated>lambdaUpdate()
                            .set(UserOthersNotesAssociated::getIsCollected, isCollected)
                            .eq(UserOthersNotesAssociated::getNoteId, noteId)
                            .eq(UserOthersNotesAssociated::getUserId, currentUser.getId())
            );
        }
        return result == 1;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNoteMapper(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Autowired
    public void setNoteResourcesDao(NoteResourcesDao noteResourcesDao) {
        this.noteResourcesDao = noteResourcesDao;
    }

    @Autowired
    public void setUserOthersNotesAssociatedDao(UserOthersNotesAssociatedDao userOthersNotesAssociatedDao) {
        this.userOthersNotesAssociatedDao = userOthersNotesAssociatedDao;
    }

}
