DROP TABLE IF EXISTS `pls_f_info`;
CREATE TABLE `pls_f_info`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '编号',
    `origin_name` varchar(256) DEFAULT NULL COMMENT '文件名',
    `extension`   varchar(128) DEFAULT NULL COMMENT '文件扩展名',
    `file_size`   int          DEFAULT NULL COMMENT '文件大小',
    `md5`         varchar(64)  DEFAULT NULL COMMENT 'md5',
    `module`      varchar(64)  DEFAULT NULL COMMENT '模块',
    `status`        tinyint DEFAULT NULL COMMENT '文件状态',
    `sensibility`   tinyint(1)   DEFAULT 0 COMMENT '敏感文件',
    `rest_times`  int          DEFAULT -1 COMMENT '剩余下载次数',
    `expire_time` datetime     DEFAULT NULL COMMENT '过期时间',
    `add_time`    datetime     DEFAULT NULL COMMENT '新增时间',
    `add_user`    bigint(20) unsigned DEFAULT NULL COMMENT '上传用户',
    PRIMARY KEY (`id`),
    KEY `idx_md5` (`md5`) USING HASH,
    KEY `idx_module` (`module`) USING HASH,
    KEY `idx_add_user` (`add_user`) USING HASH,
    KEY `idx_add_time` (`add_time`) USING BTREE,
    KEY `idx_expire_time` (`expire_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `pls_f_belonging`;
CREATE TABLE `pls_f_belonging`
(
    `id`                bigint(20) unsigned NOT NULL COMMENT '编号',
    `file_id`           bigint(20) unsigned NOT NULL COMMENT '文件编号',
    `organization`      bigint(20) unsigned NOT NULL COMMENT '组织',
    `organization_type` tinyint DEFAULT NULL COMMENT '组织类型',
    PRIMARY KEY (`id`),
    KEY `idx_file_id` (`file_id`) USING HASH,
    KEY `idx_organization` (`organization`) USING HASH
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;