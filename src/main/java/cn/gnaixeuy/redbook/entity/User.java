package cn.gnaixeuy.redbook.entity;

import cn.gnaixeuy.redbook.enums.Gender;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
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
public class User {

    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;
    @TableField
    private String userEmail;
    @TableField
    private String userNickname;
    @TableField
    private String userPhone;
    @TableField
    private String userIdentityCardId;
    @TableField
    private Gender userGender;
    @TableField
    private Date userCreateDateTime;
    @TableField
    private Date userUpdateDateTime;
    @TableField
    private Date userBirthday;
    @TableField
    private boolean enabled;
    @TableField
    private boolean locked;

}
