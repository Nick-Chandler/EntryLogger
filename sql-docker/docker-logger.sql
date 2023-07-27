CREATE DATABASE entryloggerdb;
use entryloggerdb;

CREATE TABLE logger(
  Pass_ID int AUTO_INCREMENT,
  Pass_Time time,
  Pass_Date date,
  PRIMARY KEY (Pass_ID)
);

CREATE TABLE dailylog(
  Date_Recorded Date,
  Passes int,
  PRIMARY KEY (Date_Recorded)
)