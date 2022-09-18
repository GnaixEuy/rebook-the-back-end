package cn.gnaixeuy.redbook.service;

import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.vo.UserCreateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 新增用户业务
     *
     * @param userCreateRequest 新增用户传入参数
     * @return 返回是否新增数据对象vo
     */
    UserDto addUser(UserCreateRequest userCreateRequest);

}
