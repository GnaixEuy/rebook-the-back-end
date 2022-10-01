package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.dao.NoteDao;
import cn.gnaixeuy.redbook.dto.NoteDto;
import cn.gnaixeuy.redbook.entity.Note;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.NoteMapper;
import cn.gnaixeuy.redbook.service.NoteService;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 创建新的笔记
     *
     * @param noteCreateRequest 笔记创建对象
     * @return NoteDto
     */
    @Override
    public NoteDto releaseNote(NoteCreateRequest noteCreateRequest) {
        Note note = this.noteMapper.noteCreateRequst2Entity(noteCreateRequest);
        User author = this.userService.getCurrentUser();
        note.setAuthorId(author.getId());
        int insertResult = this.baseMapper.insert(note);
        if (insertResult != 1) {
            throw new BizException(ExceptionType.NOTE_CREATE_EXCEPTION);
        } else {
            note = this.baseMapper.selectById(note.getId());
            if (note != null) {
                NoteDto noteDto = this.noteMapper.entity2Dto(note);
                noteDto.setAuthor(author);
                return noteDto;
            } else {
                throw new BizException(ExceptionType.NOTE_CREATE_EXCEPTION);
            }
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNoteMapper(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
}
