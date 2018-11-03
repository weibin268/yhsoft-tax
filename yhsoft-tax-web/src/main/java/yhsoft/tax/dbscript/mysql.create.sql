
DROP TABLE IF EXISTS `sys_autocode`;
CREATE TABLE `sys_autocode` (
  `id` varchar(50) NOT NULL,
  `expression` varchar(500) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_autocode_detail`;
CREATE TABLE `sys_autocode_detail` (
  `id` varchar(50) NOT NULL,
  `autocode_id` varchar(50) NOT NULL,
  `prefix_code` varchar(100) DEFAULT NULL,
  `seq` int(11) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` varchar(50) NOT NULL,
  `code` varchar(100) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='数据字典主表';

DROP TABLE IF EXISTS `sys_dictionary_item`;
CREATE TABLE `sys_dictionary_item` (
  `id` varchar(50) NOT NULL,
  `dictionary_id` varchar(50) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `text` varchar(200) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_error_log`;
CREATE TABLE `sys_error_log` (
  `id` varchar(50) NOT NULL,
  `log_name` varchar(500) DEFAULT '' COMMENT '日志名',
  `level` varchar(100) NOT NULL DEFAULT '' COMMENT '优先级',
  `message` varchar(1000) DEFAULT NULL,
  `stack_info` text,
  `log_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CreatedTime` (`created_time`)
) COMMENT='日志表';

DROP TABLE IF EXISTS `sys_fileupload`;
CREATE TABLE `sys_fileupload` (
  `id` varchar(50) NOT NULL,
  `template_id` varchar(50) NOT NULL,
  `biz_id` varchar(50) NOT NULL,
  `save_full_path` varchar(1000) NOT NULL,
  `orgin_file_name` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_created_time` (`created_time`)
) COMMENT='文件上传记录表';

DROP TABLE IF EXISTS `sys_fileupload_template`;
CREATE TABLE `sys_fileupload_template` (
  `id` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `save_dir` varchar(500) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_created_time` (`created_time`)
) COMMENT='文件上传模板表';

DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `user_login_id` varchar(50) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `client_ip` varchar(100) DEFAULT NULL,
  `user_agent` varchar(1000) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CreatedTime` (`created_time`)
) COMMENT='登录日志表';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `full_path` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='系统菜单表';

DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) DEFAULT NULL COMMENT '操作用户Id',
  `user_login_id` varchar(50) DEFAULT NULL COMMENT '操作用户账号',
  `user_name` varchar(20) DEFAULT NULL COMMENT '操作用户名',
  `module` varchar(250) DEFAULT NULL COMMENT '模块',
  `module_name` varchar(500) DEFAULT NULL COMMENT '模块名称',
  `action` varchar(100) DEFAULT NULL COMMENT '操作',
  `action_name` varchar(200) DEFAULT NULL COMMENT '操作名称',
  `data_info` varchar(2000) DEFAULT NULL COMMENT '数据信息',
  `created_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `IDX_CreatedTime` (`created_time`)
) COMMENT='操作日志';

DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` varchar(50) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `full_path` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `expression` varchar(1000) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `full_path` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `level` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='系统权限表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='系统角色表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(50) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `permission_id` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='角色权限关系表';

DROP TABLE IF EXISTS `sys_subsystem`;
CREATE TABLE `sys_subsystem` (
  `id` varchar(50) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  `permission_id` varchar(50) DEFAULT NULL,
  `db_type` int(11) DEFAULT NULL,
  `db_url` varchar(500) DEFAULT NULL,
  `db_user_name` varchar(100) DEFAULT NULL,
  `db_password` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='业务子系统表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `login_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'q',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '1、F=女；2、M=男；',
  `org_id` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `modified_by` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='用户角色关系表';

DROP TABLE IF EXISTS `tes_user`;
CREATE TABLE `tes_user` (
  `id` varchar(50) NOT NULL,
  `login_id` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL COMMENT '1、F=女；2、M=男；',
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `org_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
