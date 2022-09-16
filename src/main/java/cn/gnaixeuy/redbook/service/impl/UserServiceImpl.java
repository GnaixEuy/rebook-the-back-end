package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.dao.UserDao;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
}
