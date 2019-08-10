CREATE TABLE IF NOT EXISTS FEED
(
  ID   INT            NOT NULL PRIMARY KEY,
  NAME varchar2(255)  NOT NULL,
  URL  varchar2(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS STREAM
(
  FEED_ID       INT            NOT NULL,
  URL           varchar2(1000) NOT NULL,
  TITLE         varchar2(255)  NOT NULL,
  GUIDE_NUMBER  INT            NOT NULL,
  LENGTH        INT            NOT NULL,
  TVG_ID        varchar2(255)  NULL,
  TVG_SHIFT     INT            NULL,
  TVG_NAME      varchar2(255)  NULL,
  TVG_LOGO      varchar2(255)  NULL,
  AUDIO_TRACK   varchar2(10)   NULL,
  ASPECT_RATION varchar2(10)   NULL,
  GROUP_TITLE   varchar2(255)  NULL,
  FOREIGN KEY (FEED_ID) REFERENCES Feed (ID),
  PRIMARY KEY (FEED_ID, URL)
);

CREATE TABLE IF NOT EXISTS CONFIGURATION
(
  NAME        varchar2(255)  NOT NULL PRIMARY KEY,
  VALUE       varchar2(1000) NULL,
  DESCRIPTION varchar2(1000) NULL
);