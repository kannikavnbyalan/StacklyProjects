create database SQLEmployeemanagement;
use SQLEmployeemanagement;

create table employee (
   emp_id int primary key,
    emp_name varchar(20),
    department varchar(30),
    salary decimal(10,2),
    city varchar(30),
    joining_date date);
    
insert into employee values (101,'kannika','IT',60000,'Chennai','2022-01-15'),
(102,'dushyanth','HR',45000,'Bangalore','2021-03-10'),
(103,'Sneha','IT',70000,'Chennai','2020-07-12'),
(104,'nikil','Finance',55000,'Mumbai','2023-01-20'),
(105,'suma','HR',48000,'Delhi','2022-05-05'),
(106,'Lisha','Finance',65000,'Mumbai','2021-08-18');


-- 1.	Find the total number of employees in each department. 
select department,count(*) from employee  group by department;

-- 2.	Find the average salary of employees in each department. 
select department,avg(salary) from employee  group by department;

-- 3.	Display departments having more than one employee. 
select department,count(*) from employee  group by department having count(*)>1;

-- 4.	Find the highest salary in each department. 
select department,max(salary) from employee  group by department;

-- 5.	Find the lowest salary in each department.
select department,min(salary) from employee  group by department;

-- 6.	Find departments whose average salary is greater than 50,000. 
select department,avg(salary) from employee  group by department having avg(salary)>50000;

-- 7.	Calculate the total salary expenditure for each department. 
select department,sum(salary) from employee  group by department;

-- 8.	Display all employees sorted by salary in descending order. 
select * from employee  order by salary desc;

-- 9.	Display employees sorted first by department and then by salary in descending order. 
select * from employee  order by department and salary desc;

-- 10.	Find cities that have more than one employee.
 select city,count(emp_id) from employee  group by city having count(emp_id)>1;
 
-- 11.	Find the total salary paid in each city. 
 select city,sum(salary) from employee  group by city;

-- 12.	Display departments ordered by total salary expenditure from highest to lowest. 
select department,sum(salary) from employee  group by department order by sum(salary) desc;

-- 13.	Find the number of employees in each department whose salary is greater than 50,000.
 select count(emp_id),department from employee   where salary>50000 group by department;

-- 14.	Find the difference between the highest and lowest salary in each department. 
select department,max(salary) - min(salary) AS salary_difference from Employee group by department;

-- 15.	Display the top 3 highest-paid employees. 
select * from employee order by salary desc limit 3;




create table customers
(customer_id int primary key,
customer_name varchar(20),
city varchar(20));

create table orders
(order_id int primary key,
customer_id int,
amount decimal(10,2),
order_date DATE,
foreign key(customer_id) references customers(customer_id));

insert into customers values (1,'kannika','banglore'),
(2,'radha','tumkur'),(3,'niriksha','mumbai'),(4,'deepika','goa');

insert into orders values(101,2,50000,'2026-05-04'),(102,3,40000,'2024-03-8'),
(103,1,20000,'2020-07-24'),(104,4,60000,'2023-11-12'),(105,1,25000,'2021-09-26');
insert into orders values (106,2,45000,'2019-09-26');

create table customers
(customer_id int primary key,
customer_name varchar(20),
city varchar(20));

create table orders
(order_id int primary key,
customer_id int,
amount decimal(10,2),
order_date DATE,
foreign key(customer_id) references customers(customer_id));

-- 16.	Find the total order amount for each customer. 
select c.*,sum(amount) from customers c join orders o on c.customer_id=o.customer_id group by order_id;  

-- 17.	Find customers who have placed more than 3 orders. 
select c.*,count(o.order_id) from customers c join orders o on c.customer_id=o.customer_id group by customer_id having count(o.order_id)>1; 

