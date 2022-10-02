CREATE TABLE `redbook`.`note_resources`
(
    `note_id` int          NOT NULL COMMENT '笔记id',
    `file_id` varchar(255) NULL COMMENT '笔记资源id',
    CONSTRAINT `note_id` FOREIGN KEY (`note_id`) REFERENCES `redbook`.`note` (`id`),
    CONSTRAINT `file_id` FOREIGN KEY (`file_id`) REFERENCES `redbook`.`file` (`id`)
) COMMENT = '笔记的资源文件持久化记录表，如笔记图片、视频';