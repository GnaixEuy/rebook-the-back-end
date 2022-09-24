package cn.gnaixeuy.redbook.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： redbook </p>
 *
 * @author GnaixEuy
 * @date 2022/9/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
@AllArgsConstructor
public enum ExceptionType {

    /**
     * 枚举业务异常信息
     */
    INNER_ERROR(500, "系统内部错误,联系支持"),
    UNAUTHORIZED(401, "未登录"),
    BAD_REQUEST(400, "请求错误"),
    FORBIDDEN(403, "无权操作"),
    NOT_FOUND(404, "未找到"),
    USER_NAME_DUPLICATE(40001001, "用户名重复"),
    USER_NOT_FOUND(40401002, "用户不存在,请注册"),
    USER_PASSWORD_NOT_MATCH(40001003, "用户名或密码错误"),
    USER_NOT_ENABLED(50001001, "用户未启用"),
    USER_LOCKED(50001002, "用户被锁定"),
    USER_INSERT_ERROR(40001077, "创建用户信息失败"),
    USER_UPDATE_ERROR(50001040, "用户更新失败"),
    USER_DELETE_ERROR(50001070, "用户删除失败"),
    ROLE_DELETE_ERROR(50001080, "角色信息删除失败"),
    USER_ROLE_BIND_ERROR(50001081, "用户角色信息绑定失败"),
    FILE_NOT_FOUND(40403001, "文件不存在"),
    FILE_UPLOAD_ERROR(40403002, "文件上传失败"),
    FILE_EMPTY(40403003, "文件为空"),
    FILE_NOT_PERMISSION(40303002, "当前用户无权限修改文件"),
    PHONE_VERIFICATION_EXIT(50083001, "手机验证码已存在"),
    DATA_IS_EMPTY(50010001, "参数为空");

    private final Integer code;
    private final String message;

}
