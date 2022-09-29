package cn.gnaixeuy.redbook.mapper;

import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.vo.FileVo;
import cn.gnaixeuy.redbook.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 *
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/9/29
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mappings(value = {
            @Mapping(source = "uploader.id", target = "uploaderId")
    })
    File dto2Entity(FileDto fileDto);

    FileDto entity2Dto(File file);

    @Mappings(value = {
            @Mapping(source = "uploader", target = "uploader", resultType = UserVo.class)
    })
    FileVo dto2VO(FileDto fileDto);

}
