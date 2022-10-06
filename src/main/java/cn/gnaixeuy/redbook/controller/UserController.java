package cn.gnaixeuy.redbook.controller;

import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.ResponseResult;
import cn.gnaixeuy.redbook.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
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
                        .map(this.userMapper::entity2Dto)
                        .map(this.userMapper::dto2Vo)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = {"/me"})
    @RolesAllowed(value = {"ROLE_USER"})
    @ApiOperation(value = "通过请求头保存的token，获取当前用户的信息", httpMethod = "GET")
    public ResponseResult<UserVo> me() {
        return ResponseResult.success(this.userMapper.dto2Vo(
                        this.userMapper.entity2Dto(
                                this.userService.getCurrentUser()
                        )
                )
        );
    }

    @PostMapping(value = {"/profilePhoto"})
    public ResponseResult<UserVo> profilePhotoUpload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        UserDto userDto = this.userService.profilePhotoUpload(multipartFile);
        return ResponseResult.success(this.userMapper.dto2Vo(userDto));
    }

    @PostMapping(value = {"/backgroundPhoto"})
    public ResponseResult<UserVo> backgroundPhotoUpload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        UserDto userDto = this.userService.backgroundPhotoUpload(multipartFile);
        return ResponseResult.success(this.userMapper.dto2Vo(userDto));
    }


    @PostMapping(value = {"/follow/{userId}"})
    public ResponseResult<String> followUser(@PathVariable(value = "userId") String userId) {
        boolean result = this.userService.followUser(userId);
        if (result) {
            return ResponseResult.success("关注成功");
        }
        return ResponseResult.error("关注失败");
    }


    @GetMapping(value = {"/careUsers"})
    public ResponseResult<List<UserVo>> getCareUsers() {
        return ResponseResult.success(
                this.userService.getCareUsers()
                        .stream()
                        .map(this.userMapper::dto2Vo)
                        .collect(Collectors.toList())
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
