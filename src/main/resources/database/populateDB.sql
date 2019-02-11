USE userlist;

INSERT INTO country(name)
VALUES ('Ukraine'),
       ('Germany'),
       ('Poland'),
       ('France'),
       ('Italy');

INSERT INTO city(name)
VALUES ('Odessa'),
       ('Hamburg'),
       ('Krakow'),
       ('Amiens'),
       ('Naples');

INSERT INTO street(name)
VALUES ('Pantelemonovska'),
       ('Domstrasse'),
       ('Lubelska'),
       ('Basse des Tanneurs'),
       ('Casanova');

INSERT INTO address(country_id, city_id, street_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5);
