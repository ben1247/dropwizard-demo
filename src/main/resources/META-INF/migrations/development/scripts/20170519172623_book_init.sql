-- // book init
-- Migration SQL that makes the change goes here.
CREATE TABLE `book` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` VARCHAR(128) NOT NULL COMMENT '名称',
  `price` DECIMAL(11,2) NOT NULL COMMENT '价格',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX idx_name(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE `book`;