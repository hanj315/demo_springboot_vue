INSERT INTO `USER`
  (`ID`,`USERNAME`,`PASSWORD`,`PROFILE_ID`,`STATUS`,`IS_DELETED`,`CREATED_AT`,`CREATED_BY`,`UPDATED_AT`,`UPDATED_BY`)
VALUES
  (1,'admin','$2a$10$pqRCym5FQB4nneciWwUMc.gEDRfRKiOopfNH7cBgrBgLdXncNppCq',1,'',0,'2018-06-05',0,NULL,NULL),
  (2,'user','$2a$10$pqRCym5FQB4nneciWwUMc.gEDRfRKiOopfNH7cBgrBgLdXncNppCq',2,'',0,'2018-06-05',0,NULL,NULL);

INSERT INTO `PROFILE`
(`ID`,`FULLNAME`,`CREATED_AT`,`CREATED_BY`,`UPDATED_AT`,`UPDATED_BY`)
VALUES
  (1,'超级管理员','2018-06-05',0,NULL,NULL),
  (2,'用户1','2018-06-05',0,NULL,NULL);