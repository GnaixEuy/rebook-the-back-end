package cn.gnaixeuy.redbook.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/23
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@SpringBootTest
class SmsUtilsTest {

    @Test
    void sendMessage() throws Exception {
        SmsUtils.sendMessage("阿里云短信测试", "SMS_154950909", "13365917711", "79913");
    }

}