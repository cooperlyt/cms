DROP DATABASE IF EXISTS CMS;
CREATE DATABASE CMS;

USE CMS;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS category;
/* Create Tables */

DROP TABLE IF EXISTS WORKER_NODE;
CREATE TABLE WORKER_NODE
(
    ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
    HOST_NAME VARCHAR(64) NOT NULL COMMENT 'host name',
    PORT VARCHAR(64) NOT NULL COMMENT 'port',
    TYPE INT NOT NULL COMMENT 'node type: CONTAINER(1), ACTUAL(2), FAKE(3)',
    LAUNCH_DATE DATE NOT NULL COMMENT 'launch date',
    MODIFIED TIMESTAMP NOT NULL COMMENT 'modified time',
    CREATED TIMESTAMP NOT NULL COMMENT 'created time',
    PRIMARY KEY(ID)
)
    COMMENT='DB WorkerID Assigner for UID Generator',ENGINE = INNODB;

CREATE TABLE article
(
    id bigint NOT NULL,
    thumbnail varchar(32),
    content text,
    summary varchar(1024),
    title varchar(256) NOT NULL,
    sub_title varchar(512),
    tags varchar(512),
    time timestamp NOT NULL,
    category_id bigint NOT NULL,
    mime_type varchar(512) NOT NULL,
    doc_summary text,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



CREATE TABLE category
(
    id bigint NOT NULL,
    name varchar(16) NOT NULL,
    description varchar(64),
    parent_id bigint,
    _order int NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;






CREATE TABLE document
(
    id bigint NOT NULL,
    md5 varchar(32),
    mime_type varchar(512),
    size int,
    ext varchar(16),
    content varchar(32) NOT NULL,
    article_id bigint NOT NULL,
    name varchar(32) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;




CREATE TABLE image
(
    id bigint NOT NULL,
    content varchar(32) NOT NULL,
    article_id bigint NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;




/* Create Foreign Keys */

ALTER TABLE document
    ADD FOREIGN KEY (article_id)
        REFERENCES article (id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE image
    ADD FOREIGN KEY (article_id)
        REFERENCES article (id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE article
    ADD FOREIGN KEY (category_id)
        REFERENCES category (id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE category
    ADD FOREIGN KEY (parent_id)
        REFERENCES category (id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;

SET SESSION FOREIGN_KEY_CHECKS=1;