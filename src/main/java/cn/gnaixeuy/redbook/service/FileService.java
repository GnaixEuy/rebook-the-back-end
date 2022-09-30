package cn.gnaixeuy.redbook.service;

import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
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
public interface FileService extends IService<File> {

    /**
     * 保存文件
     *
     * @param multipartFile 文件
     * @param uploader      上传者
     * @return boolean
     */
    FileDto saveFile(MultipartFile multipartFile, User uploader);


}
