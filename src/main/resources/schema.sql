CREATE TABLE IF NOT EXISTS gyms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gymname VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    citystate VARCHAR(255) NOT NULL
    );

CREATE TABLE membership_option (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   fee DECIMAL(10, 2) NOT NULL
);


CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    sex VARCHAR(1) NOT NULL,
    address VARCHAR(255),
    citystate VARCHAR(255),
    profilepicture BLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

create table if not exists authority (
    id identity,
    authority_name varchar(100) not null unique
    );

create table if not exists user_authority (
    user_id bigint not null,
    authority_id bigint not null,
    constraint fk_user foreign key (user_id) references accounts(id),
    constraint fk_authority foreign key (authority_id) references authority(id)
    );

CREATE TABLE IF NOT EXISTS user_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    membership_option_id BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    usage INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (membership_option_id) REFERENCES membership_option (id)
);
