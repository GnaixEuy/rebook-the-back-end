package cn.gnaixeuy.redbook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/17
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "role", resultMap = "roleResultMap")
public class Role implements Serializable {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 名字
     */
    @TableField
    private String name;
    /**
     * 标题
     */
    @TableField
    private String title;
    /**
     * 创建日期时间
     */
    @TableField
    private Date createdDateTime;
    /**
     * 更新日期时间
     */
    @TableField
    private Date updatedDateTime;

}
