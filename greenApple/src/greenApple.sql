
-- 상품
select * from PRODUCT;
drop table PRODUCT;
create table product (
	productCode number primary key, -- 상품 코드
	thumbnail varchar2(250) not null, -- 썸네일
	productName varchar2(90) not null, -- 상품명 30자
	price number(7) not null, -- 가격
	productDescription varchar2(3000), -- 설명
	origin varchar2(40) not null, -- 원산지
	seasonal varchar2(10) not null, -- 제철
	stock number default 0 not null, -- 재고
    inputDate date not null -- 등록일
);

-- 상품 사진
select * from PRODUCT_IMG;
drop table PRODUCT_IMG;
create table productImg (
	productImgNo number primary key, -- 사진 번호
	productCode number not null, -- 상품 코드 fk
	fileName varchar2(100), -- 파일명
	FOREIGN KEY(productCode) REFERENCES product(productCode)
);

-- 회원
select * from MEMBER;
drop table MEMBER;
create table member(
	id varchar2(20) primary key, -- id
	pw varchar2(90) not null, -- pw
	name varchar2(15) not null, -- 이름
	tel varchar2(15) not null, -- 전화번호
	joinDate date not null, -- 가입일
	address1 varchar2(90) not null, -- 주소
	address2 varchar2(90), -- 상세 주소
	del char(1) default 'n' not null -- 탈퇴 여부
);

-- 리뷰
select * from REVIEW;
drop table REVIEW;
create table review (
	reviewId varchar2(20) primary key, -- 리뷰 아이디
	id varchar2(20) not null, -- 아이디 fk
	productCode number not null, -- 상품 코드 fk
	content varchar2(900) not null, -- 내용
	fileName varchar2(150), -- 파일명
	reviewDate date not null, -- 작성일
	del char(1) default 'n' not null, -- 삭제 여부
	FOREIGN KEY(productCode) REFERENCES product(productCode),
	FOREIGN KEY(id) REFERENCES member(id)
);

-- 리뷰 사진
-- 시퀀스 : review_image_seq
select * from REVIEW_IMG;
drop table REVIEW_IMG;
create table review_img (
	reviewImgNo number primary key, -- 사진 번호
	reviewId varchar2(20) not null, -- 리뷰 번호 fk
	id varchar2(20) not null, -- 아이디 fk
	fileName varchar2(100), -- 파일명
	FOREIGN KEY(reviewId) REFERENCES review(reviewId),
	FOREIGN KEY(id) REFERENCES member(id)
);

-- 장바구니
-- 시퀀스 : cart_seq
select * from CART;
drop table CART;
create table cart (
	cartNo number primary key, -- 장바구니 번호
	productCode number not null, -- 상품 코드
	id varchar2(20) not null, -- 아이디 fk
	amount number not null, -- 수량
	FOREIGN KEY(id) REFERENCES member(id)
);
alter table CART add unique (product_code, id);

-- 주문
select * from product_order;
drop table product_order;
create table product_order (
	orderId varchar2(20) primary key, -- 주문 아이디
	id varchar2(20) not null, -- 아이디 fk
	orderDate date not null, -- 주문일
	address1 varchar2(90) not null, -- 주소
	address2 varchar2(90), -- 상세 주소
	recipient varchar2(15) not null, -- 수령자 이름
	recipientTel varchar2(15) not null, -- 수령자 전화번호
	payment char(1) default 'n' not null, -- 결제 여부
	paymoney number default 0 not null, -- 결제 금액
	orderDel char(1) default 'n' not null, -- 주문취소 여부
	FOREIGN KEY(id) REFERENCES member(id)
);

-- 주문 상세
-- 시퀀스 : order_detail_seq
select * from ORDER_DETAIL;
drop table ORDER_DETAIL;
create table order_detail (
	detailNo number primary key, -- 주문 상세 번호
	orderId varchar2(20) not null, -- 주문 번호 fk
	productCode number not null, -- 상품 코드 fk
	amount number not null, -- 수량
	price number not null, -- 가격
	FOREIGN KEY(orderId) REFERENCES product_order(orderId),
	FOREIGN KEY(productCode) REFERENCES product(productCode)
);





-- insert

-- 상품
insert into product values(
	3333,
	'mango.jpg',
	'망고',
	20000,
	'노랗고 달콤한 망고',
	'태국',
	'sp',
	'15',
    sysdate
);

-- 회원
insert into member values(
	'member1', -- id
	'11', -- pw
	'멤버1', -- 이름
	'01011113333', -- 전화번호
	sysdate, -- 가입일
	'경기도', -- 주소
	'경기아파트 111동 101호',
	'n' -- 탈퇴 여부
);

-- 관리자
insert into member values(
	'admin', -- id
	'imadmin0099', -- pw
	'관리자', -- 이름
	'01000000000', -- 전화번호
	sysdate, -- 가입일
	'집', -- 주소
	'내 집',
	'n' -- 탈퇴 여부
);

-- 리뷰
insert into review values(
	'20221109', -- 리뷰 아이디
	'member1', -- 아이디 fk
	'1111', -- 상품 코드 fk
	'맛있어요', -- 내용
	'mango.jpg', -- 파일명
	sysdate, -- 등록일
	'n' -- 삭제 여부
);


CREATE SEQUENCE review_image_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCACHE
    NOORDER
    NOCYCLE;

CREATE SEQUENCE cart_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCACHE
    NOORDER
    NOCYCLE;

CREATE SEQUENCE order_detail_seq
    START WITH 100000
    INCREMENT BY 1
    MINVALUE 100000
    NOMAXVALUE
    NOCACHE
    NOORDER
    NOCYCLE;


