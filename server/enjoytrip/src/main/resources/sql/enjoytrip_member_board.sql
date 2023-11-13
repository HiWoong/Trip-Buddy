create schema enjoytrip;

use enjoytrip;

-- 사용자 정보 테이블
create table members(
	user_id varchar(20) not null primary key,
    user_name varchar(10) not null,
    user_password varchar(20) not null,
    email_id varchar(50) not null,
    email_domain varchar(50) not null,
    join_date date not null default (current_date)
);

-- 게시판 테이블
create table boards(
	article_no int not null auto_increment primary key,
    user_id varchar(20) not null,
    subject varchar(100) not null,
    content varchar(2000) not null,
    hit int not null default 0,
    register_time datetime not null default current_timestamp,
    foreign key (userId) references member(userId)
);
