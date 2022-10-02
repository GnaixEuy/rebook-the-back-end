package cn.gnaixeuy.redbook.service;

import cn.gnaixeuy.redbook.dto.NoteDto;
import cn.gnaixeuy.redbook.entity.Note;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
public interface NoteService extends IService<Note> {

    /**
     * 创建新的笔记
     *
     * @param noteCreateRequest 笔记创建对象
     * @return NoteDto
     */
    NoteDto releaseNote(NoteCreateRequest noteCreateRequest);

    /**
     * 获取当前用户收藏的笔记信息
     *
     * @return list <NoteDto>
     */
    List<NoteDto> userCollectedNotes();

    /**
     * 获取当前用户点赞的笔记信息
     *
     * @return list <NoteDto>
     */
    List<NoteDto> userLikeNotes();

    /**
     * 更新用户是否点赞该笔记
     *
     * @param noteId 笔记id
     * @param isLike 是否喜欢
     * @return 返回是否成功
     */
    boolean updateUserNoteIsLike(Integer noteId, boolean isLike);

    /**
     * 更新用户是否点赞该笔记
     *
     * @param noteId      笔记id
     * @param isCollected 是否收藏
     * @return 返回是否成功
     */
    boolean updateUserNoteIsCollected(Integer noteId, boolean isCollected);

}
