package cn.gnaixeuy.redbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/24
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenByPhoneCreateRequest {

    @NotBlank(message = "电话号码不能为空")
    @Size(min = 4, max = 64, message = "电话号码长度应该在4个字符到64个字符之间")
    private String phoneNumber;

    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 64, message = "验证码长度应该在6个字符到64个字符之间")
    private String verificationCode;

}
