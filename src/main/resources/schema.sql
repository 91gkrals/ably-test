DROP TABLE IF EXISTS `user` CASCADE;


CREATE TABLE `verificationHistory`
(
    `id`              bigint      NULL AUTO_INCREMENT,
    `digit`            varchar(100) NOT NULL,                --사용자 휴대폰번호
    `code`           varchar(50) NOT NULL,                --인증코드
    `isVerified`      boolean     NULL,                --인증여부
    PRIMARY KEY (`id`)
);



