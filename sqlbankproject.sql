create database sqlbankproject;
use sqlbankproject;

create table customers
(customer_id int primary key auto_increment,
name varchar(20),
city varchar(20));

create table accounts(
account_id int primary key auto_increment,
customer_id int,
account_type varchar(20),
balance decimal(10,2),
foreign key(customer_id) references customers(customer_id));

insert into customers(name,city) values('kannika','banglore'),
('dushyanth','tumkur'),('deekshan','tumkur'),('samiksha','bidar');

insert into accounts(customer_id,account_type,balance) values (1,'Savings',50000),
(1,'Current',20000),
(2,'Savings',30000),
(3,'Savings',15000),
(4,'Current',40000);

select * from customers;
select * from accounts;


-- 1.	Retrieve all accounts with balance greater than 20,000.
select *
from accounts where  balance>20000;


-- 2.	Find customers who live in Chennai. 

select *
from customers where city = 'tumkur';

-- 3.	Display accounts with balance between 20,000 and 50,000.
select * 
from accounts where balance between 20000 and 50000; 

-- 4.	Find customers whose names start with 'J'. 
select *
from customers where name like 'k%';


-- 5.	Retrieve accounts of type 'Savings' or 'Current'. 
select * 
from accounts where account_type='Savings' || account_type='Current'; 

-- 6.	Display accounts that are not 'Savings'. 
select * 
from accounts where account_type not in ('savings'); 


-- 7.	Find customers whose names contain the letter 'a'.
 select *
from customers where name like '%a%';

-- 8.	Retrieve accounts with balance less than or equal to 30,000.
select *
from accounts where  balance<=30000; 
-- 9.	Find customers who are not from Madurai. 
select * 
from customers where city not in ('tumkur'); 

-- 10.	Display accounts where balance is not between 10,000 and 40,000. 
select * 
from accounts where balance not between 10000 and 40000; 

-- 16.	Display all accounts sorted by balance in descending order. 
select * 
from accounts
order by balance;

-- 17.	List customers sorted alphabetically by name. 
select * 
from customers
order by name;

-- 18.	Display accounts sorted by account type and then by balance (descending). 
select * 
from accounts
order by account_type and balance desc;

--  Aggregate Functions (19–23)
-- 19.	Find the total balance of all accounts. 
select sum(balance) from accounts; 


-- 20.	Calculate the average balance of accounts. 
select avg(balance) from accounts; 

-- 21.	Find the maximum account balance. 
select max(balance) from accounts; 

-- 22.	Find the minimum account balance. 
select min(balance) from accounts; 

-- 23.	Count the total number of customers.
select count(balance) from accounts;  


-- GROUP BY + HAVING (24–28)
-- 24.	Find total balance grouped by account type. 
select account_type,sum(balance)  from accounts group by account_type;

-- 25.	Find average balance for each account type. 
select account_type,avg(balance)  from accounts group by account_type;

-- 26.	Display account types having average balance greater than 20,000.
select account_type,avg(balance)  from accounts group by account_type having avg(balance) >20000; 

-- 27.	Count number of accounts for each customer. -- 
select customer_id,count(*) from customers group by customer_id;

-- 28.	Display customers having more than one account. 
select customer_id,count(*) from customers group by customer_id;

-- 29.	Retrieve customer names along with their account balances. 
select  c.name, a.balance from customers c join accounts a  on c.customer_id=a.customer_id;

-- 30.	Display all customers and their accounts (including customers without accounts). 
select c.*, a.account_id, a.account_type, a.balance from customers c left join accounts a on c.customer_id=a.customer_id;

-- 31.	Display all accounts and corresponding customer details. 
select a.*,  c.name, c.city from customers c left join accounts a on c.customer_id=a.customer_id;

-- 32.	Retrieve customer names and account types where balance is greater than 20,000. 
select c.name,a.account_type from customers c join  accounts a on c.customer_id=a.customer_id where balance >20000;

-- 33.	List customers with their total balance using JOIN. 
select c.customer_id, c.name,sum(balance) from customers c join  accounts a on c.customer_id=a.customer_id GROUP BY c.customer_id, c.name;

-- 34.	Display customer names and balances sorted by balance. 
select  c.name,a.balance from customers c join  accounts a on c.customer_id=a.customer_id order BY a.balance;

-- 35.	Count number of accounts for each city using JOIN. 
select  c.city,count(a.customer_id) from customers c join  accounts a on c.customer_id=a.customer_id group BY c.city;


-- 36.	Find accounts with balance greater than average balance. 
select * from accounts where balance>(select avg(balance) from accounts);

-- 37.	Retrieve customers who have accounts. 
select * from accounts where customer_id in (select customer_id from accounts);

-- 38.	Find customers who do not have any accounts. 
select * from accounts where customer_id not in (select customer_id from accounts);

-- 39.	Display account(s) with the maximum balance. 
select * from accounts where balance= (select max(balance) from accounts);

-- 40.	Find customers whose total balance is greater than 40,000. 
select c.* ,sum(a.balance) from customers c join accounts a on c.customer_id=a.customer_id GROUP BY c.customer_id, c.name having sum(a.balance)>40000; 


















