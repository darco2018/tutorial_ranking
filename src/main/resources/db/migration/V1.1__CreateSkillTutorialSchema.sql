-- set spring.jpa.hibernate.ddl-auto=none if you want to use this script

CREATE TABLE IF NOT EXISTS Skill (
    skill_code CHAR(2),
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (skill_code)
)  ENGINE=INNODB;


CREATE TABLE IF NOT EXISTS Tutorial (
    id SMALLINT(2) UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    duration VARCHAR(50),
    keywords VARCHAR(50),
    LEVEL VARCHAR(20) NOT NULL,
    LINK VARCHAR(255) NOT NULL,
    medium VARCHAR(50) NOT NULL,
    price DECIMAL(4 , 0 ) UNSIGNED NOT NULL,
    title VARCHAR(100) NOT NULL,
    skill_code CHAR(2),
    PRIMARY KEY (id)
)  ENGINE=INNODB;

ALTER TABLE Tutorial
ADD CONSTRAINT FK_Skill_In_Tutorial
FOREIGN KEY (skill_code) REFERENCES Skill(skill_code);


