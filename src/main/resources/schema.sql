CREATE TABLE IF NOT EXISTS gyms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gymname VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    citystate VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthdate DATE,
    sex VARCHAR(1),
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    citystate VARCHAR(255) NOT NULL,
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

