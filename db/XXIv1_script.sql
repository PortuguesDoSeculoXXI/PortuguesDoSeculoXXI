/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     21-11-2014 16:40:34                          */
/*==============================================================*/

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
drop table if exists CATEGORY;

create table CATEGORY
(
   ID_CATEGORY          INTEGER PRIMARY KEY AUTOINCREMENT,
   CATEGORY_NAME        varchar(30) not null
);

/*==============================================================*/
/* Table: CHALLENGE                                             */
/*==============================================================*/
drop table if exists CHALLENGESCORES;

create table CHALLENGESCORES
(
   ID_CHALLENGE         INTEGER PRIMARY KEY AUTOINCREMENT,
   ID_PLAYER            int,
   DATE                 datetime not null,
   DURATION             int,
   LEVEL                int not null,
   SCORE                int not null,
   GOLD                 int,
   SILVER               int,
   BRONZE               int,
   CONSTRAINT `fk_ChallengeScore_Player`
    FOREIGN KEY (`ID_PLAYER`)
    REFERENCES `PLAYER` (`ID_PLAYER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX IF NOT EXISTS `fk_ChallengeScore_Player_idx` ON `CHALLENGESCORES` (`ID_PLAYER`);

/*==============================================================*/
/* Table: PLAYER                                                */
/*==============================================================*/
drop table if exists PLAYER;

create table PLAYER
(
   ID_PLAYER            INTEGER PRIMARY KEY AUTOINCREMENT,
   NAME                 varchar(40) not null
);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
drop table if exists QUESTION;

create table QUESTION
(
   ID_QUESTION          INTEGER PRIMARY KEY AUTOINCREMENT,
   ID_RULE              int,
   ID_CATEGORY          int not null,
   ENUNCIATION          varchar(150) not null,
   ANSWER_A             varchar(50) not null,
   ANSWER_B             varchar(50) not null,
   RIGHT_ANSWER         int not null,
   CONSTRAINT `fk_Question_Rule`
    FOREIGN KEY (`ID_RULE`)
    REFERENCES `RULE` (`ID_RULE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
   CONSTRAINT `fk_Question_Category`
    FOREIGN KEY (`ID_CATEGORY`)
    REFERENCES `CATEGORY` (`ID_CATEGORY`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX IF NOT EXISTS `fk_Question_Rule_idx` ON `QUESTION` (`ID_RULE`);
CREATE INDEX IF NOT EXISTS `fk_Question_Category_idx` ON `QUESTION` (`ID_CATEGORY`);

/*==============================================================*/
/* Table: RULE                                                  */
/*==============================================================*/
drop table if exists RULE;

create table RULE
(
   ID_RULE              INTEGER PRIMARY KEY AUTOINCREMENT,
   CLARIFICATION        varchar(100) not null
);
