package cn.gnaixeuy.redbook.vo;

import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.enums.Gender;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest
class UserVoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void entityToVo() {
        User user = new User(
                "1",
                "123088@test.com",
                "测试",
                "1234-1234-123",
                "123456789012345678",
                Gender.MAN,
                new Date(),
                new Date(),
                new Date(),
                true,
                true,
                null,
                null,
                "测试用户",
                "厦门",
                "学生",
                "jmu",
                1
        );
        UserVo userVo = this.userMapper.entityToVo(user);
        System.out.println(userVo);
    }


}