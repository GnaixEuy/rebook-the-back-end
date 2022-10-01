package cn.gnaixeuy.redbook.service;

import cn.gnaixeuy.redbook.dto.NoteDto;
import cn.gnaixeuy.redbook.entity.Note;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
