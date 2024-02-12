create database store_manager;
use store_manager;

-- nhân viên
create table users(
	users_id int primary key auto_increment,
    users_username varchar(50) not null,
    users_password varchar(50) not null,
    users_fullname nvarchar(50) not null,
    users_phone varchar(11) not null,
    users_address nvarchar(100) not null,
    user_description text
);

-- khách hàng
create table customer(
	customer_id int primary key auto_increment,
	customer_fullname nvarchar(50) not null,
    customer_phone varchar(11),
    customer_email nvarchar(50),
    customer_description text
);

-- loại mặt hàng
create table categories(
	categories_id int primary key auto_increment,
    categories_name nvarchar(50),
    categories_description text
);

-- nhà phân phối
create table distributor(
	distributor_id int primary key auto_increment,
    distributor_name nvarchar(50) not null,
    distributor_phone varchar(11),
    distributor_address nvarchar(100)
);

-- sản phẩm 
create table product(
	product_id int primary key auto_increment,
    product_code varchar(100) not null,
    product_name nvarchar(255) not null,
    product_size nvarchar(10) not null,
    product_price int(11) not null,
    product_quantity int(11) not null,
    product_description text,
    foreign key (categories_id) references categories(categories_id),
    foreign key (distributor_id) references distributor(distributor_id)
);

-- đơn mua
create table orders(
	orders_id int primary key auto_increment,
    orders_date datetime,
    orders_pay_status nvarchar(50),
    foreign key (product_id) references product(product_id),
    foreign key (users_id) references users(users_id),
    foreign key (customer_id) references customer(customer_id)
);
