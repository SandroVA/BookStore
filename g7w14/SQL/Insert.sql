insert into customer(title, l_name, f_name, company, address1, city, province, country, postal_code,
home_phone,cell_phone,email,categoryId, userId)
values('Mrs.', 'Antonova','Desislava', 'Arg Service', '1584 Saint Denis, apt.224',
'Montreal', 'Quebec', 'Canada', 'H24 1X8', '514-987-8521', '514-556-5874', 'dessiG@gmail.com',2,7),
('Mr.','Gagne','Robert','ITHQ','3535 Barley','Montreal','Quebec','Canada','H3X 3P1','514-654-4862','514-365-9841',
'rgagne@ithq.qc.ca',3,8),
('Mr','Johnes','Mark','ColbaNet','1500 Cote-Des-Neiges','Montreal','Quebec','Canada','H3J 3L2',
'514-753-2147',null,'mjh36@yahoo.com',4,9),
('Mr.','Lavalee','Tom',null,'1281 Elendale, apt.418','Montreal','Quebec','Canada','H3J 3L8','514-265-3574',
'514-256-1596','tll@gmail.com',1,10),
('Mr.','Milanov','Kamen','Kater Sport','6565 Cote-Des-Neiges','Montreal','Quebec','Canada','H3S 1L9',
'514-268-8741','514-652-7569','kmil@gmail.com',2,11),
('Mr.','Stefanov','Todor','DIM Security','3440 Place Decelles apt.308','Montreal','Quebec','Canada','H3S 1X4',
'514-598-1236','514-256-8852','tts@gmail.com',3,12),
('Miss','Thomas','Maria','DIM Security','659 Mont Claire apt.206','Toronto','Ontario','Canada','N9K 5J1',
'598-564-2123',null,'maria_t@gmail.com',4,13),
('Mr.','Todd','Igor','Alkabra','1800 Marshal str.','Toronto','Ontario','Canada','N3L 1S5',
'598-359-1245','598-425-7896','ittd@yahoo.com',3,14);

insert into book(ISBN,FormatId,Title,PublisherId,Number_copies,Wholesale_price,List_price,
CategoryId, Weight,Dimensions,Removal_Status,Number_Pages)
values('9780471722618',1,'COBOL for the 21st Century',18,5,53.12,81.99,5,1724,'20.58/26.46/2.54',1,832),
('9781598638684',1,'UML For The IT Business Analyst',20,8,18.25,30.66,5,816,'18/25/2.5',1,400),
('9781118311813',1,'Beginning Visual Basic 2012',19,4,12.60,28.68,5,1089,'23.4/18.5/3.56',1,744),
('9780470371749',1,'Beginning Programming with Java For Dummies',21,3,9.63,18.78,5,635,'25/40/2.3',1,456),
('9781449392772',1,'Programming PHP',22,6,10.55,27.12,5,907,'18/30/2',1,540),
('9781890774660',1,'Murach\'s HTML5 and CSS3 ',23,9,21.60,36.89,5,1542,'23/32/2',1,656),
('9780596517748',1,'JavaScript: The Good Parts ',22,4,7.00,18.44,5,635,'25/35/2.8',1,176),
('9781449397227',1,'jQuery Pocket Reference',22,12,3.25,10.1,5,1814,'18/28/2.4',1,160),
('9780071492164',1,'Ajax: The Complete Reference',24,3,22.58,38.84,5,1089,'25/40/3.5',1,654),
('9780804842358',1,'Chinese for Beginners: Mastering Conversational Chinese',25,15,5.80,14.39,6,499,'/30/45/2.1',1,192),
('9781492241935',1,'French. Learn the Easy Way',26,8,2.14,6.99,6,1157,'30/42/3',1,114),
('9780804841009',1,'Korean for Beginners: Mastering Conversational Korean',25,12,4.98,13.87,6,454,'24/35/1.5',1,176),
('9781857336146',1,'Japan - Culture Smart!: The Essential Guide to Customs & Culture',27,6,3.90,8.96,7,903,'20/35/1.5',1,168),
('9784805311295',1,'A Geek in Japan: Discovering the Land of Manga, Anime, Zen, and the Tea Ceremony',25,18,5.19,14.34,7,590,'18/30/1',1,160),
('9780521369183',1,'The Languages of Japan (Cambridge Language Surveys)',28,7,39.56,56.19,6,680,'20/35/3',1,428),
('9780321827333',1,'Adobe Photoshop CS6 Classroom in a Book',29,9,16.89,31.23,5,816,'18/30/2.8',1,432),
('9782218065910',2,'Complete Guide to Conjugating 12000 French Verbs (English Edition)',30,10,8.19,18.68,6,856,'18/30/2.6',1,174),
('9782245874151',2,'La conjugaison pour tous ; L\'orthographe pour tous ; La grammaire pour tous : Coffret 3 volumes',31,13,41.99,63.73,6,1225,'30/42/3.8',1,900),
('9781908843357',2,'Guinness World Records 2014 ',32,5,7.20,15.33,8,1225,'30/40/4',1,472),
('9781904994879',2,'Guinness World Records 2013',32,5,5.40,11.99,8,1270,'30/40/4',1,472);



