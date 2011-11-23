DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `item_id` int(11) NOT NULL,
  `price` float NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `importers`;
CREATE TABLE `importers` (
  `importer_id` varchar(11) NOT NULL DEFAULT '0',
  `user_name` varchar(255) NOT NULL DEFAULT '',
  `credentials` text,
  `date` timestamp NULL DEFAULT NULL,
  `statusMessage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`importer_id`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asin` varchar(20) NOT NULL DEFAULT '',
  `site` varchar(11) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `asin` (`asin`,`site`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `observations`;
CREATE TABLE `observations` (
  `item_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT '',
  `name` varchar(255) DEFAULT '',
  `mode` varchar(255) DEFAULT NULL,
  `limit` float DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;