package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import cn.gnaixeuy.redbook.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@RequestMapping(value = {"/user"})
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping(value = {"/all"})
    public ResponseResult<List<UserVo>> allUserInfo() {
        return ResponseResult.success(
                this.userService
                        .list()
                        .stream()
                        .map(userMapper::entityToVo)
                        .collect(Collectors.toList()
                        )
        );
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
