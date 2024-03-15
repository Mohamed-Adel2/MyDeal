-- create database mydeal;
CREATE TABLE Address (
	address_id INT AUTO_INCREMENT PRIMARY KEY,
	street VARCHAR(255),
	city VARCHAR(255),
	apartment INT
);
CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(11) UNIQUE,
    date_of_birth DATE,
    password VARCHAR(255),
    credit_limit DECIMAL(10,2),
    address_id INT,
       FOREIGN KEY (address_id) REFERENCES Address(address_id)
);
CREATE TABLE Categories(
	category_id INT AUTO_INCREMENT PRIMARY KEY,
    categoty_name VARCHAR(255)
);
CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255),
    description TEXT,
    price DECIMAL(10,2),
    available_quantity INT,
    average_rating DECIMAL(3,2),
    category_id INT,
     FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE OrderDetails (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE CustomerCart (
    customer_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (customer_id, product_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE ProductImages (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    image BLOB,
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE Reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    stars INT,
    description TEXT,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);
