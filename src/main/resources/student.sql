show databases;

use study2;

insert into student(name)
    value
    ('박일'),
    ('박이'),
    ('박삼');

insert into course(title)
value
('JAVA'),
    ('SPRING'),
    ('PYTHON');

insert into enrollment(enrolled_date, course_id, student_id)
values
    (DATE_FORMAT(NOW(),'%Y-%m-%d'), 1, 1),
    (DATE_FORMAT(NOW(),'%Y-%m-%d'), 2, 1),
    (DATE_FORMAT(NOW(),'%Y-%m-%d'), 3, 1);
