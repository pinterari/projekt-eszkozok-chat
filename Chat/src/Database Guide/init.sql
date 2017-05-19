CREATE USER IF NOT EXISTS 'project_chat'@'localhost' IDENTIFIED BY 'project_chat';
GRANT ALL PRIVILEGES ON * . * TO 'project_chat'@'localhost';

CREATE DATABASE IF NOT EXISTS `project_chat`;
USE `project_chat`;

drop table if exists Message;
drop table if exists MessagesIn;
drop table if exists Users;
drop table if exists ChatGroup;

CREATE TABLE Users (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL unique,
    pwd varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ChatGroup (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE Message (
    id int NOT NULL AUTO_INCREMENT,
    chat_group_id int not null,
    user_id int not null,
    message varchar(255) not null,
    sdate date not null,
    PRIMARY KEY (id),
    CONSTRAINT FK_Message_ChatGroup FOREIGN KEY (chat_group_id) REFERENCES ChatGroup(id) ON DELETE CASCADE,
    CONSTRAINT FK_Message_User FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

create table Users_ChatGroup (
	id int not null auto_increment,
    chat_group_id int not null,
    user_id int not null,
    PRIMARY KEY (id),
    CONSTRAINT FK_Messages_In_ChatGroup FOREIGN KEY (chat_group_id) REFERENCES ChatGroup(id) ON DELETE CASCADE,
    CONSTRAINT FK_Messages_In_User FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


create table chatgroup_message (
	id int not null auto_increment,
    chat_group_id int not null,
    message_id int not null,
    PRIMARY KEY (id)
);