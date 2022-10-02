CREATE TABLE `redbook`.`user_others_notes_associated`
(
    `user_id`      varchar(255) NOT NULL COMMENT '用户id',
    `note_id`      int          NOT NULL COMMENT '笔记id',
    `is_collected` tinyint(1)   NULL COMMENT '是否收藏',
    `is_like`      tinyint(1)   NULL COMMENT '是否点赞',
    CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `redbook`.`user` (`id`),
    CONSTRAINT `note` FOREIGN KEY (`note_id`) REFERENCES `redbook`.`note` (`id`)
) COMMENT = '用户和他人笔记之间的联系';