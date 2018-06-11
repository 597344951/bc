CREATE TABLE IF NOT EXISTS `publish_content` (
  `content_id` INT AUTO_INCREMENT COMMENT 'ID',
  `title` VARCHAR(20) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '内容',
  `content_type_id` INT NOT NULL COMMENT '类型',
  `start_date` TIMESTAMP NOT NULL COMMENT '开始时间',
  `end_date` TIMESTAMP NOT NULL COMMENT '结束时间',
  `user_id` INT NOT NULL COMMENT '发起人',
  `relate_content_id` INT NOT NULL COMMENT '关联发布内容',
  `process_item_id` INT NOT NULL COMMENT '当前状态',
  `snapshot` VARCHAR(20) DEFAULT NULL COMMENT '预览',
  `add_date` TIMESTAMP NOT NULL COMMENT '添加时间',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  PRIMARY KEY ( `content_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_content_type` (
  `content_type_id`	INT	NOT NULL COMMENT '类型ID	',
  `name`	VARCHAR(20)	NOT NULL	COMMENT '名称',
  `description`	VARCHAR(32)	NOT NULL	COMMENT '描述',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `content_type_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_process_item` (
  `process_item_id`	INT	NOT NULL COMMENT '流程项ID',
  `name`	VARCHAR(20)	NOT NULL	COMMENT '名称',
  `description`	VARCHAR(30)	NOT NULL	COMMENT '描述',
  `label`	VARCHAR(20)	NOT NULL	COMMENT '显示内容',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `process_item_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_process` (
  `process_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `content_type_id`	INT	NOT NULL	COMMENT '内容类型',
  `process_item_id`	INT	NOT NULL	COMMENT '流程项',
  `process_sort`	INT	NOT NULL	COMMENT '顺序',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `process_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_process_state` (
  `process_state_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `content_id`	INT	NOT NULL	COMMENT '内容',
  `user_id`	INT	NOT NULL	COMMENT '用户',
  `process_item_id`	INT	NOT NULL	COMMENT '流程项',
  `msg`	VARCHAR(128)	NOT NULL	COMMENT '状态信息',
  `remark` VARCHAR(128) DEFAULT NULL COMMENT '备注',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `process_state_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_examine_user` (
  `examine_user_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `user_id`	INT	NOT NULL	COMMENT '用户',
  `content_id`	INT	NOT NULL	COMMENT '发布内容',
  `sort`	INT	NOT NULL	COMMENT '审核顺序',
  `state` INT NOT NULL COMMENT '审核状态（0：审核未开始，1：审核通过，2：审核未通过，3：等待在审核）',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `examine_user_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `material` (
  `material_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `type`	VARCHAR(20)	NOT NULL	COMMENT '类型',
  `content`	TEXT	DEFAULT NULL	COMMENT '内容',
  `url`	VARCHAR(32)	DEFAULT NULL	COMMENT '存储地址',
  `user_id`	INT	NOT NULL	COMMENT '上传用户',
  `upload_reason`	VARCHAR(32)	NOT NULL	COMMENT '上传原因',
  `relate_content_id`	INT	NOT NULL	COMMENT '关联节目',
  `limit_content_id`	INT	DEFAULT NULL	COMMENT '限定使用者',
  `examine_state`	INT	NOT NULL	COMMENT '备选审核状态',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `material_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_more_edit_user` (
  `more_edit_user_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `user_id`	INT	NOT NULL	COMMENT '用户',
  `content_id`	INT	DEFAULT NULL	COMMENT '内容',
  `sort`	INT	NOT NULL DEFAULT 0	COMMENT '顺序',
  `state`	INT	NOT NULL	COMMENT '状态（0：未编辑，1：正在编辑，2：已提交，3：正在编辑）',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `more_edit_user_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `publish_terminal` (
  `id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `terminal_id`	INT	NOT NULL	COMMENT '终端ID',
  `content_id`	INT	DEFAULT NULL	COMMENT '内容ID',
  `add_date`	TIMESTAMP	NOT NULL	COMMENT '添加时间',
  `update_date`	TIMESTAMP	NOT NULL	COMMENT '更新时间',
  PRIMARY KEY ( `id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `log` (
  `log_id`	INT	AUTO_INCREMENT	COMMENT 'ID',
  `level`	VARCHAR(10)	NOT NULL	COMMENT '日志等级',
  `class_name`	VARCHAR(128)	NOT NULL	COMMENT '类名',
  `date`	TIMESTAMP	NOT NULL	COMMENT '时间',
  `msg`	VARCHAR(128)	NOT NULL	COMMENT '日志内容',
  `pid`	VARCHAR(10)	NOT NULL	COMMENT '进程ID',
  `thread_name`	VARCHAR(32)	NOT NULL	COMMENT '线程名称',
  `username`	VARCHAR(20)	NOT NULL	COMMENT '用户名',
  `feature`	VARCHAR(20)	NOT NULL	COMMENT '机能',
  PRIMARY KEY ( `log_id` )
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;