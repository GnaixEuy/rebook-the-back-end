ALTER TABLE `redbook`.`user`
    ADD COLUMN `user_description` varchar(255) NULL AFTER `profile_photo_id`,
    ADD COLUMN `user_region` varchar(255) NULL AFTER `user_description`,
    ADD COLUMN `user_professional` varchar(255) NULL AFTER `user_region`,
    ADD COLUMN `user_school` varchar(255) NULL AFTER `user_professional`,
    ADD COLUMN `backgroud_photo_id` varchar(255) NULL AFTER `user_school`;