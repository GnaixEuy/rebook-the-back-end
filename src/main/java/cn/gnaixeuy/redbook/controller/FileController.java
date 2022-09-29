package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.FileMapper;
import cn.gnaixeuy.redbook.service.FileService;
import cn.gnaixeuy.redbook.utils.FastDfsUtil;
import cn.gnaixeuy.redbook.vo.FileVo;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

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

    /**
     *
     */
    private FastDfsUtil fastDfsUtil;
    private FileMapper fileMapper;

    private FileService fileService;

    /**
     * @param multipartFile 文件
     * @return {@link ResponseResult}<{@link String}>
     */
    @PostMapping(value = {"/upload"})
    public ResponseResult<FileVo> upload(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        try {
            // TODO 上传文件重构->指定上传group
            String returnUrl = this.fastDfsUtil.upload(multipartFile);
            FileDto fileDto = new FileDto();
            fileDto.setUrl(returnUrl);
            fileDto.setSize(multipartFile.getSize());
            fileDto.setHash(String.valueOf(multipartFile.hashCode()));
            fileDto.setExt(multipartFile.getContentType());
            fileDto.setUploadDateTime(new Date());
            fileDto = this.fileService.saveFile(fileDto);
            if (fileDto != null) {
                return ResponseResult.success(this.fileMapper.dto2VO(fileDto));
            } else {
                throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置快速dfs注入
     *
     * @param fastDfsUtil 注入工具类
     */
    @Autowired
    public void setFastDfsUtil(FastDfsUtil fastDfsUtil) {
        this.fastDfsUtil = fastDfsUtil;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
