CREATE TABLE `redbook`.`note`
(
    `id`                int          NOT NULL COMMENT '笔记id',
    `description`       varchar(255) NULL COMMENT '笔记内容',
    `author_id`         varchar(255) NULL COMMENT '作者id',
    `like`              int          NULL COMMENT '笔记点赞数量',
    `collection`        int          NULL COMMENT '笔记收藏数量',
    `created_date_time` datetime     NULL COMMENT '笔记首次发布时间',
    `updated_date_time` datetime     NULL COMMENT '笔记最新修改发布时间',
    `watch`             int          NULL COMMENT '笔记观看数量',
    `title`             varchar(255) NULL COMMENT '笔记标题',
    `locate`            varchar(10)  NULL COMMENT '笔记发布地址',
    `label`             varchar(10)  NULL COMMENT '笔记标签',
    `type`              varchar(10)  NULL COMMENT '笔记类型',
    PRIMARY KEY (`id`),
    CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `redbook`.`user` (`id`)
) COMMENT = '笔记表';