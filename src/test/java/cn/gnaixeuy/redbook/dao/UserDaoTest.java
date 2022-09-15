package cn.gnaixeuy.redbook.dao;

import cn.gnaixeuy.redbook.entity.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@SpringBootTest
@Transactional
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Order(1)
    public void testSelectAllUser() {
        List<User> users = this.userDao.selectList(null);
        assertNotNull(users);
        users.forEach(System.out::println);
    }

    @Test
    @Order(2)
    public void testInsertUser() {
        User user = new User();
        user.setUserPhone("12312312312");
        int result = this.userDao.insert(user);
        assertEquals(1, result);
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        User user = this.userDao.selectOne(Wrappers
                .<User>lambdaQuery()
                .eq(User::getUserId, "2da22d1f-5bd8-92e8-4354-fca11d8042b7"));
        assertNotNull(user);
        user.setUserNickname("测试小子");
        int result = this.userDao.updateById(user);
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    public void test03SelectByUserPhone() {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, "21-439-4780"));
        assertNotNull(user);
    }

    @Test
    @Order(5)
    public void test04DeleteUserByUserPhone() {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, "21-439-4780"));
        assertNotNull(user);
        int result = this.userDao.deleteById(user);
        assertEquals(1, result);
    }

}