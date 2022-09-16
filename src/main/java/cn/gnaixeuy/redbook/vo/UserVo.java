package cn.gnaixeuy.redbook.vo;

import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String userEmail;

    private String userNickname;

    private String userPhone;

    private Gender userGender;

    private Date userBirthday;

    private boolean enabled;

    private boolean locked;

    /**
     * TODO 头像文件Id
     */
    private File profilePhotoImage;

    /**
     * TODO 背景图文件Id
     */
    private File backgroundImage;

    private String userDescription;

    private String userRegion;

    private String userProfessional;

    private String userSchool;
    private Integer userLevel;

}
