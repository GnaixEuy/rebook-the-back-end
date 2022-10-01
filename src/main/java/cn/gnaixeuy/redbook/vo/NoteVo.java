package cn.gnaixeuy.redbook.vo;

import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteVo {

    private Integer id;
    private String description;
    private User author;
    private Integer like;
    private Integer collection;
    private Date createdDateTime;
    private Date updatedDateTime;
    private Integer watch;
    private List<File> resources;
    private String title;
    private String locate;
    private List<String> label;
    private String type;

}
