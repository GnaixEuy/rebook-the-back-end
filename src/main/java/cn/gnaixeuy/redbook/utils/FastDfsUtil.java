package cn.gnaixeuy.redbook.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/16
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Component
public class FastDfsUtil {

    private FastFileStorageClient storageClient;

    public String upload(MultipartFile multipartFile) throws Exception {
        String originalFilename = Objects.requireNonNull(multipartFile
                        .getOriginalFilename())
                .substring(multipartFile
                        .getOriginalFilename()
                        .lastIndexOf(".") + 1
                );
        StorePath storePath = this.storageClient
                .uploadImageAndCrtThumbImage(multipartFile.getInputStream(),
                        multipartFile.getSize(),
                        originalFilename,
                        null
                );
        return storePath.getFullPath();
    }

    @Autowired
    public void setStorageClient(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }
    
}
