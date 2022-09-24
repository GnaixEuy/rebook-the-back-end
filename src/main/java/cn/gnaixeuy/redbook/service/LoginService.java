package cn.gnaixeuy.redbook.service;

import cn.gnaixeuy.redbook.dto.TokenByPhoneCreateRequest;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/24
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface LoginService {

    /**
     * 请求手机验证码
     *
     * @param phoneNumber 手机号码
     * @return 业务是否成功
     */
    boolean getPhoneVerificationCode(String phoneNumber);

    /**
     * 通过手机方式登录
     *
     * @param tokenByPhoneCreateRequest 手机登录方式包装对象 手机号和验证码
     * @return token
     */
    String createTokenByPhone(TokenByPhoneCreateRequest tokenByPhoneCreateRequest);

}
