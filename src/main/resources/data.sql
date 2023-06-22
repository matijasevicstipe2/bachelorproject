INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Upper East Side', '123 Main Street', 'New York, NY');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Midtown', '456 Elm Street', 'New York, NY');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Williamsburg', '789 Oak Avenue', 'New York, NY');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Astoria', '321 Pine Lane', 'New York, NY');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Park Slope', '654 Maple Road', 'New York, NY');

INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Hollywood', '123 Sunset Boulevard', 'Los Angeles, CA');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Downtown', '456 Main Street', 'Los Angeles, CA');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Santa Monica', '789 Ocean Avenue', 'Los Angeles, CA');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Venice Beach', '321 Pacific Avenue', 'Los Angeles, CA');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Silver Lake', '654 Hyperion Avenue', 'Los Angeles, CA');

INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Downtown', '123 Main Street', 'Chicago, IL');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - River North', '456 Michigan Avenue', 'Chicago, IL');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Lincoln Park', '789 Clark Street', 'Chicago, IL');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Wicker Park', '321 Division Street', 'Chicago, IL');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Lakeview', '654 Belmont Avenue', 'Chicago, IL');

INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Downtown', '123 Main Street', 'Houston, TX');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Montrose', '456 Westheimer Road', 'Houston, TX');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Rice Village', '789 Kirby Drive', 'Houston, TX');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Heights', '321 19th Street', 'Houston, TX');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Midtown', '654 Travis Street', 'Houston, TX');

INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - South Beach', '123 Ocean Drive', 'Miami, FL');
INSERT INTO gyms (gymname, address, citystate) VALUES ('PowerHouse Fitness - Downtown', '456 Biscayne Boulevard', 'Miami, FL');

INSERT INTO membership_option (name, description, fee, duration, type)
VALUES ('30 Days Trial', '30-days trail for new members', 50.00, 30, 'T');

INSERT INTO membership_option (name, description, fee, duration, type)
VALUES ('Monthly Fee (No Contract)', 'Monthly fee without contract', 50.00, 30, 'NC');

INSERT INTO membership_option (name, description, fee, duration, type)
VALUES ('Yearly Fee (No Contract)', 'Yearly fee without contract', 450.00, 365, 'NC');

INSERT INTO membership_option (name, description, fee, duration, type)
VALUES ('Monthly Fee (6 Months Contract)', 'Monthly fee with contract for 6 months', 35.00, 30, 'M');

INSERT INTO membership_option (name, description, fee, duration, type)
VALUES ('Monthly Fee (12 Months Contract)', 'Monthly fee with contract for 12 months', 25.00, 30, 'Y');

INSERT INTO accounts (firstname, lastname, username, email, password, birthdate, sex, address, citystate)
VALUES ('John', 'Doe', 'user', 'johndoe@example.com', '$2a$10$WClhdH/ZXS6MWlwFvGMfkeWKdVKWpWAUjikCjgZh.As4IYJBGUm7O', '1990-01-01', 'M', '123 Main St', 'City, State');

INSERT INTO authority (id, authority_name)
VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 2);
