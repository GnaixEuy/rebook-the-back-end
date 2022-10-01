package cn.gnaixeuy.redbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/10/1
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "note", resultMap = "noteResultMap")
public class Note {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String description;

    private String authorId;
    @TableField(value = "like")
    private Integer like;
    private Integer collection;
    private Date createdDateTime;
    private Date updatedDateTime;
    private Integer watch;
    @TableField(exist = false)
    private List<File> resources;
    private String title;
    private String locate;
    private List<String> label;
    private String type;

}
