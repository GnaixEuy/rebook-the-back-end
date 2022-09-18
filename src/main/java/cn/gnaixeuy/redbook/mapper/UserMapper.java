package cn.gnaixeuy.redbook.mapper;

import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.vo.UserCreateRequest;
import cn.gnaixeuy.redbook.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * @param user 实体层对象
     * @return userVo vo对象
     */
    @Mappings(value = {
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
    })
    UserDto entityToDto(User user);

    @Mappings(value = {
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "profilePhotoImage", source = "profilePhoto"),
            @Mapping(target = "backgroundImage", source = "backgroundPhoto")
    })
    UserVo dtoToVo(UserDto userDto);


    User userCreateRequstToEntity(UserCreateRequest userCreateRequest);

}
