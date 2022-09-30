package cn.gnaixeuy.redbook.dao;

import cn.gnaixeuy.redbook.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户刀
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/15
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 更新配置文件照片
     *
     * @param id                用户id
     * @param profilePhotoId    头像照片id
     * @param backgroundPhotoId 背景图id
     * @return int
     */
    int updateUserPhoto(String id, String profilePhotoId, String backgroundPhotoId);

}
