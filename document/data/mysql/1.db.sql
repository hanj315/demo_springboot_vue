CREATE DATABASE demo DEFAULT CHARACTER SET utf8;
GRANT ALL PRIVILEGES ON demo.* TO 'demo'@'%' IDENTIFIED BY 'Password$1';
FLUSH PRIVILEGES;
USE demo;