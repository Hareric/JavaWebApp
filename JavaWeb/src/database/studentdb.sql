drop database if exists STUDENTDB;
create database STUDENTDB;
use STUDENTDB;

 

create table STUDENTS ( 

  ID bigint not null auto_increment primary key,

  NO varchar(11) not null,

  NAME varchar(10) not null,

  AGE INT,

  CLASS varchar(25)

);

 

create table TEACHERS (

  ID bigint not null auto_increment primary key,

  NO varchar(11) not null,

  NAME varchar(10) not null,

  AGE INT,

  DEPARTMENT varchar(25)

);

 

create table COURSES (

   ID bigint not null auto_increment primary key,

   NAME varchar(15),

   TIME INT,

   SCORES INT

) ;

insert into STUDENTS(ID,NO,NAME,AGE,CLASS) values(1, '20141000001','С��',23, '�����01');

insert into STUDENTS(ID,NO,NAME,AGE,CLASS) values(2, '20141000002','Сm',23, '���繤��');