package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.dto.TokenByPhoneCreateRequest;
import cn.gnaixeuy.redbook.service.LoginService;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 * 登录接口
 *
 * @author GnaixEuy
 * @date 2022/9/23
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */

@RestController
@RequestMapping(value = {"/login"})
public class LoginController {

    private LoginService loginService;

    @GetMapping(value = {"/getPhoneVerificationCode/{phoneNumber}"})
    public ResponseResult<String> getVerificationCode(@PathVariable String phoneNumber) {
        this.loginService.getPhoneVerificationCode(phoneNumber);
        return ResponseResult.success("短信发送成功");
    }

    @PostMapping(value = {"/phoneNumber"})
    public ResponseResult<String> getTokenByPhone(@RequestBody TokenByPhoneCreateRequest tokenByPhoneCreateRequest) {
        return ResponseResult.success(this.loginService.createTokenByPhone(tokenByPhoneCreateRequest));
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
