DROP TABLE IF EXISTS test;

CREATE TABLE `test` (
  `id` int AUTO_INCREMENT  PRIMARY KEY,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `create_date` date DEFAULT NULL
);

INSERT INTO `test` (`name`,`email`,`mobile_number`,`create_date`)
 VALUES ('Test name','template@rbc.com','9876548337',CURDATE());
