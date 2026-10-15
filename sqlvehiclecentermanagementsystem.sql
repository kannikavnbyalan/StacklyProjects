create database sqlvehicleservicecentermanagement;
use sqlvehicleservicecentermanagement;

create table customers
(customer_id int primary key auto_increment,
customer_name varchar(20),
phone varchar(20),
city varchar(20));


insert into customers(customer_name,phone,city)values
('kannika','9876543210','Chennai'),
('shiva','9876543211','Bangalore'),
('ganga','9876543212','mumbai'),
('jesta','9876543213','delhi'),
('guna','9876543214','kashmir');

create table vehicles (
    vehicle_id int primary key auto_increment,
    customer_id int,
    vehicle_number varchar(15),
    vehicle_model varchar(50),
    vehicle_type varchar(30),
    foreign key(customer_id) references customers(customer_id));

insert into vehicles(customer_id,vehicle_number,vehicle_model,vehicle_type)
VALUES(1,'TN10AB1234','Hyundai i20','Car'),
(2,'KA05XY5678','Honda City','Car'),
(3,'TS08PQ4321','Royal Enfield','Bike'),
(4,'TN22KL9090','Maruti Swift','Car'),
(5,'MH12AA1111','TVS Apache','Bike'),
(1, 'KA06CD2222', 'Tata Nexon', 'Car');

create table mechanics (
    mechanic_id int primary key auto_increment,
    mechanic_name varchar(50),
    specialization varchar(50),
    experience int);

insert into mechanics(mechanic_name,specialization,experience)
values('ramu','Engine',10),
('guru','Electrical',8),
('deesha','General Service',6),
('gani','Painting',12);

create table service_Records (
    service_id int primary key auto_increment,
    vehicle_id int,
    mechanic_id int,
    service_type varchar(50),
    service_date DATE,
    cost decimal(10,2),
   foreign key(vehicle_id) references vehicles(vehicle_id),
    foreign key(mechanic_id) references mechanics(mechanic_id));

insert into service_Records(vehicle_id,mechanic_id,service_type,service_date,cost)
values(1,1,'Engine Repair','2026-07-10',8000),
(1,2,'Electrical Repair','2026-07-20',3000),
(2,3,'General Service','2026-07-21',2500),
(3,1,'Engine Repair','2026-07-15',5000),
(4,3,'General Service','2026-07-18',2200),
(5,2,'Electrical Repair','2026-06-30',1800),
(2,4,'Painting','2026-07-25',7000),
(3,3,'General Service','2026-07-27',2000),
(1, 1, 'General Service', '2026-10-14', 1500),
(1, 4, 'Painting', '2026-10-14', 2500),
(5, 4, 'Painting', '2024-05-10', 4000),
(1, 3, 'Engine Repair', '2026-08-10', 3500);

create table bills (
    bill_id int primary key auto_increment,
    service_id int,
    total_amount decimal(10,2),
    payment_status varchar(20),
     foreign key(service_id) references service_Records(service_id));

insert into bills(service_id,total_amount,payment_status)
values(1,8000,'Paid'),
(2,3000,'Paid'),
(3,2500,'Pending'),
(4,5000,'Paid'),
(5,2200,'Paid'),
(6,1800,'Pending'),
(7,7000,'Paid'),
(8,2000,'Pending'),
(9, 1500, 'Pending'),
(10, 2500, 'Pending'),
(11, 4000, 'Paid');


-- 1. Display vehicles serviced today.
select * from vehicles v join service_Records s on v.vehicle_id=s.vehicle_id where service_date=curdate(); 

-- 2. Find the mechanic handling the most services
select mechanic_name,count(service_id) from mechanics m join service_records s
on m.mechanic_id = s.mechanic_id group by mechanic_name order by count(service_id) desc limit 1;

-- Calculate customer-wise bill amount.
select c.customer_name,sum(b.total_amount) from customers c join vehicles v on c.customer_id=v.customer_id join 
service_records s on v.vehicle_id =s.vehicle_id join bills b
on s.service_id =b.service_id group by customer_name;

-- Find vehicles not serviced in the last year
select * from vehicles where  vehicle_id not in (select vehicle_id from service_Records
where service_date>=DATE_SUB(CURDATE(),INTERVAL 1 YEAR));

-- 5. Display service history for a vehicle.
select * from vehicles v join service_Records s on v.vehicle_id=s.vehicle_id
where vehicle_number='TN10AB1234' order by service_date;

-- 6. Count services by type.
select service_type, COUNT(*) from service_Records group by service_type;

-- . Find customers with more than three visits.
SELECT customer_name, COUNT(service_id) from customers c join vehicles v on c.customer_id=v.customer_id
join service_Records s on v.vehicle_id=s.vehicle_id
group by customer_name
having count(service_id)>3;

-- Display pending payments.
select * from customers c join vehicles v on c.customer_id=v.customer_id join service_Records s
on v.vehicle_id=s.vehicle_id join Bills b on s.service_id=b.service_id
where payment_status='Pending';

-- 9. Find the most common service.
SELECT service_type, COUNT(*) AS Frequency from service_Records
group by service_type order by Frequency desc LIMIT 1;

-- 10. Calculate monthly service revenue.
SELECT month(service_date),sum(total_amount)from service_Records s
join bills b on s.service_id=b.service_id group by month(service_date);


-- Additional Practice Queries
-- 11. Find the highest bill.
select * from bills order by  total_amount desc limit 1;

-- 12. Find the mechanic with the highest revenue.
select m.mechanic_name,sum(b.total_amount) 
from mechanics m join service_Records s on m.mechanic_id=s.mechanic_id
join bills b on s.service_id=b.service_id
group by mechanic_name
order by sum(total_amount) DESC;

-- 13. Display customers who own more than one vehicle.
select customer_name,count(vehicle_id) 
from customers c join vehicles v on c.customer_id=v.customer_id
group by customer_name having count(vehicle_id)>1;

-- 14. Find the average service cost.
select avg(cost)
from service_Records;

-- 15. Find the most experienced mechanic.
select * from mechanics order by experience DESC LIMIT 1;

-- 16. Display all engine repairs.
select * from service_Records where service_type='Engine Repair';

-- 17. Find customers from Chennai.
select * from customers
where city='Chennai';

-- 18. Find all services performed by a particular mechanic.
select mechanic_name,service_type,service_date
from mechanics m  join service_Records s on m.mechanic_id=s.mechanic_id where mechanic_name='guru';

-- 19. Display unpaid bill details with service information.
select b.bill_id, c.customer_name, v.vehicle_number,s.service_type, s.service_date, b.total_amount
from bills b join service_Records s on b.service_id=s.service_id
join vehicles v on s.vehicle_id=v.vehicle_id join customers c on v.customer_id=c.customer_id
where b.payment_status='Pending';

-- 20. Rank mechanics based on the number of services handled.
select mechanic_name, count(service_id),rank() over(order by  count(service_id) desc) 
from mechanics m left join service_Records s on m.mechanic_id=s.mechanic_id
group by  mechanic_name;

