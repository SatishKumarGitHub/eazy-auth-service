use eazybank;

create table users (
`id` int not null auto_increment,
username varchar(60) not null,
password varchar(30) not null ,
enabled boolean not null ,
primary key (`id`) );

alter table  eazybank.user rename to users;

create table authorities (
`id` int not null auto_increment,
username varchar(60) not null,
authority varchar(30) not null ,
primary key (`id`) );


create table customer (
`id` int not null auto_increment,
email varchar(60) not null,
pwd varchar(30) not null ,
role varchar(30) not null ,
primary key (`id`) );

insert into eazybank.users values(null, 'user', 'user123', 1);
insert into eazybank.authorities values(null, 'user', 'read');

insert into eazybank.customer values(null, 'admin', 'admin@123', 'admin');
