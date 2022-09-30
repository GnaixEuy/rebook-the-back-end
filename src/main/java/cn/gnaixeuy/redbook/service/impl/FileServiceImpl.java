package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.dao.FileDao;
import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.FileMapper;
import cn.gnaixeuy.redbook.service.FileService;
import cn.gnaixeuy.redbook.utils.FastDfsUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/9/29
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

    private FileMapper fileMapper;
    private FastDfsUtil fastDfsUtil;


    /**
     * 保存文件
     *
     * @param multipartFile 文件
     * @param uploader      上传者
     * @return boolean
     */
    @Override
    @Transactional
    public FileDto saveFile(MultipartFile multipartFile, User uploader) {
        File file = null;
        FileDto fileDto;
        try {
            // TODO 上传文件重构->指定上传group
            String returnUrl = this.fastDfsUtil.upload(multipartFile);
            fileDto = new FileDto();
            fileDto.setUrl(returnUrl);
            fileDto.setSize(multipartFile.getSize());
            fileDto.setHash(String.valueOf(multipartFile.hashCode()));
            fileDto.setExt(multipartFile.getContentType());
            fileDto.setUploadDateTime(new Date());
            if (uploader == null) {
                throw new BizException(ExceptionType.FILE_NOT_PERMISSION);
            }
            fileDto.setUploader(uploader);
            file = this.fileMapper.dto2Entity(fileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int insertResult = this.baseMapper.insert(file);
        if (insertResult == 1) {
            assert file != null;
            file = this.baseMapper.selectById(file.getId());
            fileDto = this.fileMapper.entity2Dto(file);
            fileDto.setUploader(uploader);
            return fileDto;
        } else {
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
    }


    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Autowired
    public void setFastDfsUtil(FastDfsUtil fastDfsUtil) {
        this.fastDfsUtil = fastDfsUtil;
    }


}
