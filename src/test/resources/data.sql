INSERT INTO CATEGORY (ID,NAME) VALUES (0,'Music');
INSERT INTO CATEGORY (ID,NAME) VALUES (1,'Artwork');
INSERT INTO CATEGORY (ID,NAME) VALUES (2,'Movie');
INSERT INTO CATEGORY (ID,NAME) VALUES (3,'Ebook');
INSERT INTO CATEGORY (ID,NAME) VALUES (4,'Course');

INSERT INTO MEMBER (BLOCKED,MEMBER_ID,EMAIL,NAME,PASSWORD) VALUES (0,1,'1@1','a1','$2a$10$WR5UE50cjF03s8gsctsiWODrfwdWx5c31lX0/Qavll7ZFwXRikin6');

INSERT INTO SELLER (MEMBER_ID,SELLER_ID,NAME) VALUES (1,1,'b2');

INSERT INTO ITEM (PRICE,quantityState,stockQuantity,ITEM_ID,SELLER_ID,DTYPE,CONTENT,NAME) VALUES (10000,1,0,1,1,'Ar','this is art1','art1');
INSERT INTO ITEM (PRICE,quantityState,stockQuantity,ITEM_ID,SELLER_ID,DTYPE,CONTENT,NAME) VALUES (10000,1,0,2,1,'Ar','this is art2','art2');
INSERT INTO ITEM (PRICE,quantityState,stockQuantity,ITEM_ID,SELLER_ID,DTYPE,CONTENT,NAME) VALUES (10000,1,0,3,1,'Ar','this is the picture1','picture1');
INSERT INTO ITEM (PRICE,quantityState,stockQuantity,ITEM_ID,SELLER_ID,DTYPE,CONTENT,NAME) VALUES (10000,1,0,4,1,'Mu','this is music1','music1');
INSERT INTO ITEM (PRICE,quantityState,stockQuantity,ITEM_ID,SELLER_ID,DTYPE,CONTENT,NAME) VALUES (10000,1,0,5,1,'Mu','this is rapformessi','rapformessi');

insert into item_category (item_id, categoryIds) values (1, 1);
insert into item_category (item_id, categoryIds) values (2, 1);
insert into item_category (item_id, categoryIds) values (3, 1);

insert into item_category (item_id, categoryIds) values (4, 0);
insert into item_category (item_id, categoryIds) values (5, 0);


