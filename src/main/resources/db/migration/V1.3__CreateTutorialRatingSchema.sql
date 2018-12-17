CREATE TABLE IF NOT EXISTS TutorialRating (
    user_id INTEGER NOT NULL,
    comment VARCHAR(255),
    score SMALLINT(2) UNSIGNED NOT NULL,
    tutorial_id SMALLINT(2) UNSIGNED NOT NULL,
    PRIMARY KEY (tutorial_id , user_id)
)  ENGINE=INNODB;

ALTER TABLE TutorialRating
ADD CONSTRAINT FK_Tutorial_In_TutorialRating
FOREIGN KEY (tutorial_id) REFERENCES Tutorial(id);
