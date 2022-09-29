package cn.gnaixeuy.redbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 *
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/9/30
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {

    /**
     * 文件标识
     */
    private String id;
    /**
     * 文件url
     */
    private String url;
    /**
     * 文件ext
     */
    private String ext;
    /**
     * 文件哈希
     */
    private String hash;
    /**
     * 文件尺寸
     */
    private long size;
    /**
     * 上传者
     */
    private UserVo uploader;
    /**
     * 上传日期时间
     */
    private Date uploadDateTime;

}
