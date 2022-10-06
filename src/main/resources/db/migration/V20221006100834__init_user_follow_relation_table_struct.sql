CREATE TABLE `redbook`.`user_follow_relation`
(
    `user_id`   varchar(255) NULL COMMENT '用户id',
    `follow_id` varchar(255) NULL COMMENT '关注的人的id',
    CONSTRAINT `follow_user_id` FOREIGN KEY (`follow_id`) REFERENCES `redbook`.`user` (`id`),
    CONSTRAINT `active_user` FOREIGN KEY (`user_id`) REFERENCES `redbook`.`user` (`id`)
) COMMENT = '用户关注对象持久化记录表';