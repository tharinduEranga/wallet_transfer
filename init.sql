CREATE DATABASE IF NOT EXISTS wallet_transfer_db;
USE wallet_transfer_db;
/*user wallet account table*/
CREATE TABLE IF NOT EXISTS account
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(256),
    passport_id VARCHAR(256) NOT NULL,
    balance     DECIMAL(10, 2),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = INNODB;

/*account transactions table*/
CREATE TABLE IF NOT EXISTS transaction
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    cr_account_id  INT,
    dr_account_id  INT,
    amount         DECIMAL(10, 2),
    cr_new_balance DECIMAL(10, 2),
    dr_new_balance DECIMAL(10, 2),
    reference      VARCHAR(256),
    description    LONGTEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cr_account FOREIGN KEY (cr_account_id)
        REFERENCES account (id),
    CONSTRAINT fk_dr_account FOREIGN KEY (dr_account_id)
        REFERENCES account (id)
) ENGINE = INNODB;

/*account transaction error log details table*/
CREATE TABLE IF NOT EXISTS transaction_fail_log
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    cr_account_id INT,
    dr_account_id INT,
    amount        DECIMAL(10, 2),
    description   LONGTEXT,
    error         LONGTEXT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    /*No Foreign Keys have been added since this is a log table*/
) ENGINE = INNODB;

/*version column*/
ALTER TABLE account ADD COLUMN version INT DEFAULT 1;

/*data*/

INSERT INTO account(name, passport_id, balance, created_at)
VALUES ('John', '9944580', 1200, CURRENT_TIMESTAMP);
INSERT INTO account(name, passport_id, balance, created_at)
VALUES ('Anna', '3549580', 5000, CURRENT_TIMESTAMP);
