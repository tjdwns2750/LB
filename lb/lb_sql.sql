--drop table Board;
--select * from board;
--drop table grade;
--drop table message;
--drop table ratting;
--drop table Reply;
--drop table WarningUser;

-- Exported from QuickDBD: https://www.quickdatabasediagrams.com/
-- Link to schema: https://app.quickdatabasediagrams.com/#/d/wsNf76
-- NOTE! If you have used non-SQL datatypes in your design, you will have to change these here.

-- Modify this code to update the DB schema diagram.
-- To reset the sample schema, replace everything with
-- two dots ('..' - without quotes).

CREATE TABLE ChatRoom (
    roomId number not null,
    id varchar2(100) not null,
    buno varchar2(100) not null,
    bbno number not null,
    created_day date  DEFAULT sysdate NOT NULL,
        CONSTRAINT pk_ChatRoom PRIMARY KEY (
        roomId
     )
);

CREATE TABLE CHAT_MESSAGES (
    roomId number not null,
    writer varchar2(100) not null,
    message varchar2(100) not null,
    created_day date  DEFAULT sysdate NOT NULL
);

CREATE TABLE BookBoard (
    bbno number   NOT NULL,
    id varchar2(50)   NOT NULL,
    address varchar2(100)   NOT NULL,
    title varchar2(100)   NOT NULL,
    content varchar2(3000)   NOT NULL,
    hits number  DEFAULT 0,
    category varchar2(10)   ,
    originalfile varchar2(300)   ,
    savedfile varchar2(100)   ,
    recommend number   ,
    created_day date  DEFAULT sysdate NOT NULL,
    isbn varchar2(50) NOT NULL,
    thumbnail varchar2(100) NOT NULL,
    bookTitle varchar2(100)   NOT NULL,
    author varchar2(100)   ,
    publisher varchar2(100)   ,
    price number   NOT NULL,
    amount number   NOT NULL,
    CONSTRAINT pk_BookBoard PRIMARY KEY (
        bbno
     )
);
