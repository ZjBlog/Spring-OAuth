-- 创建一个用户 一些初始化操作--
CREATE USER 'test'@'%' IDENTIFIED WITH mysql_native_password BY 'admin';
grant all on *.* to 'test'@'%';

use bgcz;
DROP TABLE IF EXISTS bgcz_user;
CREATE TABLE bgcz_user (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  email VARCHAR(50),
  password VARCHAR(500),
  activated BOOLEAN DEFAULT FALSE,
  activationkey VARCHAR(50) DEFAULT NULL,
  resetpasswordkey VARCHAR(50) DEFAULT NULL
);
INSERT INTO bgcz_user (username,email, password, activated) VALUES ('admin', 'admin@mail.me', 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1', true);
INSERT INTO bgcz_user (username,email, password, activated) VALUES ('user', 'user@mail.me', 'd6dfa9ff45e03b161e7f680f35d90d5ef51d243c2a8285aa7e11247bc2c92acde0c2bb626b1fac74', true);

