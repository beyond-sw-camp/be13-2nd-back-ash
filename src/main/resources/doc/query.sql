create table todo (
  id int auto_increment primary key,
  userId varchar(50) not null,
  title varchar(100) not null,
  done tinyint default 0
) default character set utf8;

select * from todo;