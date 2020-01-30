CREATE TABLE IF NOT EXISTS accounts (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  accountName varchar(255) NOT NULL,
  accountStatus varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS developers (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  developerName varchar(255) NOT NULL,
  accountId INT(11) NOT NULL,
  FOREIGN KEY (accountId) REFERENCES accounts (id)
);


CREATE TABLE IF NOT EXISTS skills (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  skillName varchar(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS developersskills (
  id INT PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
  developerId INT(11) NOT NULL,
  skillId INT(11) NOT NULL,
  FOREIGN KEY (developerId) REFERENCES developers (id),
  FOREIGN KEY (skillId) REFERENCES skills (id)
)