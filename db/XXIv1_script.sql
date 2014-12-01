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
   DATE                 datetime not null,
   DURATION             time,
   LEVEL                int not null,
   GOLD                 int,
   SILVER               int,
   IRON                 int,
   primary key (ID_CHALLENGE),
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
   primary key (ID_QUESTION),
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
create table RULE
(
   ID_RULE              int not null,
   CLARIFICATION        varchar(100) not null,
   primary key (ID_RULE)
);
