CREATE TABLE MEMBER_authorities (
member_id varchar(50) not null,
authority varchar(50) not null,
primary key (member_id, authority)
) character set utf8mb4;

INSERT INTO MEMBER VALUES (200,FALSE,2,'a@a.com','test2','1234')
INSERT INTO member_authorities values ('test2', 'ROLE_USER');