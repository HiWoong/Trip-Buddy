-- create schema enjoytrip;

use enjoytrip;

-- 사용자 정보 테이블   
create table users(
	user_id varchar(20) primary key,
    user_name varchar(10) not null,
    user_password varchar(20) not null,
    email_id varchar(50) not null,
    email_domain varchar(50) not null,
    join_date timestamp not null default (current_timestamp),
    token VARCHAR(500),
    profile_image MEDIUMBLOB,
    favorite VARCHAR(1000) default '[]'
);

-- 게시판 테이블
create table boards(
	article_no int auto_increment primary key,
    user_id varchar(20) not null,
    subject varchar(100) not null,
    content varchar(2000) not null,
    hit int not null default 0,
    register_time timestamp not null default current_timestamp,
    foreign key (user_id) references users(user_id) on update cascade on delete cascade
);

-- 코멘트 테이블
create table comments (
	comment_id int auto_increment primary key,
    user_id varchar(20) not null,
    article_no int not null,
    content varchar(500) not null,
    created_date timestamp not null default (current_timestamp),
    updated_date timestamp not null default (current_timestamp),
    foreign key (user_id) references users(user_id) on update cascade on delete cascade,
    foreign key (article_no) references boards(article_no) on update cascade on delete cascade
);

-- 여행계획 테이블
create table plans (
	plan_id int not null auto_increment primary key,
	user_id varchar(20) not null,
    subject varchar(100) not null,
    content_1 varchar(1000),
    content_2 varchar(1000),
    content_3 varchar(1000),
    content_4 varchar(1000),
    content_5 varchar(1000),
    create_date timestamp not null default (current_timestamp),
    update_date timestamp not null default (current_timestamp),
    foreign key (user_id) references users(user_id) on update cascade on delete cascade
);

-- 핫플레이스 테이블
create table hotplaces (
	hotplace_id int auto_increment primary key,
    user_id varchar(20),
    subject varchar(100) not null,
    content varchar(2000) not null,
    image mediumblob,
	hit_count int not null default 0,
    visited_count int not null default 0,
    created_date timestamp not null default (current_timestamp),
    updated_date timestamp not null default (current_timestamp),
    foreign key (user_id) references users(user_id) on update cascade on delete cascade
);

-- 핫플레이스 별점 테이
create table stars (
	star_id int auto_increment primary key,
	hotplace_id int not null,
    user_id varchar(20) not null,
    score int not null,
    foreign key (hotplace_id) references hotplaces(hotplace_id) on update cascade on delete cascade,
    foreign key (user_id) references users(user_id) on update cascade on delete cascade
);