DROP TABLE IF EXISTS `pls_u_user`;
CREATE TABLE `pls_u_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `telephone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `account_expired` tinyint(1) DEFAULT NULL COMMENT '账号过期',
  `account_locked` tinyint(1) DEFAULT NULL COMMENT '账号锁定',
  `pwd_expired` tinyint(1) DEFAULT NULL COMMENT '密码过期',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`) USING HASH,
  KEY `idx_telephone` (`telephone`) USING HASH,
  KEY `idx_add_time` (`add_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_authority`;
CREATE TABLE `pls_u_authority` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `authority` varchar(128) DEFAULT NULL COMMENT '标识',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_authority_set`;
CREATE TABLE `pls_u_authority_set` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `authority_id` bigint(20) unsigned NOT NULL COMMENT '权限编号',
  `target` bigint(20) unsigned NOT NULL COMMENT '目标编号',
  `target_type` tinyint(1) DEFAULT NULL COMMENT '目标类型',
  `add_user` bigint(20) unsigned NOT NULL COMMENT '添加用户',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`),
  KEY `idx_authority_id` (`authority_id`) USING HASH,
  KEY `idx_target` (`target`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_menu`;
CREATE TABLE `pls_u_menu` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `p_id` bigint(20) unsigned NOT NULL COMMENT '父级编号',
  `title` varchar(20) DEFAULT NULL COMMENT '菜单名',
  `icon` varchar(20) DEFAULT NULL COMMENT '菜单icon',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `disabled` tinyint(1) DEFAULT NULL COMMENT '是否显示禁用',
  `type` tinyint(1) DEFAULT NULL COMMENT '菜单类型',
  `url` varchar(128) DEFAULT NULL COMMENT '菜单url',
  `add_user` bigint(20) unsigned NOT NULL COMMENT '添加用户',
  `upd_user` bigint(20) unsigned NOT NULL COMMENT '更新用户',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_p_id` (`p_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_menu_set`;
CREATE TABLE `pls_u_menu_set` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单编号',
  `target` bigint(20) unsigned NOT NULL COMMENT '目标',
  `target_type` tinyint(1) DEFAULT NULL COMMENT '目标类型',
  `add_user` bigint(20) unsigned NOT NULL COMMENT '添加用户',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`),
  KEY `idx_menu_id` (`menu_id`) USING HASH,
  KEY `idx_target` (`target`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_role`;
CREATE TABLE `pls_u_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `pls_u_role_set`;
CREATE TABLE `pls_u_role_set` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色编号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户编号',
  `add_user` bigint(20) unsigned NOT NULL COMMENT '添加用户',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`) USING HASH,
  KEY `idx_user_id` (`user_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;