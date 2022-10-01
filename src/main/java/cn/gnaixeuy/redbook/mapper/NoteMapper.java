package cn.gnaixeuy.redbook.mapper;

import cn.gnaixeuy.redbook.dto.NoteDto;
import cn.gnaixeuy.redbook.entity.Note;
import cn.gnaixeuy.redbook.vo.NoteCreateRequest;
import cn.gnaixeuy.redbook.vo.NoteVo;
import org.mapstruct.Mapper;

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
@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note noteCreateRequst2Entity(NoteCreateRequest noteCreateRequest);

    NoteDto entity2Dto(Note note);

    NoteVo dto2Vo(NoteDto noteDto);

}
