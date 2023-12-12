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

CREATE TABLE "User" (
    -- User num
    "uno" number   NOT NULL,
    -- User id
    "id" varchar2(100)   NOT NULL,
    -- User pw
    "pw" varchar2(100)   NOT NULL,
    -- Address
    "address" varchar2(400)   NOT NULL,
    -- email
    "email" varchar2(400)   NOT NULL,
    "originfile" varchar2(300)   NOT NULL,
    "savedfile" varchar2(100)   NOT NULL,
    "rolename" varchar2(10)  DEFAULT 'role_user' NOT NULL,
    "enabled" number(1)  DEFAULT 1 NOT NULL,
    -- Creatived day
    "created_day" date  DEFAULT sysdate NOT NULL,
    CONSTRAINT "pk_User" PRIMARY KEY (
        "uno"
     )
);

CREATE TABLE "Grade" (
    -- Grade Num
    "gno" number   NOT NULL,
    -- User num
    "uno" number   NOT NULL,
    -- Grade Name
    "name" varchar2(10)   NOT NULL,
    CONSTRAINT "pk_Grade" PRIMARY KEY (
        "gno"
     )
);

CREATE TABLE "Ratting" (
    -- Ratting Num
    "rno" number   NOT NULL,
    -- User Num
    "uno" number   NOT NULL,
    -- Score Num
    "scroe" number   NOT NULL,
    -- Creatived day
    "created_day" date  DEFAULT sysdate NOT NULL,
    CONSTRAINT "pk_Ratting" PRIMARY KEY (
        "rno"
     )
);

CREATE TABLE "WarningUser" (
    -- WarningUser num
    "wuno" number   NOT NULL,
    -- User num
    "uno" number   NOT NULL,
    -- get count
    "count" number   NOT NULL,
    -- Creatived day
    "created_day" date  DEFAULT sysdate NOT NULL
);

CREATE TABLE "Message" (
    "mno" number   NOT NULL,
    "uno" number   NOT NULL,
    "title" varchar2(100)   NOT NULL,
    "content" varchar2(1000)   NOT NULL,
    CONSTRAINT "pk_Message" PRIMARY KEY (
        "mno"
     )
);

CREATE TABLE "Board" (
    "bno" number   NOT NULL,
    "uno" number   NOT NULL,
    "title" varchar2(100)   NOT NULL,
    "content" varchar2(3000)   NOT NULL,
    "hits" number  DEFAULT 0 NOT NULL,
    "categori" varchar2(10)   NOT NULL,
    "originfile" varchar2(300)   NOT NULL,
    "savedfile" varchar2(100)   NOT NULL,
    "recommend" number   NOT NULL,
    -- Creatived day
    "created_day" date  DEFAULT sysdate NOT NULL,
    CONSTRAINT "pk_Board" PRIMARY KEY (
        "bno"
     )
);

CREATE TABLE "Reply" (
    "rno" number   NOT NULL,
    "bno" number   NOT NULL,
    "content" varchar2(1000)   NOT NULL,
    -- Creatived day
    "created_day" date  DEFAULT sysdate NOT NULL,
    CONSTRAINT "pk_Reply" PRIMARY KEY (
        "rno"
     )
);

CREATE TABLE "BookBoard" (
    "bno" number   NOT NULL,
    "title" varchar2(100)   NOT NULL,
    "author" varchar2(100)   NOT NULL,
    "publisher" varchar2(100)   NOT NULL,
    "input_date" varchar2(100)   NOT NULL,
    "tb_cnt_url" varchar2(100)   NOT NULL,
    "book_introduction_url" varchar2(1000)   NOT NULL,
    "book_summary_url" varchar2(1000)   NOT NULL,
    "page" varchar2(100)   NOT NULL,
    "title_url" varchar2(1000)   NOT NULL,
    "isbn" varchar2(1000)   NOT NULL
);

ALTER TABLE "Grade" ADD CONSTRAINT "fk_Grade_uno" FOREIGN KEY("uno")
REFERENCES "User" ("uno");

ALTER TABLE "Ratting" ADD CONSTRAINT "fk_Ratting_uno" FOREIGN KEY("uno")
REFERENCES "User" ("uno");

ALTER TABLE "WarningUser" ADD CONSTRAINT "fk_WarningUser_uno" FOREIGN KEY("uno")
REFERENCES "User" ("uno");

ALTER TABLE "Message" ADD CONSTRAINT "fk_Message_uno" FOREIGN KEY("uno")
REFERENCES "User" ("uno");

ALTER TABLE "Board" ADD CONSTRAINT "fk_Board_uno" FOREIGN KEY("uno")
REFERENCES "User" ("uno");

ALTER TABLE "Reply" ADD CONSTRAINT "fk_Reply_bno" FOREIGN KEY("bno")
REFERENCES "Board" ("bno");

ALTER TABLE "BookBoard" ADD CONSTRAINT "fk_BookBoard_bno" FOREIGN KEY("bno")
REFERENCES "Board" ("bno");

