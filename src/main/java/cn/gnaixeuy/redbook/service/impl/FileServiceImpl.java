package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.dao.FileDao;
import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.entity.File;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.FileMapper;
import cn.gnaixeuy.redbook.service.FileService;
import cn.gnaixeuy.redbook.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

    /**
     * 保存文件
     *
     * @param fileDto 文件dto
     * @return boolean
     */
    @Override
    @Transactional
    public FileDto saveFile(FileDto fileDto) {
        User uploader = this.userService.getCurrentUser();
        if (uploader == null) {
            throw new BizException(ExceptionType.FILE_NOT_PERMISSION);
        }
        fileDto.setUploader(uploader);
        File file = this.fileMapper.dto2Entity(fileDto);
        int insertResult = this.baseMapper.insert(file);
        if (insertResult == 1) {
            file = this.baseMapper.selectById(file.getId());
            fileDto = this.fileMapper.entity2Dto(file);
            fileDto.setUploader(this.userService.getById(file.getUploaderId()));
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
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
