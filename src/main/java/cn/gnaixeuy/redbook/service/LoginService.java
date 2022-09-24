package cn.gnaixeuy.redbook.service;

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
     * @return 业务是否成功
     */
    boolean getPhoneVerificationCode(String phoneNumber);

}
