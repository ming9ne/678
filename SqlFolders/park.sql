use sw678;

CREATE TABLE park (
	p_id INT PRIMARY KEY,
    p_name VARCHAR(300),
    p_addr VARCHAR(300),
    p_telnum VARCHAR(300),
    p_locx VARCHAR(300),
    p_locy VARCHAR(300),
    p_img VARCHAR(300),
    p_url VARCHAR(300)
);


SELECT * FROM PARK;

DROP TABLE PARK;