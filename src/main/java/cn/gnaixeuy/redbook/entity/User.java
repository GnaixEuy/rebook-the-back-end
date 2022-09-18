package cn.gnaixeuy.redbook.entity;

import cn.gnaixeuy.redbook.enums.Gender;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
@TableName(value = "user", resultMap = "userResultMap")
public class User implements UserDetails {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField
    private String email;
    @TableField
    private String nickname;
    @TableField
    private String phone;
    @TableField
    private String password;
    @TableField
    private String identityCardId;
    @TableField
    private Gender gender;
    @TableField
    private Date createdDateTime;
    @TableField
    private Date updatedDateTime;
    @TableField
    private Date birthday;
    @TableField
    private boolean enabled;
    @TableField
    private boolean locked;
    /**
     * TODO 头像文件Id
     */
    @TableField(exist = false)
    private File profilePhoto;
    /**
     * TODO 背景图文件Id
     */
    @TableField(exist = false)
    private File backgroundPhoto;

    @TableField
    private String description;
    @TableField
    private String region;
    @TableField
    private String professional;
    @TableField
    private String school;
    @TableField
    private Integer level;
    @TableField(exist = false)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
