DROP TABLE IF EXISTS schedules;

CREATE TABLE schedules (
  id int unsigned AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  scheduleDate Date NOT NULL,
  scheduleTime Time NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO schedules (title,scheduleDate,scheduleTime) VALUES ("子供の予防接種","20240220","140000")

