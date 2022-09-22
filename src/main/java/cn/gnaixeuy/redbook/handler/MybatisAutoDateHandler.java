package cn.gnaixeuy.redbook.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/18
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Slf4j
@Component
public class MybatisAutoDateHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始装填日期信息");
        this.strictInsertFill(metaObject, "createdDateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updatedDateTime", Date.class, new Date());
        log.info("结束装填日期信息");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新日期信息");
        this.strictUpdateFill(metaObject, "updatedDateTime", Date.class, new Date());
        this.setFieldValByName("updatedDateTime", new Date(), metaObject);
        log.info("结束更新日期信息");
    }
}
