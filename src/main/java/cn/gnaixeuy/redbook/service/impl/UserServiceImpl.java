package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.config.SecurityConfig;
import cn.gnaixeuy.redbook.dao.RoleDao;
import cn.gnaixeuy.redbook.dao.UserDao;
import cn.gnaixeuy.redbook.dao.UserRoleAssociateDao;
import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.entity.Role;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.entity.common.UserRoleAssociate;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.UserCreateRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    /**
     * 映射转换对象
     */
    private UserMapper userMapper;
    private UserRoleAssociateDao userRoleAssociateDao;
    private RoleDao roleDao;

    /**
     * 新增用户业务
     *
     * @param userCreateRequest 新增用户传入参数
     * @return 返回是否新增数据对象vo
     */
    @Override
    public UserDto addUser(UserCreateRequest userCreateRequest) {
        User user = this.userMapper.userCreateRequstToEntity(userCreateRequest);
        int result = this.baseMapper.insert(user);
        if (result != 1) {
            //TODO throw 创建 exception
        }
        Optional<User> optionalUser = Optional.of(
                this.baseMapper
                        .selectOne(lambdaQuery()
                                .eq(User::getId, user.getId())));
        if (optionalUser.isEmpty()) {
            //TODO throw 查询 exception
        }
        //默认新用户赋予用户权限
        int authorizeResult = this.userRoleAssociateDao.insert(new UserRoleAssociate(1, user.getId()));
        if (authorizeResult != 1) {
            //TODO throw 赋权 exception
        }
        //填充默认用户权限信息
        Role role = this.roleDao.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, 1));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        optionalUser.get().setRoles(roles);
        return this.userMapper.entityToDto(optionalUser.get());
    }

    @Override
    public Optional<User> userPhoneExist(String phone) {
        return Optional.of(this.baseMapper
                .selectOne(this.lambdaQuery()
                        .eq(User::getPhone, phone)));
        //TODO 后期国家机制预存
//        String phoneNumberAttribution = phone.substring(0, 3);
//        phone = phone.substring(3, 6) + "-" + phone.substring(6, 10) + "-" + phone.substring(10, 14);
//        switch (phoneNumberAttribution) {
//            case "+86":
//                break;
//            default:
//                //TODO 其他国家
//                break;
//        }
    }

    /**
     * @param phone 手机号码作为用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return this.baseMapper
                .selectOne(lambdaQuery()
                        .eq(User::getPhone, phone));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // todo
        //phone 作为UserName
        return (User) loadUserByUsername(authentication.getName());
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
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleAssociateDao(UserRoleAssociateDao userRoleAssociateDao) {
        this.userRoleAssociateDao = userRoleAssociateDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
