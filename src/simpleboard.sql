create database jpaboard;

use jpaboard;

create table member(
	member_id bigint auto_increment primary key,
	login_id varchar(300) not null unique,
    password varchar(300) not null,
    name varchar(300),
    gender enum ('M','F'),
    phone_number varchar(300),
    email varchar(300),
    zipcode varchar(300),
    addr varchar(300),
    addrdetail varchar(300),
    addretc varchar(300),
	isdelete enum('Y','N') default 'N'
);
select * from member;
    
create table board(
	board_id bigint auto_increment primary key,
    member_id bigint not null,
    title varchar(300) not null,
    content text not null,
    regdate datetime default now(),
    updatecheck enum('ORIGIN','UPDATE'),
    like_cnt bigint default 0 ,
    view_cnt bigint default 0,
    reply_cnt bigint default 0,
    isdelete enum('Y','N') default 'N',
    foreign key (member_id) references member(member_id)
);

create table comment(
	comment_id bigint auto_increment primary key,
    board_id bigint not null,
    member_id bigint not null,
    content text not null,
    regdate datetime default now(),
    foreign key (board_id) references board(board_id),
    foreign key (member_id) references member(member_id)
);