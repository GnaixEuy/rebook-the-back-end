package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.utils.FastDfsUtil;
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

    private FastDfsUtil fastDfsUtil;

    @PostMapping(value = {"/upload"})
    public ResponseResult<String> upload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        System.out.println(multipartFile.getOriginalFilename());
        try {
            return ResponseResult.success(this.fastDfsUtil.upload(multipartFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setFastDfsUtil(FastDfsUtil fastDfsUtil) {
        this.fastDfsUtil = fastDfsUtil;
    }
}