-- 18.	Find the average order amount for each customer.
select c.*,avg(amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id; 
 
-- 19.	Find the highest order amount placed by each customer. 
select c.*,max(amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id; 

-- 20.	Display customers sorted by their total purchase amount.
select c.*,max(amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id order by max(amount) desc;  

-- 21.	Find customers whose total purchase amount exceeds 10,000. 
select c.*,sum(o.amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id having sum(o.amount)>10000;  

-- 22.	Display customer names along with the total number of orders placed. 
select c.*,count(o.order_id) from customers c join orders o on c.customer_id=o.customer_id group by customer_id;  

-- 23.	Find the customer who spent the highest amount.
SELECT c.customer_id, c.customer_name,
       COUNT(o.order_id) AS total_orders
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(o.order_id) = (
    SELECT MAX(order_count)
    FROM (
        SELECT COUNT(*) AS order_count
        FROM Orders
        GROUP BY customer_id
    ) AS order_totals
);

 
-- 24.	Find the customer who placed the maximum number of orders.
select c.*,max(o.order_id) from customers c join orders o on c.customer_id=o.customer_id group by customer_id order by max(o.amount) limit 1;  

 select c.customer_id, c.customer_name,
       COUNT(o.order_id) AS total_orders
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(o.order_id) = (
    SELECT MAX(order_count)
    FROM (
        SELECT COUNT(*) AS order_count
        FROM Orders
        GROUP BY customer_id
    ) AS order_totals
);

-- 25.	Find customers whose average order amount is greater than 2,000. 
select c.*,avg(amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id having avg(amount)>2000;  

-- 26.	Display the top 5 customers based on total purchase amount. 
select c.*,sum(amount) from customers c join orders o on c.customer_id=o.customer_id group by order_id order by amount limit 5;  

-- 27.	Find the minimum order amount for each customer. 
select c.*,min(amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id order by min(amount);  

-- 28.	Find customers who have placed orders worth more than 5,000 in total. 
select c.*,sum(o.amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id having sum(o.amount)>5000;  

-- 29.	Display customer-wise total orders and total purchase amount. 
select c.*,count(o.order_id),sum(o.amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id;  


-- 30.	Find customers who placed more than 2 orders and spent more than 8,000. 
select c.*,count(o.order_id),sum(o.amount) from customers c join orders o on c.customer_id=o.customer_id group by customer_id having count(o.order_id)>2 and sum(o.amount)>8000;  




create table students (
    student_id int primary key,
    student_name varchar(50),
    department varchar(30),
    marks int);

insert into students values(1,'kannika','CSE',89),(2,'Peeti','ISE',87),(3,'bhagya','ECE',52),(4,'kannika','ME',95),(5,'ananya','CSE',95),(6,'nisha','ECE',32);


-- 31.	Find the average marks scored by students in each department. 
select department,avg(marks) from students group by department; 

-- 32.	Find departments whose average marks are above 75. 
select department,avg(marks) from students group by department having avg(marks)>75; 

-- 33.	Find the highest mark scored in each department. 
select department,max(marks) from students group by department; 

-- 34.	Find the total number of students in each department. 
select department,count(student_id) from students group by department; 

-- 35.	Find departments having more than 5 students. 
select department,count(student_id) from students group by department having count(student_id)>1; 

-- 36.	Display departments sorted by average marks in descending order.
select department,avg(marks) from students group by department order by department desc; 
 
-- 37.	Find the top 3 departments based on average marks. 
select department,avg(marks) from students group by department limit 3; 

-- 38.	Find departments whose average marks are between 70 and 90. 
select department,avg(marks) from students group by department having avg(marks) between 70 and 90; 

-- 39.	Find the total marks scored by students in each department.
 select department,sum(marks) from students group by department; 

-- 40.	Display departments sorted by the total number of students. 
 select department,count(student_id) from students group by department order by count(student_id) desc; 

-- 41.	Find the lowest mark scored in each department.
 select department,min(marks) from students group by department order by min(marks); 
 
-- 42.	Find departments where the highest mark is greater than 90.
 select department,max(marks) from students group by department having max(marks)>90; 
 
-- 43.	Find the number of students scoring above 80 in each department. 
 select count(student_id) from students where marks>80 group by department;

-- 44.	Find departments where more than 3 students scored above 75.
 select department,count(*) from students where marks>75 group by department having count(*)>1; 

-- 45.	Display departments ordered by highest mark in descending order. 
 select department,max(marks) from students group by department order by max(marks) desc; 





