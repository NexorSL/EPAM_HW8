INSERT INTO skills  values (1,'Java');
INSERT INTO skills  values (2,'C++');
INSERT INTO skills  values (3,'Spring');


INSERT INTO accounts  values (1,'Hex', 'ACTIVE');
INSERT INTO accounts  values (2,'Keln', 'BANNED');
INSERT INTO accounts  values (3,'Lock', 'DELETED');

INSERT INTO developers  values (1,'John', 1);
INSERT INTO developers  values (2,'Lex', 2);
INSERT INTO developers  values (3,'Max', 3);

INSERT INTO developersskills (developerId, skillId) values (1, 1);
INSERT INTO developersskills (developerId, skillId) values (1, 3);
INSERT INTO developersskills (developerId, skillId) values (2, 2);
INSERT INTO developersskills (developerId, skillId) values (3, 1);