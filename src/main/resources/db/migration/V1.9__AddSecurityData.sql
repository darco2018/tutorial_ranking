-- USER
-- hashed password: letmein
INSERT INTO security_user (id, username, password, first_name, last_name) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Administrator', 'Adminstrator'),
(2,  'sr_jane', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Jane', 'Doe'),
(3,  'sr_mark', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Mark', 'Smith'),
(4,  'dev', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Dariusz', 'Ustrzycki');

-- ROLES
INSERT INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO security_role (id, role_name, description) VALUES (2, 'ROLE_SR', 'Simple User');

INSERT INTO user_role(user_id, role_id) VALUES
(1, 1), -- give admin ROLE_ADMIN
(2, 2),  -- give Jane ROLE_SR
(3, 2),  -- give Mark ROLE_SR
(4, 1),  -- give dev ROLE_ADMIN
(4, 2);  -- give dev ROLE_SR
