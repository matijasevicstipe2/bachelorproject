CREATE TABLE IF NOT EXISTS gyms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gymname VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    citystate VARCHAR(255) NOT NULL,
    opening_hours VARCHAR(255),
    profile_picture_path VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS personal_trainers (
    id INT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    availability BOOLEAN NOT NULL,
    gym_id INT,
    speciality VARCHAR(255),
    profile_picture_path VARCHAR(255),
    FOREIGN KEY (gym_id) REFERENCES gyms (id)
);

CREATE TABLE membership_option (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   fee DECIMAL(10, 2) NOT NULL,
   duration INT NOT NULL,
   type VARCHAR(2) NOT NULL
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

CREATE TABLE IF NOT EXISTS authority (
    id IDENTITY,
    authority_name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS user_authority (
    user_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES accounts(id),
    CONSTRAINT fk_authority FOREIGN KEY (authority_id) REFERENCES authority(id)
    );

CREATE TABLE IF NOT EXISTS user_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    membership_option_id BIGINT,
    personal_trainer_id BIGINT,
    payment_date TIMESTAMP,
    usage INT NOT NULL,
    active BOOLEAN NOT NULL,
    in_gym BOOLEAN,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (membership_option_id) REFERENCES membership_option (id),
    FOREIGN KEY (personal_trainer_id) REFERENCES personal_trainers (id)
);

CREATE TABLE qr_codes (
    id INT PRIMARY KEY,
    account_id INT,
    qr_pass VARCHAR(255) NOT NULL,
    code BLOB,
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS gym_visits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    gym_id BIGINT,
    enter_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    CONSTRAINT fk_account_gv FOREIGN KEY (user_id) REFERENCES user_details (id),
    CONSTRAINT fk_gym_gv FOREIGN KEY (gym_id) REFERENCES gyms (id)
);

CREATE TABLE IF NOT EXISTS session_details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    gym_visit_id BIGINT,
    title VARCHAR(255),
    notes VARCHAR(1000),
    CONSTRAINT fk_gym_visit FOREIGN KEY (gym_visit_id) REFERENCES gym_visits (id)
);

CREATE TABLE IF NOT EXISTS group_classes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    trainer_id BIGINT,
    gym_id BIGINT,
    schedule DATETIME,
    CONSTRAINT fk_trainer FOREIGN KEY (trainer_id) REFERENCES personal_trainers (id),
    CONSTRAINT fk_gym_gc FOREIGN KEY (gym_id) REFERENCES gyms (id)
);

CREATE TABLE IF NOT EXISTS user_group_class (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    group_class_id BIGINT,
    CONSTRAINT fk_account_ugc FOREIGN KEY (user_id) REFERENCES user_details (id),
    CONSTRAINT fk_group_class FOREIGN KEY (group_class_id) REFERENCES group_classes (id)
);



