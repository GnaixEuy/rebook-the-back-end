package cn.gnaixeuy.redbook.service.impl;

import cn.gnaixeuy.redbook.config.RedisConfig;
import cn.gnaixeuy.redbook.dao.RoleDao;
import cn.gnaixeuy.redbook.dao.UserDao;
import cn.gnaixeuy.redbook.dao.relation.UserFollowRelationDao;
import cn.gnaixeuy.redbook.dao.relation.UserRoleAssociateDao;
import cn.gnaixeuy.redbook.dto.FileDto;
import cn.gnaixeuy.redbook.dto.UserDto;
import cn.gnaixeuy.redbook.entity.Role;
import cn.gnaixeuy.redbook.entity.User;
import cn.gnaixeuy.redbook.entity.relation.UserFollowRelation;
import cn.gnaixeuy.redbook.entity.relation.UserRoleAssociate;
import cn.gnaixeuy.redbook.enums.ExceptionType;
import cn.gnaixeuy.redbook.enums.RedisDbType;
import cn.gnaixeuy.redbook.exception.BizException;
import cn.gnaixeuy.redbook.mapper.UserMapper;
import cn.gnaixeuy.redbook.service.FileService;
import cn.gnaixeuy.redbook.service.UserService;
import cn.gnaixeuy.redbook.vo.UserCreateRequest;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private FileService fileService;
    private UserFollowRelationDao userFollowRelationDao;
    private RedisConfig redisConfig;

    /**
     * 新增用户业务
     *
     * @param userCreateRequest 新增用户传入参数
     * @return 返回是否新增数据对象vo
     */
    @Override
    @Transactional
    public UserDto addUser(UserCreateRequest userCreateRequest) {
        User user = this.userMapper.userCreateRequst2Entity(userCreateRequest);
        user.setCreatedDateTime(new Date());
        int result = this.baseMapper.insert(user);
        if (result != 1) {
            throw new BizException(ExceptionType.USER_CREATE_EXCEPTION);
        }
        user = this.baseMapper
                .selectOne(Wrappers
                        .<User>lambdaQuery()
                        .eq(User::getId, user.getId()));
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        //默认新用户赋予用户权限
        int authorizeResult = this.userRoleAssociateDao.insert(new UserRoleAssociate(1, user.getId()));
        if (authorizeResult != 1) {
            throw new BizException(ExceptionType.AUTHORIZATION_EXCEPTION);
        }
        //填充默认用户权限信息
        Role role = this.roleDao.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, 1));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return this.userMapper.entity2Dto(user);
    }

    /**
     * @param phone 手机号码作为用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = (User) this.redisConfig
                .getRedisTemplateByDb(RedisDbType.USER_INFO.getCode())
                .opsForValue()
                .get(phone);
        if (user != null) {
            return user;
        }
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, phone));
    }

    /**
     * 获取当前登录用户
     *
     * @return 返回当前登录用户
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) this.loadUserByUsername(authentication.getName());
    }

    /**
     * 配置文件上传照片
     *
     * @param multipartFile 用户头像文件
     * @return {@link UserDto}
     */
    @Override
    public UserDto profilePhotoUpload(MultipartFile multipartFile) {
        FileDto fileDto = this.fileService.saveFile(multipartFile, this.getCurrentUser());
        User uploader = fileDto.getUploader();
        int updateResult = this.baseMapper.updateUserPhoto(uploader.getId(), fileDto.getId(), null);
        if (updateResult != 1) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        } else {
            return this.userMapper.entity2Dto(this.baseMapper.selectById(uploader.getId()));
        }
    }

    /**
     * 用户背景上传照片
     *
     * @param multipartFile 用户背景文件
     * @return {@link UserDto}
     */
    @Override
    public UserDto backgroundPhotoUpload(MultipartFile multipartFile) {
        FileDto fileDto = this.fileService.saveFile(multipartFile, this.getCurrentUser());
        User uploader = fileDto.getUploader();
        int updateResult = this.baseMapper.updateUserPhoto(uploader.getId(), null, fileDto.getId());
        if (updateResult != 1) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        } else {
            return this.userMapper.entity2Dto(this.baseMapper.selectById(uploader.getId()));
        }
    }

    /**
     * 关注用户业务
     *
     * @param userId 被关注的用户的用户Id
     * @return 返回业务是否成功
     */
    @Override
    public boolean followUser(String userId) {
        User currentUser = this.getCurrentUser();
        if (userId.equals(currentUser.getId())) {
            throw new BizException(ExceptionType.USER_FOCUS_ON_SELF_ERROR);
        }
        User usersWhoAreFollowed = this.getById(userId);
        if (usersWhoAreFollowed == null) {
            throw new BizException(ExceptionType.USER_FOCUS_ON_NO_EXIST_ERROR);
        }
        UserFollowRelation userFollowRelation = this.userFollowRelationDao.selectOne(Wrappers
                .<UserFollowRelation>lambdaQuery()
                .eq(UserFollowRelation::getUserId, currentUser.getId())
                .eq(UserFollowRelation::getFollowId, usersWhoAreFollowed.getId())
        );
        if (userFollowRelation != null) {
            throw new BizException(ExceptionType.USER_FOCUS_ON_REPEAT_ERROR);
        }
        userFollowRelation = new UserFollowRelation(currentUser.getId(), usersWhoAreFollowed.getId());
        return 1 == this.userFollowRelationDao.insert(userFollowRelation);
    }

    /**
     * 获取当前用户关注的人
     *
     * @return List <UserDto> 关注的人集合
     */
    @Override
    public List<UserDto> getCareUsers() {
        User currentUser = this.getCurrentUser();
        List<UserDto> cares = new LinkedList<>();
        this.userFollowRelationDao.selectList(Wrappers
                .<UserFollowRelation>lambdaQuery()
                .eq(UserFollowRelation::getUserId, currentUser.getId())
        ).forEach(item -> cares.add(this.userMapper.entity2Dto(this.baseMapper.selectById(item.getFollowId()))));
        return cares;
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

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserFollowRelationDao(UserFollowRelationDao userFollowRelationDao) {
        this.userFollowRelationDao = userFollowRelationDao;
    }

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}