INSERT INTO `g7w14`.`orders` (`CustomerId`, `Order_Date`, `Ship_Date`, `Title`, `SL_Name`, `SF_Name`, `SCompany`, `SAddress1`, `SAddress2`, `SCity`, `SProvince`, `SCountry`, `SPostal_Code`) VALUES ('9', '2014-01-03', '2014-01-05', 'Mrs.', 'Antonova', 'Desislava', 'Kalatea', '1584 Saint Denis apt.224', ' ', 'Montreal', 'Quebec', 'Canada', 'H2L 1X8');
INSERT INTO `g7w14`.`orders` (`CustomerId`, `Order_Date`, `Ship_Date`, `Title`, `SL_Name`, `SF_Name`, `SCompany`, `SAddress1`, `SAddress2`, `SCity`, `SProvince`, `SCountry`, `SPostal_Code`) VALUES ('12', '2014-02-08', '2014-01-11', 'Mr.', 'Lavalee', 'Tom', 'DIM Security', '1281  Elendale apt.418', ' ', 'Montreal', 'Quebec', 'Canada', 'H3J 3L8');
INSERT INTO `g7w14`.`orders` (`CustomerId`, `Order_Date`, `Ship_Date`, `Title`, `SL_Name`, `SF_Name`, `SCompany`, `SAddress1`, `SAddress2`, `SCity`, `SProvince`, `SCountry`, `SPostal_Code`) VALUES ('14', '2014-02-16', '2014-02-18', 'Mr.', 'Stefanov', 'Todor', 'DIM Security', '3440 Place Decelles, apt.308', ' ', 'Montreal', 'Quebec', 'Canada', 'H3S 1X4');
INSERT INTO `g7w14`.`orders` (`CustomerId`, `Order_Date`, `Ship_Date`, `Title`, `SL_Name`, `SF_Name`, `SCompany`, `SAddress1`, `SAddress2`, `SCity`, `SProvince`, `SCountry`, `SPostal_Code`) VALUES ('16', '2014-03-01', '2014-03-03', 'Mr.', 'Todd', 'Igor', 'Zimmer Ltd', '1800 Marshar str.', ' ', 'Toronto', 'Ontarion', 'Canada', 'N3L 1S5');


INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('58', '21');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('59', '21');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('60', '21');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('61', '22');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('62', '23');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('63', '24');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('64', '25');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('65', '25');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('66', '25');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('67', '26');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('68', '26');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('69', '27');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('70', '28');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('71', '29');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('72', '30');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('73', '30');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('74', '31');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('75', '32');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('76', '32');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('77', '33');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('78', '34');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('82', '35');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('79', '36');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('80', '37');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('80', '38');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('81', '39');
INSERT INTO `g7w14`.`bookauthor` (`AuthorId`, `BookId`) VALUES ('81', '40');


INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('1', '6', '4', '48.99');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('1', '7', '1', '40');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('1', '16', '1', '28.99');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('1', '18', '1', '24.95');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('2', '2', '1', '6.99');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('2', '14', '1', '17.00');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('2', '32', '1', '13.87');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('3', '6', '2', '48.99');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('3', '32', '1', '13.87');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('3', '28', '1', '10.10');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('3', '36', '1', '17.00');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('4', '15', '1', '17.95');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('4', '19', '1', '20.95');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('4', '39', '1', '15.33');
INSERT INTO `g7w14`.`orderitem` (`OrderId`, `BookId`, `Quantity`, `Price`) VALUES ('4', '27', '1', '18.44');

INSERT INTO `g7w14`.`invoice` (`Quantity`, `OrderId`, `Net_Value`, `PST`, `GST`, `Total_Gross`) VALUES ('4', '1', '142.93', '7.15', '7.15', '157.22');
INSERT INTO `g7w14`.`invoice` (`Quantity`, `OrderId`, `Net_Value`, `PST`, `GST`, `Total_Gross`) VALUES ('3', '2', '37.86', '1.89', '1.89', '41.65');
INSERT INTO `g7w14`.`invoice` (`Quantity`, `OrderId`, `Net_Value`, `PST`, `GST`, `Total_Gross`) VALUES ('5', '3', '138.95', '6.95', '6.95', '152.85');
INSERT INTO `g7w14`.`invoice` (`Quantity`, `OrderId`, `Net_Value`, `HST`, `Total_Gross`) VALUES ('4', '4', '72.67', '9.45', '82.12');


