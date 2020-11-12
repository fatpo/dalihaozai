DROP TABLE IF EXISTS activity;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) NOT NULL comment '活动标题',
  `detail` text NOT NULL comment '活动详情',
  `link_url` varchar(1024) NOT NULL comment '活动跳转链接',
  `begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '活动开始时间，默认今天',
  `end_time` timestamp NOT NULL DEFAULT '2037-01-01 12:00:00' comment '活动结束时间，默认遥远的未来',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_idx` (`title`(20))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

comment activity.title '';

insert into activity(title, content, link_url) values ('测试title', '天气很好的内容！', 'http://www.baidu.com');
