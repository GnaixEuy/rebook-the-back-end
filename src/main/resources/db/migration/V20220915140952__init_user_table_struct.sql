CREATE TABLE `redbook`.`user`
(
    `user_id`               varchar(255) NOT NULL COMMENT '用户ID',
    `user_email`            varchar(255) NULL COMMENT '用户邮箱',
    `user_nickname`         varchar(255) NULL COMMENT '用户昵称',
    `user_phone`            varchar(255) NOT NULL COMMENT '用户手机号',
    `user_identity_card_id` char(18)     NULL COMMENT '用户身份证号码',
    `user_gender`           varchar(20)  NULL COMMENT '用户性别',
    `user_create_date_time` datetime     NULL COMMENT '用户创建时间',
    `user_update_date_time` datetime     NULL COMMENT '用户信息更新时间',
    `user_birthday`         datetime     NULL COMMENT '用户生日日期',
    `user_role_id`          int          NULL COMMENT '用户身份属性编号',
    `enabled`               tinyint(1)   NOT NULL DEFAULT 1 COMMENT '账户是否激活',
    `locked`                tinyint(1)   NOT NULL DEFAULT 0 COMMENT '账户是否锁定',
    `profile_photo_id`      varchar(255) NULL COMMENT '用户头像外健',
    PRIMARY KEY (`user_id`)
);