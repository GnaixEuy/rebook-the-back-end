package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.config.RedisConfig;
import cn.gnaixeuy.redbook.config.SecurityConfig;
import cn.gnaixeuy.redbook.dto.TokenByPhoneCreateRequest;
import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.enums.RedisDbType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.LoginService;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.utils.ValidateCodeUtils;
import cn.gnaixeuy.redbook.vo.UserCreateRequest;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/24
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Value("#{T(java.lang.Integer).parseInt('${phone.verification.length:6}')}")
    private Integer phoneVerificationCodeLength;
    @Value("#{T(java.lang.Integer).parseInt('${phone.verification.live:120}')}")
    private Integer phoneVerificationCodeLiveTime;

    private RedisConfig redisConfig;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserMapper userMapper;

    /**
     * 请求手机验证码
     *
     * @param phoneNumber 手机号码
     * @return 业务是否成功
     */
    @Override
    public boolean getPhoneVerificationCode(String phoneNumber) {
        if (StrUtil.isBlank(phoneNumber)) {
            throw new BizException(ExceptionType.DATA_IS_EMPTY);
        }
        RedisTemplate<String, Object> redisTemplateByDb = this.redisConfig.getRedisTemplateByDb(RedisDbType.PHONEVERIFICATIONCODE.getCode());
        if (ObjectUtil.isNotNull(redisTemplateByDb.opsForValue().get(phoneNumber))) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_EXIT);
        }
        Integer verificationCode = ValidateCodeUtils.generateValidateCode(this.phoneVerificationCodeLength);
        LoginServiceImpl.log.info(phoneNumber + " verificationCode: " + verificationCode);
        redisTemplateByDb.opsForValue().set(phoneNumber, verificationCode, this.phoneVerificationCodeLiveTime, TimeUnit.SECONDS);
        try {
            //TODO 发送功能已正常，模版未申请
//            SmsUtils.sendMessage("阿里云短信测试", "SMS_154950909", phoneNumber, String.valueOf(verificationCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 返回机制待确认
        return true;
    }

    /**
     * 通过手机方式登录
     *
     * @param tokenByPhoneCreateRequest 手机登录方式包装对象 手机号和验证码
     * @return token
     */
    @Override
    public String createTokenByPhone(TokenByPhoneCreateRequest tokenByPhoneCreateRequest) {
        String realVerificationCode = String.valueOf(this.redisConfig
                .getRedisTemplateByDb(RedisDbType.PHONEVERIFICATIONCODE.getCode())
                .opsForValue()
                .getAndDelete(tokenByPhoneCreateRequest.getPhoneNumber()));
        if (realVerificationCode == null || "null".equals(realVerificationCode)) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_EXPIRED);
        }
        if (!StrUtil.equals(tokenByPhoneCreateRequest.getVerificationCode(), realVerificationCode)) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_CODE_ERROR);
        }
        UserDetails user = this.userService.loadUserByUsername(tokenByPhoneCreateRequest.getPhoneNumber());
        if (ObjectUtil.isNull(user)) {
            UserDto userDto = this.userService.addUser(new UserCreateRequest() {{
                this.setPhone(tokenByPhoneCreateRequest.getPhoneNumber());
                this.setEnabled(true);
                this.setLocked(false);
            }});
            user = this.userMapper.dto2Entity(userDto);
        }

        return tokenVerifyAndGenerated((User) user);
    }

    private String tokenVerifyAndGenerated(User user) {
        if (!user.isEnabled()) {
            throw new BizException(ExceptionType.USER_NOT_ENABLED);
        }
        if (!user.isAccountNonLocked()) {
            throw new BizException(ExceptionType.USER_LOCKED);
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
