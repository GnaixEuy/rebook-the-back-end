CREATE TABLE `redbook`.`user_role_associate`
(
    `user_id` varchar(255) NULL,
    `role_id` int          NULL
);
ALTER TABLE `redbook`.`user_role_associate`
    MODIFY COLUMN `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL FIRST,
    MODIFY COLUMN `role_id` int NOT NULL AFTER `user_id`,
    ADD CONSTRAINT `user表关联` FOREIGN KEY (`user_id`) REFERENCES `redbook`.`user` (`id`),
    ADD CONSTRAINT `role表关联` FOREIGN KEY (`role_id`) REFERENCES `redbook`.`role` (`id`);