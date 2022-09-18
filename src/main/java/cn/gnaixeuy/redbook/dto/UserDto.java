package cn.gnaixeuy.redbook.dto;

import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.entity.Role;
import cn.gnaixeuy.redbook.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/18
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private String id;

    private String email;

    private String nickname;

    private String phone;

    private String identityCardId;

    private Gender gender;

    private Date createdDateTime;

    private Date updatedDateTime;

    private Date birthday;

    private boolean enabled;

    private boolean locked;
    /**
     * TODO 头像文件Id
     */
    private File profilePhoto;
    /**
     * TODO 背景图文件Id
     */
    private File backgroundPhoto;

    private String description;

    private String region;

    private String professional;

    private String school;

    private Integer level;

    private List<Role> roles;
    
}
