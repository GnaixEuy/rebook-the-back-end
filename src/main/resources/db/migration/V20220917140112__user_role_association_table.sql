CREATE TABLE `redbook`.`role`
(
    `id`                int(1)       NOT NULL COMMENT '角色ID',
    `name`              varchar(255) NULL COMMENT '角色名称',
    `title`             varchar(255) NULL COMMENT '角色标题',
    `created_date_time` datetime     NULL COMMENT '创建时间',
    `updated_date_time` datetime     NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
);