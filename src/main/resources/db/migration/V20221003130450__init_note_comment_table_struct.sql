CREATE TABLE `redbook`.`note_comment`
(
    `id`                int          NOT NULL COMMENT '评论id',
    `note_id`           int          NOT NULL COMMENT '笔记id',
    `user_id`           varchar(255) NOT NULL COMMENT '用户id',
    `description`       varchar(255) NULL COMMENT '评论内容',
    `created_date_time` datetime     NULL COMMENT '评论时间',
    PRIMARY KEY (`id`),
    CONSTRAINT `note_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `redbook`.`user` (`id`),
    CONSTRAINT `note_comment_note_id` FOREIGN KEY (`note_id`) REFERENCES `redbook`.`note` (`id`)
) COMMENT = '笔记评论表';