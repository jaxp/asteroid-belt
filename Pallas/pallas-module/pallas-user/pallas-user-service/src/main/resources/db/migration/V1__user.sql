DROP TABLE IF EXISTS `pls_u_user`;
CREATE TABLE `pls_u_user`
(
    `id`              bigint(20) unsigned NOT NULL COMMENT '编号',
    `username`        varchar(32)  DEFAULT NULL COMMENT '用户名',
    `password`        varchar(128) DEFAULT NULL COMMENT '密码',
    `telephone`       varchar(32)  DEFAULT NULL COMMENT '手机号',
    `email`           varchar(32)  DEFAULT NULL COMMENT '邮箱',
    `add_time`        datetime     DEFAULT NULL COMMENT '新增时间',
    `upd_time`        datetime     DEFAULT NULL COMMENT '更新时间',
    `enabled`         tinyint(1)   DEFAULT NULL COMMENT '是否有效',
    `account_expired` tinyint(1)   DEFAULT NULL COMMENT '账号过期',
    `account_locked`  tinyint(1)   DEFAULT NULL COMMENT '账号锁定',
    `pwd_expired`     tinyint(1)   DEFAULT NULL COMMENT '密码过期',
    PRIMARY KEY (`id`),
    KEY `idx_username` (`username`) USING HASH,
    KEY `idx_telephone` (`telephone`) USING HASH,
    KEY `idx_add_time` (`add_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `pls_db`.`pls_u_user`(`id`, `username`, `password`, `telephone`, `email`, `add_time`, `upd_time`, `enabled`,
                                  `account_expired`, `account_locked`, `pwd_expired`)
VALUES (1000000000000000001, 'admin', '{bcrypt}$2a$10$fH1GkNhHY5xVtC5l1KRm3O.PvkULJbIWqIso1xfHIDPniFaCFeGCC', NULL, NULL, now(), now(), 1,
        0, 0, 0);

DROP TABLE IF EXISTS `pls_u_menu`;
CREATE TABLE `pls_u_menu`
(
    `id`       bigint(20) unsigned NOT NULL COMMENT '编号',
    `pid`      bigint(20) unsigned DEFAULT NULL COMMENT '父级编号',
    `title`    varchar(20)         DEFAULT NULL COMMENT '菜单名',
    `icon`     varchar(20)         DEFAULT NULL COMMENT '菜单icon',
    `enabled`  tinyint(1)          DEFAULT NULL COMMENT '是否可用',
    `disabled` tinyint(1)          DEFAULT NULL COMMENT '是否显示禁用',
    `type`     tinyint             DEFAULT NULL COMMENT '菜单类型',
    `url`      varchar(128)        DEFAULT NULL COMMENT '菜单url',
    `grade`    tinyint             DEFAULT NULL COMMENT '等级',
    `add_user` bigint(20) unsigned NOT NULL COMMENT '添加用户',
    `upd_user` bigint(20) unsigned NOT NULL COMMENT '更新用户',
    `add_time` datetime            DEFAULT NULL COMMENT '添加时间',
    `upd_time` datetime            DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_pid` (`pid`) USING HASH
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `pls_db`.`pls_u_menu`(`id`, `pid`, `title`, `icon`, `enabled`, `disabled`, `type`, `url`, `grade`,
                                  `add_user`, `upd_user`, `add_time`, `upd_time`)
VALUES (1100000000000000001, null, '主页', 'home', 1, 0, 0, '/home', 0, 1000000000000000001, 1000000000000000001, now(), now());
INSERT INTO `pls_db`.`pls_u_menu`(`id`, `pid`, `title`, `icon`, `enabled`, `disabled`, `type`, `url`, `grade`,
                                  `add_user`, `upd_user`, `add_time`, `upd_time`)
VALUES (1100000000000000002, null, '系统管理', 'project', 1, 0, 1, null, 0, 1000000000000000001, 1000000000000000001, now(), now());
INSERT INTO `pls_db`.`pls_u_menu`(`id`, `pid`, `title`, `icon`, `enabled`, `disabled`, `type`, `url`, `grade`,
                                  `add_user`, `upd_user`, `add_time`, `upd_time`)
VALUES (1100000000000000003, 1100000000000000002, '菜单管理', 'menu', 1, 0, 0, '/menu', 0, 1000000000000000001, 1000000000000000001, now(), now());

DROP TABLE IF EXISTS `pls_u_role`;
CREATE TABLE `pls_u_role`
(
    `id`       bigint(20) unsigned NOT NULL COMMENT '编号',
    `pid`      bigint(20) unsigned DEFAULT NULL COMMENT '父角色',
    `role`     varchar(20)         DEFAULT NULL COMMENT '角色',
    `name`     varchar(20)         DEFAULT NULL COMMENT '名称',
    `grade`    tinyint             DEFAULT NULL COMMENT '等级',
    `enabled`  tinyint(1)          DEFAULT NULL COMMENT '是否可用',
    `add_time` datetime            DEFAULT NULL COMMENT '新增时间',
    `upd_time` datetime            DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `pls_u_role`(`id`, `pid`, `role`, `name`, `grade`, `enabled`, `add_time`, `upd_time`)
VALUES (1110000000000000001, null, 'admin', '超级管理员', 0, 1, now(), now());

DROP TABLE IF EXISTS `pls_u_authority`;
CREATE TABLE `pls_u_authority`
(
    `id`                bigint(20) unsigned NOT NULL COMMENT '编号',
    `authority`         varchar(128) DEFAULT NULL COMMENT '标识',
    `organization`      bigint(20) unsigned NOT NULL COMMENT '组织编号',
    `organization_type` tinyint             NOT NULL COMMENT '组织类型',
    `permission`        tinyint             NOT NULL COMMENT '权限',
    `resource`          bigint(20) unsigned NOT NULL COMMENT '资源',
    `resource_type`     varchar(64)         NOT NULL COMMENT '资源类型',
    `add_user`          bigint(20) unsigned NOT NULL COMMENT '添加人',
    `add_time`          datetime     DEFAULT NULL COMMENT '新增时间',
    PRIMARY KEY (`id`),
    KEY `idx_authority` (`authority`) using BTREE,
    KEY `idx_organization` (`organization`) USING HASH,
    KEY `idx_resource` (`resource`) USING HASH
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `pls_u_authority`(`id`, `authority`, `organization`, `organization_type`, `permission`, `resource`,
                              `resource_type`, `add_user`, `add_time`)
VALUES (1, '超级管理员', 1000000000000000001, 0, 15, 1110000000000000001, 'role', 1000000000000000001, now());
INSERT INTO `pls_u_authority`(`id`, `authority`, `organization`, `organization_type`, `permission`, `resource`,
                              `resource_type`, `add_user`, `add_time`)
VALUES (2, '超级管理员', 1110000000000000001, 1, 15, 0, 'dashboard', 1000000000000000001, now());