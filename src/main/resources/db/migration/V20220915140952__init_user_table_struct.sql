CREATE TABLE `redbook`.`user`
(
    `id`                  varchar(32)  NOT NULL COMMENT '用户ID',
    `email`               varchar(255) NULL COMMENT '用户邮箱',
    `nickname`            varchar(64)  NULL COMMENT '用户昵称',
    `phone`               varchar(32)  NULL COMMENT '用户手机号(账号)',
    `password`            varchar(64)  NULL COMMENT '用户密码',
    `identity_card_id`    char(18)     NULL COMMENT '用户身份证号码',
    `gender`              int(1)       NULL COMMENT '用户性别',
    `created_date_time`   datetime     NULL COMMENT '用户创建时间',
    `updated_date_time`   datetime     NULL COMMENT '用户信息更新时间',
    `birthday`            datetime     NULL COMMENT '用户生日日期',
    `enabled`             tinyint(1)   NOT NULL DEFAULT 1 COMMENT '账户是否激活',
    `locked`              tinyint(1)   NOT NULL DEFAULT 0 COMMENT '账户是否锁定',
    `profile_photo_id`    varchar(255) NULL COMMENT '用户头像外健',
    `description`         varchar(255) NULL COMMENT '用户简介',
    `region`              varchar(255) NULL COMMENT '用户归属地区',
    `professional`        varchar(255) NULL COMMENT '用户职业',
    `school`              varchar(255) NULL COMMENT '用户学校',
    `background_photo_id` varchar(255) NULL COMMENT '用户背景图id',
    `level`               int(1)                DEFAULT 1 NULL COMMENT '用户等级',
    PRIMARY KEY (`id`)
);