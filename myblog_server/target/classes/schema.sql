drop table USERS if exists;
drop table POSTS if exists;

create table USERS (ID integer identity primary key, NAME varchar(50) not null);
create table POSTS (ID integer identity primary key, USER_ID integer, TITLE varchar(100), CREATION_DATE date not null, CONTENT varchar(500));
       
alter table POSTS add constraint FK_AUTHOR foreign key (USER_ID) references USERS(ID) on delete cascade;