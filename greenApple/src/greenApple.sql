
-- 상품
select * from PRODUCT;
drop * from PRODUCT;
create table product (
	product_code number primary key, -- 상품 코드
	thumbnail varchar2(250) not null, -- 썸네일
	product_name varchar2(90) not null, -- 상품명 30자
	price number(7) not null, -- 가격
	product_description varchar2(3000), -- 설명
	origin varchar2(40) not null, -- 원산지
	seasonal varchar2(10) not null, -- 재철
	stock number default 0 not null, -- 재고
    input_date date not null -- 등록일
);

-- 상품 사진
select * from PRODUCT_IMG;
drop * from PRODUCT_IMG;
create table product_img (
	img_num number primary key, -- 사진 번호
	product_code number not null, -- 상품 코드 fk
	file_name varchar2(100), -- 파일명
	file_size varchar2(250), -- 파일 크기
    CONSTRAINTS product_code FOREIGN KEY(product_code) 
        REFERENCES product(product_code)
);

-- 회원
select * from MEMBER;
drop * from MEMBER;
create table member(
	id varchar2(20) primary key, -- id
	pw varchar2(30) not null, -- pw
	name varchar2(15) not null, -- 이름
	tel number not null, -- 전화번호
	join_date date not null, -- 가입일
	address varchar2(90) not null, -- 주소
	del char(1) default 'n' not null -- 삭제 여부
);

-- 리뷰
select * from REVIEW;
drop * from REVIEW;
create table review (
	review_no number primary key, -- 리뷰 번호
	id varchar2(20) not null, -- 아이디 fk
	product_code number not null, -- 상품 코드 fk
	content varchar2(900) not null, -- 내용
    CONSTRAINTS product_code FOREIGN KEY(product_code) 
        REFERENCES product(product_code),
    CONSTRAINTS id FOREIGN KEY(id) 
        REFERENCES member(id)
);

-- 장바구니
select * from CART;
drop * from CART;
create table cart (
	cart_num number primary key, -- 장바구니 번호
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
drop * from product_order;
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
drop * from ORDER_DETAIL;
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
insert into product values(
	1111,
	'apple.jpg',
	'사과',
	20000,
	'달고 맛있는 사과',
	'한국',
	'10월',
	'10',
    sysdate
);

