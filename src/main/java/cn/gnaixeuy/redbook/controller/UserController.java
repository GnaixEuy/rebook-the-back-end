package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import cn.gnaixeuy.redbook.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                        .map(this.userMapper::entityToDto)
                        .map(this.userMapper::dtoToVo)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = {"/me"})
//    @ApiOperation(value = "通过请求头保存的token，获取当前用户的信息", httpMethod = "GET")
    public ResponseResult<UserVo> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseResult.success(this.userMapper.dtoToVo(
                        this.userMapper.entityToDto(
                                this.userService.getCurrentUser()
                        )
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