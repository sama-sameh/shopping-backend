IF DB_ID('shoppingdb') IS NULL
BEGIN
    CREATE DATABASE shoppingdb;
END
GO

USE shoppingdb;
GO

-- users table
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    email NVARCHAR(255) UNIQUE,
    password NVARCHAR(255)
);
GO

-- roles table
CREATE TABLE roles (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50)
);
GO

-- user_roles table
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
GO

-- products table
CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    description NVARCHAR(MAX),
    price FLOAT,
    image_url NVARCHAR(500)
);
GO

-- contact_messages table
CREATE TABLE contact_messages (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    email NVARCHAR(255),
    phone NVARCHAR(20),
    message NVARCHAR(MAX)
);
GO

-- cart_items table
CREATE TABLE cart_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
GO

-- orders table
CREATE TABLE orders (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT,
    order_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(50),
    total FLOAT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
GO

-- order_items table
CREATE TABLE order_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price FLOAT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
GO
