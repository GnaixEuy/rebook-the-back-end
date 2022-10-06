package cn.gnaixeuy.redbook.dao;

import cn.gnaixeuy.redbook.dao.relation.UserRoleAssociateDao;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.entity.relation.UserRoleAssociate;
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
    @Autowired
    private UserRoleAssociateDao userRoleAssociateDao;

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
        user.setPhone("12312312312");
        int result = this.userDao.insert(user);
        assertEquals(1, result);
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        User user = this.userDao.selectOne(Wrappers
                .<User>lambdaQuery()
                .eq(User::getId, "1"));
        assertNotNull(user);
        user.setNickname("测试小子");
        int result = this.userDao.updateById(user);
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    public void testSelectByUserPhone() {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, "13365917711"));
        assertNotNull(user);
    }

    @Test
    @Order(5)
    public void testDeleteUserByUserPhone() {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, "13365917711"));
        this.userRoleAssociateDao.delete(Wrappers.<UserRoleAssociate>lambdaQuery().eq(UserRoleAssociate::getUserId, user.getId()));

        assertNotNull(user);
        int result = this.userDao.deleteById(user);
        assertEquals(1, result);
    }

}