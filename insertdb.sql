USE thesisdb;

-- INSERT INTO account (username, password, avatar, role) 
-- VALUES ('admin', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://res.cloudinary.com/dyuafq1hx/image/upload/v1716802738/b920ceyu6uoiix7g0h3c.jpg', 'ADMIN');

-- INSERT INTO admin (last_name, first_name, gender, email, dob, address, account_id)
-- VALUES ('Pham', 'Vuong', 'male', '2151013110@ou.edu.vn', '2003-11-07', 'Tay Ninh', 1);

ALTER TABLE affair DROP COLUMN degree;
