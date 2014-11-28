/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     21-11-2014 16:40:34                          */
/*==============================================================*/


drop table if exists CATEGORY;

drop table if exists CHALLENGESCORES;

drop table if exists PLAYER;

drop table if exists QUESTION;

drop table if exists RULE;

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   ID_CATEGORY          int not null,
   CATEGORY_NAME        varchar(30) not null,
   primary key (ID_CATEGORY)
);

/*==============================================================*/
/* Table: CHALLENGE                                             */
/*==============================================================*/
create table CHALLENGESCORES
(
   ID_CHALLENGE         int not null,
   ID_PLAYER            int,
   ID_SCORE             int not null,
   DATE                 datetime not null,
   DURATION             time,
   LEVEL                int not null,
   GOLD                 int,
   SILVER               int,
   IRON                 int,
   primary key (ID_CHALLENGE)
);

/*==============================================================*/
/* Table: PLAYER                                                */
/*==============================================================*/
create table PLAYER
(
   ID_PLAYER            int not null,
   NAME                 varchar(40) not null,
   primary key (ID_PLAYER)
);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
create table QUESTION
(
   ID_QUESTION          int not null,
   ID_RULE              int,
   ID_CATEGORY          int not null,
   ENUNCIATION          varchar(150) not null,
   ANSWER_A             varchar(50) not null,
   ANSWER_B             varchar(50) not null,
   RIGHT_ANSWER         int not null,
   primary key (ID_QUESTION)
);

/*==============================================================*/
/* Table: RULE                                                  */
/*==============================================================*/
create table RULE
(
   ID_RULE              int not null,
   CLARIFICATION        varchar(100) not null,
   primary key (ID_RULE)
);
