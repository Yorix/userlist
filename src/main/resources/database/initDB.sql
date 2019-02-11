CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

DROP SCHEMA IF EXISTS userlist;
CREATE SCHEMA IF NOT EXISTS userlist;
USE userlist;

CREATE TABLE IF NOT EXISTS user
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  firstname  VARCHAR(50) NOT NULL,
  lastname   VARCHAR(50) NOT NULL,
  address_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS address
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  country_id INT NOT NULL,
  city_id    INT NOT NULL,
  street_id  INT NOT NULL
);

CREATE TABLE IF NOT EXISTS country
(
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS city
(
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS street
(
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

# ALTER TABLE address ADD FOREIGN KEY (id) REFERENCES user (address_id)
# ON UPDATE RESTRICT ON DELETE CASCADE;
