DROP TABLE IF EXISTS `pls_d_dashboard`;
CREATE TABLE `pls_d_dashboard`
(
    `id`          bigint(20) unsigned           NOT NULL COMMENT '编号',
    `label`       varchar(32)         DEFAULT NULL COMMENT 'label',
    `name`        varchar(32)         DEFAULT NULL COMMENT '名称',
    `sort`        int                 DEFAULT 0 comment '顺序',
    `height`      varchar(10)         DEFAULT NULL COMMENT '高度',
    `width`       varchar(10)         DEFAULT NULL COMMENT '宽度',
    `type`        tinyint             DEFAULT 0 NOT NULL COMMENT '类型',
    `template_id` bigint(20) unsigned DEFAULT NULL COMMENT '模板编号',
    `content`     longtext            DEFAULT NULL COMMENT '内容',
    `add_user`    bigint(20) unsigned DEFAULT NULL COMMENT '添加用户',
    `upd_user`    bigint(20) unsigned DEFAULT NULL COMMENT '更新用户',
    `add_time`    datetime            DEFAULT NULL COMMENT '新增时间',
    `upd_time`    datetime            DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_label` (`label`) USING HASH,
    KEY `idx_template_id` (`template_id`) USING HASH
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `pls_d_dashboard_data`;
CREATE TABLE `pls_d_dashboard_data`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '编号',
    `template_id` bigint(20) unsigned NOT NULL COMMENT '模板编号',
    `label`       varchar(32) DEFAULT NULL COMMENT 'label',
    `uri`         varchar(32) DEFAULT NULL COMMENT 'uri',
    `params`      text        DEFAULT NULL COMMENT '参数',
    PRIMARY KEY (`id`),
    KEY `idx_template_id` (`template_id`) USING HASH
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `pls_d_dashboard_template`;
CREATE TABLE `pls_d_dashboard_template`
(
    `id`       bigint(20) unsigned           NOT NULL COMMENT '编号',
    `name`     varchar(32)         DEFAULT NULL COMMENT '名称',
    `type`     tinyint             DEFAULT 0 NOT NULL COMMENT '类型',
    `content`  longtext            DEFAULT NULL COMMENT '内容',
    `remark`   varchar(128)        DEFAULT NULL COMMENT '备注',
    `add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增用户',
    `upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '修改用户',
    `add_time` datetime            DEFAULT NULL COMMENT '新增时间',
    `upd_time` datetime            DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;