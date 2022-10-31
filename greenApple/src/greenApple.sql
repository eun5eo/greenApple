
-- 상품
select * from PRODUCT;
drop table PRODUCT;
create table product (
	product_code number primary key, -- 상품 코드
	thumbnail varchar2(250) not null, -- 썸네일
	product_name varchar2(90) not null, -- 상품명 30자
	price number(7) not null, -- 가격
	product_description varchar2(3000), -- 설명
	origin varchar2(40) not null, -- 원산지
	seasonal varchar2(10) not null, -- 제철
	stock number default 0 not null, -- 재고
    input_date date not null -- 등록일
);

-- 상품 사진
select * from PRODUCT_IMG;
drop table PRODUCT_IMG;
create table product_img (
	product_img_no number primary key, -- 사진 번호
	product_code number not null, -- 상품 코드 fk
	file_name varchar2(100), -- 파일명
	CONSTRAINTS product_code FOREIGN KEY(product_code) 
		REFERENCES product(product_code)
);

-- 회원
select * from MEMBER;
drop table MEMBER;
create table member(
	id varchar2(20) primary key, -- id
	pw varchar2(90) not null, -- pw
	name varchar2(15) not null, -- 이름
	tel number not null, -- 전화번호
	join_date date not null, -- 가입일
	address varchar2(90) not null, -- 주소
	del char(1) default 'n' not null -- 탈퇴 여부
);

-- 리뷰
select * from REVIEW;
drop table REVIEW;
create table review (
	review_no number primary key, -- 리뷰 번호
	id varchar2(20) not null, -- 아이디 fk
	product_code number not null, -- 상품 코드 fk
	content varchar2(900) not null, -- 내용
	file_name varchar2(150), -- 파일명
	review_date date not null, -- 등록일
	del char(1) default 'n' not null, -- 삭제 여부
	CONSTRAINTS product_code FOREIGN KEY(product_code) 
		REFERENCES product(product_code),
	CONSTRAINTS id FOREIGN KEY(id) 
		REFERENCES member(id)
);

-- 리뷰 사진
-- 시퀀스 : review_image_seq
select * from REVIEW_IMG;
drop table REVIEW_IMG;
create table review_img (
	review_img_no number primary key, -- 사진 번호
	id varchar2(20) not null, -- 아이디 fk
	product_code number not null, -- 상품 코드 fk
	file_name varchar2(100), -- 파일명
	CONSTRAINTS product_code_rvImg FOREIGN KEY(product_code) 
		REFERENCES product(product_code),
	CONSTRAINTS id_rvImg FOREIGN KEY(id) 
		REFERENCES member(id)
);

-- 장바구니
select * from CART;
drop table CART;
create table cart (
	cart_no number primary key, -- 장바구니 번호
	product_code number not null, -- 상품 코드 fk
	id varchar2(20) not null, -- 아이디 fk
	amount number not null, -- 수량
	CONSTRAINTS product_code FOREIGN KEY(product_code) 
		REFERENCES product(product_code),
	CONSTRAINTS id FOREIGN KEY(id) 
		REFERENCES member(id)
);

-- 주문
select * from product_order;
drop table product_order;
create table product_order (
	order_no number primary key, -- 주문 번호
	id varchar2(20) not null, -- 아이디 fk
	order_date date not null, -- 주문일
	address varchar2(90) not null, -- 주소
	recipient varchar2(15) not null, -- 수령자 이름
	recipient_tel number not null -- 수령자 전화번호
	CONSTRAINTS id FOREIGN KEY(id) 
		REFERENCES member(id)
);

-- 주문 상세
select * from ORDER_DETAIL;
drop table ORDER_DETAIL;
create table order_detail (
	detail_no number primary key, -- 주문 상세 번호
	order_no number not null, -- 주문 번호 fk
	product_code number not null, -- 상품 코드 fk
	amount number not null, -- 수량
	price number not null -- 가격
	CONSTRAINTS orderno FOREIGN KEY(orderno) 
		REFERENCES prod_order(orderno),
	CONSTRAINTS product_code FOREIGN KEY(product_code) 
		REFERENCES product(product_code)
);





-- insert

-- 상품
insert into product values(
	2222,
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
	'멤버', -- 이름
	'01011112222', -- 전화번호
	sysdate, -- 가입일
	'서울시 강남구', -- 주소
	'n' -- 탈퇴 여부
);

-- 리뷰
insert into review values(
	'1', -- 리뷰 번호
	'member1', -- 아이디 fk
	'1111', -- 상품 코드 fk
	'맛있어요', -- 내용
	'apple.jpg', -- 파일명
	sysdate, -- 등록일
	'n' -- 삭제 여부
);

