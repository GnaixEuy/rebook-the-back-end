CREATE TABLE `redbook`.`file`
(
    `id`               varchar(255) NOT NULL COMMENT '文件id',
    `url`              varchar(255) NULL COMMENT '文件访问路径',
    `ext`              varchar(255) NOT NULL COMMENT '文件拓展类型',
    `hash`             varchar(255) NOT NULL COMMENT '文件哈希',
    `size`             bigint       NOT NULL COMMENT '文件大小',
    `uploader_id`      varchar(255) NOT NULL COMMENT '上传者id',
    `upload_date_time` datetime     NOT NULL COMMENT '上传时间',
    PRIMARY KEY (`id`)
) COMMENT = '文件持久化信息表';

ALTER TABLE `redbook`.`file`
    ADD CONSTRAINT `uploader_connect` FOREIGN KEY (`uploader_id`) REFERENCES `redbook`.`user` (`id`);