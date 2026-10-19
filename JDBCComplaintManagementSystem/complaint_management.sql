use complaint_management;
show tables;
USE complaint_management;

insert into users (name, email, phone) values
('kannika', 'kannika@gmail.com', '9876543210'),
('dushyanth', 'dushyanth@gmail.com', '9876543211'),
('Raj', 'raj@gmail.com', '9876543212');
select * from users;

insert into officers (officer_name, department, phone) values
('Suresh Kumar', 'Sanitation', '9876501234'),
('Anitha Rao', 'Water Supply', '9876501235'),
('Manoj Singh', 'Road Maintenance', '9876501236');
select * from officers;

insert into complaints (user_id, officer_id, complaint_type, description, location, status, resolution)values
(1, 1, 'Garbage', 'Garbage not collected',
 'BTM Layout', 'Pending', NULL),
(2, 2, 'Water Supply', 'No water supply for two days',
 'Jayanagar', 'In Progress', NULL),
(3, 3, 'Road Damage', 'Large pothole on the road',
 'Electronic City', 'Resolved', 'Pothole repaired');
select * from complaints;


update complaints
set officer_id = 2
where complaint_id = 1;

update complaints
set status = 'In Progress'
where complaint_id = 1;

select complaint_id, complaint_type, status, resolution
from complaints
where complaint_id = 1;


delete from complaints
where complaint_id = 4;

