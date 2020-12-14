# CREATE DATABASE IF NOT EXISTS football;
USE football;
DROP TABLE IF EXISTS Player, Team;
CREATE TABLE Team
(
    ID   int         NOT NULL AUTO_INCREMENT,
    Name varchar(30) NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE (Name)
);
CREATE TABLE Player
(
    ID         int         NOT NULL AUTO_INCREMENT,
    Surname    varchar(30) NOT NULL,
    Name       varchar(30) NOT NULL,
    Patronymic varchar(30) NOT NULL,
    TeamID     int,
    PRIMARY KEY (ID),
    FOREIGN KEY (TeamID) REFERENCES Team (ID)
);
INSERT INTO Team (Name)
VALUES ('Акулы'),
       ('Орлы');
INSERT INTO Player (Surname, Name, Patronymic, TeamID)
VALUES ('Ситников', 'Севастьян', 'Серапионович', 1),
       ('Кудрявцев', 'Глеб', 'Валерьянович', 1),
       ('Мишин', 'Эдуард', 'Рудольфович', 1),
       ('Блохин', 'Роберт', 'Тимофеевич', 1),
       ('Никонов', 'Августин', 'Даниилович', 1),
       ('Беспалов', 'Емельян', 'Федорович', 1),
       ('Шарапов', 'Венедикт', 'Авксентьевич', 1),
       ('Андреев', 'Орест', 'Лукьевич', 1),
       ('Борисов', 'Ярослав', 'Протасьевич', 1),
       ('Афанасьев', 'Май', 'Валентинович', 1),
       ('Макаров', 'Лукьян', 'Михайлович', 1),

       ('Веселов', 'Мечислав', 'Григорьевич', 2),
       ('Гаврилов', 'Устин', 'Улебович', 2),
       ('Панфилов', 'Гурий', 'Валерьянович', 2),
       ('Королёв', 'Артур', 'Григорьевич', 2),
       ('Чернов', 'Гордий', 'Эдуардович', 2),
       ('Кулагин', 'Май', 'Даниилович', 2),
       ('Коновалов', 'Панкратий', 'Максимович', 2),
       ('Ершов', 'Аким', 'Тимурович', 2),
       ('Абрамов', 'Иннокентий', 'Макарович', 2),
       ('Бобылёв', 'Велорий', 'Евсеевич', 2),
       ('Богданов', 'Степан', 'Куприянович', 2);