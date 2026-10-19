USE online_exams;
SELECT * FROM exams;
SHOW TABLES;

insert into students (name, email, password) values
('Kannika', 'kannika@gmail.com', 'kannika123'),
('Rahul', 'rahul@gmail.com', 'rahul123'),
('Priya', 'priya@gmail.com', 'priya123'),
('Amit', 'amit@gmail.com', 'amit123'),
('Sneha', 'sneha@gmail.com', 'sneha123');

select * from exams;
select * from students;

insert into exams (exam_name, total_marks, duration_minutes) values
('Java Basics', 20, 30),
('SQL Fundamentals', 25, 40),
('Python Programming', 30, 45),
('Web Development', 20, 30),
('Aptitude Test', 50, 60);

insert into questions
(exam_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks)
values
-- Java Basics (exam_id = 1)
(1, 'Which keyword creates a class in Java?', 'class', 'new', 'void', 'static', 'A', 2),
(1, 'Which method is the entry point of Java?', 'start()', 'main()', 'run()', 'execute()', 'B', 2),
(1, 'Which is not a primitive type?', 'int', 'char', 'String', 'boolean', 'C', 2),
(1, 'Which keyword is used for inheritance?', 'implements', 'extends', 'inherits', 'superclass', 'B', 2),
(1, 'Which symbol ends a Java statement?', ':', ',', '.', ';', 'D', 2),

-- SQL Fundamentals (exam_id = 2)
(2, 'Which command retrieves data?', 'GET', 'SELECT', 'FETCH ALL', 'OPEN', 'B', 5),
(2, 'Which command adds a new row?', 'INSERT', 'UPDATE', 'ALTER', 'DROP', 'A', 5),
(2, 'Which clause filters rows?', 'ORDER BY', 'GROUP BY', 'WHERE', 'JOIN', 'C', 5),
(2, 'Which function counts rows?', 'SUM()', 'COUNT()', 'TOTAL()', 'NUMBER()', 'B', 5),
(2, 'Which command removes a table?', 'DELETE', 'REMOVE', 'CLEAR', 'DROP', 'D', 5),

-- Python Programming (exam_id = 3)
(3, 'Which keyword defines a function?', 'function', 'define', 'def', 'fun', 'C', 6),
(3, 'Which data type stores True or False?', 'int', 'bool', 'str', 'list', 'B', 6),
(3, 'Which symbol starts a comment?', '//', '#', '/*', '--', 'B', 6),
(3, 'Which function displays output?', 'print()', 'display()', 'show()', 'output()', 'A', 6),
(3, 'Which collection uses key-value pairs?', 'List', 'Tuple', 'Dictionary', 'Set', 'C', 6);

insert into questions
(exam_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks)
values
(1, 'Which keyword creates a class in Java?', 'class', 'new', 'void', 'static', 'A', 2),
(1, 'Which method is the entry point of Java?', 'start()', 'main()', 'run()', 'execute()', 'B', 2),
(1, 'Which is not a primitive type?', 'int', 'char', 'String', 'boolean', 'C', 2),
(1, 'Which keyword is used for inheritance?', 'implements', 'extends', 'inherits', 'superclass', 'B', 2),
(1, 'Which symbol ends a Java statement?', ':', ',', '.', ';', 'D', 2);

-- aptitude questions
insert into questions
(exam_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks)
values
(3, 'What is 25 percent of 200?', '25', '40', '50', '60', 'C', 2),
(3, 'Find the next number: 2, 4, 8, 16, ?', '20', '24', '32', '36', 'C', 2),
(3, 'A man buys a pen for 20 rupees and sells it for 25 rupees. What is the profit?', '3 rupees', '5 rupees', '10 rupees', '15 rupees', 'B', 2),
(3, 'If 5 workers finish a job in 10 days, how many days will 10 workers take at the same rate?', '2 days', '5 days', '10 days', '20 days', 'B', 2),
(3, 'What is the average of 10, 20 and 30?', '15', '20', '25', '30', 'B', 2),
(3, 'A train travels 120 km in 2 hours. What is its speed?', '40 km/h', '50 km/h', '60 km/h', '80 km/h', 'C', 2),
(3, 'If x + 15 = 40, what is x?', '15', '20', '25', '30', 'C', 2),
(3, 'Find the odd one out.', 'Apple', 'Mango', 'Carrot', 'Banana', 'C', 2),
(3, 'A shirt costs 500 rupees. A 10 percent discount is given. What is the final price?', '400 rupees', '450 rupees', '475 rupees', '490 rupees', 'B', 2),
(3, 'Complete the series: 3, 6, 9, 12, ?', '13', '14', '15', '18', 'C', 2);

-- //web developmement
select * from questions where exam_id=1;
insert into questions
(exam_id, question_text, option_a, option_b, option_c, option_d, correct_option, marks)
values
(2, 'Which language is used to structure web pages?', 'CSS', 'HTML', 'Java', 'SQL', 'B', 2),
(2, 'Which language styles a web page?', 'HTML', 'Java', 'CSS', 'Python', 'C', 2),
(2, 'Which tag creates a hyperlink in HTML?', '<link>', '<a>', '<href>', '<url>', 'B', 2),
(2, 'Which language adds interactivity to web pages?', 'JavaScript', 'SQL', 'HTML', 'CSS', 'A', 2),
(2, 'Which symbol is ed to select an id in CSS?', '.', '#', '*', '@', 'B', 2);
select * from results;

select * from questions where exam_id=2;
SELECT * FROM students
WHERE student_id = 4;


select * from students
where email = 'rahul@gmail.com';

select * from exams
where total_marks > 30;

update exams
set duration_minutes = 90
where exam_id = 1;

delete from students
where student_id = 6;

select count(*) as total_students
from students;

select sum(marks_obtained) as total_marks_scored
from results;

select min(marks_obtained) as lowest_marks
from results;

select avg(percentage) as average_percentage
from results;

select exam_id, count(*) as total_questions
from questions
group by exam_id;

select max(marks_obtained) as highest_marks
from results;

select s.name, r.marks_obtained,
       r.total_marks, r.percentage
from students s
join results r
on s.student_id = r.student_id;

select s.name, e.exam_name,
       r.marks_obtained, r.percentage
from results r
join students s
on r.student_id = s.student_id
join exams e
on r.exam_id = e.exam_id;

select e.exam_name, q.question_text, q.marks
from questions q
join exams e
on q.exam_id = e.exam_id;