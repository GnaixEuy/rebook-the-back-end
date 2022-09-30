package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.FileMapper;
import cn.gnaixeuy.redbook.service.FileService;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.FileVo;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/16
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@RequestMapping(value = {"/file"})
public class FileController {


    private FileMapper fileMapper;

    private FileService fileService;
    private UserService userService;

    /**
     * @param multipartFile 文件
     * @return {@link ResponseResult}<{@link String}>
     */
    @PostMapping(value = {"/upload"})
    public ResponseResult<FileVo> upload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        FileDto fileDto = this.fileService.saveFile(multipartFile, this.userService.getCurrentUser());
        if (fileDto != null) {
            return ResponseResult.success(this.fileMapper.dto2VO(fileDto));
        } else {
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
    }


    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
