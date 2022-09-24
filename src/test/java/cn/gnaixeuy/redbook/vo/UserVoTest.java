package cn.gnaixeuy.redbook.vo;

import cn.gnaixeuy.redbook.dto.UserDto;
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
class UserDtoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void entityToDto() {
        User user = new User();
        user.setId("1");
        user.setEmail("123088@test.com");
        user.setNickname("测试");
        user.setPhone("1234-1234-123");
        user.setIdentityCardId("123456789012345678");
        user.setGender(Gender.MAN);
        user.setCreatedDateTime(new Date());
        user.setUpdatedDateTime(new Date());
        user.setBirthday(new Date());
        user.setLocked(false);
        user.setEnabled(true);
        user.setRegion("厦门");
        user.setLevel(1);
        user.setSchool("jmu");
        user.setProfessional("student");
        UserDto userDto = this.userMapper.entity2Dto(user);
        System.out.println(userDto);
    }


}