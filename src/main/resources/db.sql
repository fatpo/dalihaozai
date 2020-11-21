create database if not exists dalihaozai;
use dalihaozai;
DROP TABLE IF EXISTS activity;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) NOT NULL comment '活动标题',
  `detail` text NULL comment '活动详情',
  `link_url` varchar(1024) NOT NULL comment '跳转链接',
  `category` varchar(64) NOT NULL comment '分类',
  `address` text  NOT NULL comment '地址',
  `price` int(11) NOT NULL DEFAULT 0 comment '价格',
  `start_date` date NOT NULL  DEFAULT '2023-01-01' comment '活动开始日期',
  `end_date` date NOT NULL DEFAULT '2023-01-01' comment '活动结束日期，默认遥远的未来',
  `start_time` varchar(32) NOT NULL DEFAULT '00:00:00' comment '活动开始时间',
  `end_time` varchar(32) NOT NULL DEFAULT '00:00:00' comment '活动结束时间',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_idx` (`title`(128))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

insert into activity(title, detail, link_url, category, address, price) values ('测试title', '天气很好的内容！', 'http://www.baidu.com', '健康', '大新南山深圳 101', 9999);
