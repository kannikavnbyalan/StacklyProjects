create database SQLrailwayreseervationproject;
use SQLrailwayreseervationproject;

create table trains
(train_id int primary key auto_increment,
train_name varchar(20),
source varchar(20),
destination varchar(20));

create table bookings
(booking_id int primary key auto_increment,
train_id int,
passenger_name varchar(20),
fare decimal(10,2),
status varchar(20),
foreign key(train_id) references trains(train_id));

insert into trains(train_name,source,destination) values ('Express1','banglore','tumkur'),
('Express2','bidar','banglore'),
('Express3','mysore','mumbai');

insert into bookings(train_id, passenger_name, fare, status) values (1,'kannika',500,'Confirmed'),
(1,'dushyanth',500,'Waiting'),
(2,'deekshan',300,'Confirmed'),
(3,'Karthik',450,'Cancelled'),
(2,'sona',300,'Confirmed');



--  Basic + WHERE + Operators (41–45)
-- 41.	Retrieve all bookings with fare greater than 400.
select * from bookings where fare>400; 

-- 42.	Find bookings where status is not 'Confirmed'. 
select * from bookings where status = 'Confirmed'; 

-- 43.	Display trains starting from Chennai. 
select * from trains where source = 'bidar'; 

-- 44.	Retrieve bookings with fare between 300 and 500. 
select * from bookings where fare between 300 and 500; 

-- 45.	Find passengers whose names start with 'A'. //no name starts from ainmy table
select * from bookings where passenger_name like 'k%'; 
 

--  JOINS + GROUP BY + ORDER BY (46–48)
-- 46.	Retrieve train names along with passenger names. 
select t.train_name,b.passenger_name from trains t join bookings b on t.train_id = b.train_id;

-- 47.	Count number of bookings for each train. 
select t.train_name,count(b.booking_id) from trains t join bookings b on t.train_id = b.train_id group by t.train_id;

-- 48.	Display train names and total fare collected for each train.
select t.train_name,sum(b.fare) from trains t join bookings b on t.train_id = b.train_id group by t.train_id;
 
--  SUBQUERIES (49–50)
-- 49.	Find bookings with fare equal to the highest fare. 
select * from bookings where fare = (select max(fare) from bookings);

-- 50.	Retrieve trains that have more than one booking. 
select t.* ,count(b.booking_id) from trains t join bookings b on t.train_id = b.train_id group by t.train_id having count(b.booking_id)>1;





