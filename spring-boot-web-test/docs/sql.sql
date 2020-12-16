
DROP TABLE t_user_inf;
CREATE TABLE t_user_inf (
        id                                      BIGINT(20)     UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '主键',
        user_id                                 VARCHAR(60)    BINARY     NOT NULL DEFAULT ''   COMMENT '用户编号id',
        name                                    VARCHAR(30)    BINARY     NOT NULL DEFAULT 0    COMMENT '姓名',
        mobile_no                               VARCHAR(30)    BINARY     NOT NULL DEFAULT 0    COMMENT '手机号',
        birth                                   INT(8)         UNSIGNED   NOT NULL DEFAULT 0    COMMENT '生日',
        sex                                     TINYINT(3)     UNSIGNED   NOT NULL DEFAULT 0    COMMENT '性别：0男 1女',
        crt_ts                                  DATETIME(6)    NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
    PRIMARY KEY (id) COMMENT '',
    UNIQUE KEY `i_t_user_inf_1` (user_id) COMMENT '',
    UNIQUE KEY `i_t_user_inf_2` (mobile_no) COMMENT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;