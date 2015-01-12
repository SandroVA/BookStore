CREATE TABLE `author` (
  `AuthorId` int(11) NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(50) DEFAULT NULL,
  `Last_Name` varchar(50) NOT NULL,
  PRIMARY KEY (`AuthorId`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

CREATE TABLE `book` (
  `BookId` int(11) NOT NULL AUTO_INCREMENT,
  `ISBN` varchar(13) NOT NULL,
  `FormatId` int(11) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `PublisherId` int(11) NOT NULL,
  `Number_copies` int(5) NOT NULL,
  `Wholesale_price` decimal(5,2) DEFAULT NULL,
  `List_price` decimal(5,2) DEFAULT NULL,
  `Sale_price` decimal(5,2) DEFAULT NULL,
  `CategoryId` int(11) NOT NULL,
  `Weight` int(5) DEFAULT '0',
  `Dimensions` varchar(45) DEFAULT '0',
  `Removal_Status` tinyint(4) NOT NULL,
  `Date_Added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Number_Pages` int(4) DEFAULT NULL,
  PRIMARY KEY (`BookId`),
  KEY `CategoryId_idx` (`CategoryId`),
  KEY `FormatId_idx` (`FormatId`),
  KEY `PublisherId_idx` (`PublisherId`),
  CONSTRAINT `CategoryId` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`CategoryId`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FormatId` FOREIGN KEY (`FormatId`) REFERENCES `format` (`FormatId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PublisherId` FOREIGN KEY (`PublisherId`) REFERENCES `publisher` (`PublisherId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

CREATE TABLE `bookauthor` (
  `AuthorId` int(11) NOT NULL,
  `BookId` int(11) NOT NULL,
  PRIMARY KEY (`AuthorId`,`BookId`),
  KEY `BookId_idx` (`BookId`),
  CONSTRAINT `AuthorId` FOREIGN KEY (`AuthorId`) REFERENCES `author` (`AuthorId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `BookId` FOREIGN KEY (`BookId`) REFERENCES `book` (`BookId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `category` (
  `CategoryId` int(11) NOT NULL AUTO_INCREMENT,
  `Category_Name` varchar(200) NOT NULL,
  PRIMARY KEY (`CategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `customer` (
  `CustomerId` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `Title` varchar(10) NOT NULL,
  `L_Name` varchar(50) NOT NULL,
  `F_Name` varchar(50) NOT NULL,
  `Company` varchar(45) DEFAULT NULL,
  `Address1` varchar(100) NOT NULL,
  `Address2` varchar(100) DEFAULT NULL,
  `City` varchar(35) NOT NULL,
  `Province` varchar(30) NOT NULL,
  `Country` varchar(6) NOT NULL,
  `Postal_Code` varchar(7) NOT NULL,
  `Home_Phone` varchar(12) NOT NULL,
  `Cell_Phone` varchar(12) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  PRIMARY KEY (`CustomerId`),
  KEY `CategoryId_fk_idx` (`CategoryId`),
  KEY `UserId_fk_idx` (`UserId`),
  CONSTRAINT `CategoryId_fk` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`CategoryId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserId_fk` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `customer_review` (
  `ReviewId` int(11) NOT NULL,
  `Review_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CustomerId` int(11) NOT NULL,
  `Rating` int(11) NOT NULL DEFAULT '1',
  `Review_Text` varchar(500) DEFAULT NULL,
  `Approval_Status` tinyint(4) NOT NULL DEFAULT '0',
  `BookId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ReviewId`),
  KEY `CustomerId_idx` (`CustomerId`),
  KEY `BookId_fk_idx` (`BookId`),
  CONSTRAINT `BookId_fk` FOREIGN KEY (`BookId`) REFERENCES `book` (`BookId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CustomerId_fk` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `format` (
  `FormatId` int(11) NOT NULL AUTO_INCREMENT,
  `Type_Format` varchar(15) NOT NULL,
  PRIMARY KEY (`FormatId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `invoice` (
  `InvoiceId` int(11) NOT NULL AUTO_INCREMENT,
  `Quantity` int(10) NOT NULL,
  `OrderId` int(11) NOT NULL,
  `Net_Value` decimal(6,2) NOT NULL,
  `PST` decimal(5,2) NOT NULL,
  `GST` decimal(5,2) NOT NULL,
  `HST` decimal(5,2) NOT NULL,
  `Total_Gross` decimal(6,2) NOT NULL,
  PRIMARY KEY (`InvoiceId`,`OrderId`),
  KEY `OrderId_idx` (`OrderId`),
  CONSTRAINT `OrderId` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`OrderId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orderitem` (
  `OrderId` int(11) NOT NULL,
  `BookId` int(11) NOT NULL,
  `Quantity` int(6) NOT NULL,
  `Price` decimal(5,2) DEFAULT '0.00',
  PRIMARY KEY (`OrderId`),
  KEY `BookId_idx` (`BookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orders` (
  `OrderId` int(11) NOT NULL AUTO_INCREMENT,
  `CustomerId` int(11) NOT NULL,
  `Order_Date` date NOT NULL,
  `Ship_Date` date NOT NULL,
  `Title` varchar(10) NOT NULL,
  `SL_Name` varchar(50) NOT NULL,
  `SF_Name` varchar(50) NOT NULL,
  `SCompany` varchar(100) NOT NULL,
  `SAddress1` varchar(100) NOT NULL,
  `SAddress2` varchar(100) NOT NULL,
  `SCity` varchar(35) NOT NULL,
  `SProvince` varchar(30) NOT NULL,
  `SCountry` varchar(6) NOT NULL,
  `SPostal_Code` varchar(7) NOT NULL,
  PRIMARY KEY (`OrderId`),
  KEY `CustomerId_idx` (`CustomerId`),
  KEY `TitleId_idx` (`Title`),
  CONSTRAINT `CustomerId` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publisher` (
  `PublisherId` int(11) NOT NULL AUTO_INCREMENT,
  `Publisher_Name` varchar(100) NOT NULL,
  PRIMARY KEY (`PublisherId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
