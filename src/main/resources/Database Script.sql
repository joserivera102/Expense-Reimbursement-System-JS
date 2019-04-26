ALTER TABLE
	ERS_USERS DROP
		CONSTRAINT USER_ROLES_FK;

ALTER TABLE
	ERS_USERS DROP
		CONSTRAINT ERS_USERS__UNV1;

ALTER TABLE
	ERS_REIMBURSEMENT DROP
		CONSTRAINT ERS_REIMBURSEMENT_TYPE_FK;

ALTER TABLE
	ERS_REIMBURSEMENT DROP
		CONSTRAINT ERS_REIMBURSEMENT_STATUS_FK;

ALTER TABLE
	ERS_REIMBURSEMENT DROP
		CONSTRAINT ERS_USERS_FK_RESLVR;

ALTER TABLE
	ERS_REIMBURSEMENT DROP
		CONSTRAINT ERS_USERS_FK_AUTH;

ALTER TABLE
	ERS_USERS DROP
		CONSTRAINT ERS_USERS_PK;

ALTER TABLE
	ERS_REIMBURSEMENT DROP
		CONSTRAINT ERS_REIMBURSEMENT_PK;

DROP
	TABLE
		ers_reimbursement_status;

DROP
	TABLE
		ers_reimbursement_type;

DROP
	TABLE
		ers_user_roles;

DROP
	TABLE
		ers_users;

DROP
	TABLE
		ers_reimbursement;

DROP
	SEQUENCE ers_users_pk_sequence;

DROP
	SEQUENCE ers_reimbursement_pk_sequence;

CREATE
	TABLE
		ers_reimbursement_status ( reimb_status_id NUMBER NOT NULL,
		reimb_status VARCHAR2(10) NOT NULL,
		CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id) );

CREATE
	TABLE
		ers_reimbursement_type ( reimb_type_id NUMBER NOT NULL,
		reimb_type VARCHAR2(10) NOT NULL,
		CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id) );

CREATE
	TABLE
		ers_user_roles ( ers_user_role_id NUMBER NOT NULL,
		user_role VARCHAR2(15) NOT NULL,
		CONSTRAINT ers_user_roles_pk PRIMARY KEY (ers_user_role_id) );

CREATE
	TABLE
		ers_users ( ers_users_id NUMBER NOT NULL,
		ers_username VARCHAR2(50) NOT NULL,
		ers_password VARCHAR2(256) NOT NULL,
		user_first_name VARCHAR2(100) NOT NULL,
		user_last_name VARCHAR2(100) NOT NULL,
		user_email VARCHAR2(150) NOT NULL,
		user_role_id NUMBER NOT NULL,
		CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id),
		CONSTRAINT ers_users__unv1 UNIQUE ( user_email,
		ers_username ),
		CONSTRAINT user_roles_fk FOREIGN KEY (user_role_id) REFERENCES ers_user_roles (ers_user_role_id) );

CREATE
	TABLE
		ers_reimbursement ( reimb_id NUMBER NOT NULL,
		reimb_amount NUMBER NOT NULL,
		reimb_submitted TIMESTAMP NOT NULL,
		reimb_resolved TIMESTAMP,
		reimb_description VARCHAR2(250),
		reimb_receipt BLOB,
		reimb_author NUMBER NOT NULL,
		reimb_resolver NUMBER,
		reimb_status_id NUMBER NOT NULL,
		reimb_type_id NUMBER NOT NULL,
		CONSTRAINT ers_reimbursement_pk PRIMARY KEY (reimb_id),
		CONSTRAINT ers_users_fk_auth FOREIGN KEY (reimb_author) REFERENCES ers_users (ers_users_id),
		CONSTRAINT ers_users_fk_reslvr FOREIGN KEY (reimb_resolver) REFERENCES ers_users (ers_users_id),
		CONSTRAINT ers_reimbursement_status_fk FOREIGN KEY (reimb_status_id) REFERENCES ers_reimbursement_status (reimb_status_id),
		CONSTRAINT ers_reimbursement_type_fk FOREIGN KEY (reimb_type_id) REFERENCES ers_reimbursement_type (reimb_type_id) );

CREATE
	SEQUENCE ers_users_pk_sequence MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1
START WITH
	1;

CREATE
	SEQUENCE ers_reimbursement_pk_sequence MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1
START WITH
	1;

CREATE
OR REPLACE
TRIGGER ers_users_pk_trigger BEFORE INSERT
	ON
	ers_users FOR EACH ROW
BEGIN
	SELECT
		ers_users_pk_sequence.NEXTVAL INTO
			:new.ers_users_id
		FROM
			dual;
END;

CREATE
OR REPLACE
TRIGGER ers_reimbursement_pk_trigger BEFORE INSERT
	ON
	ers_reimbursement FOR EACH ROW
BEGIN
	SELECT
		ers_reimbursement_pk_sequence.NEXTVAL INTO
			:new.reimb_Id
		FROM
			dual;
END;

INSERT
	INTO
		ers_reimbursement_type
	VALUES ( 1,
	'LODGING' );

INSERT
	INTO
		ers_reimbursement_type
	VALUES ( 2,
	'TRAVEL' );

INSERT
	INTO
		ers_reimbursement_type
	VALUES ( 3,
	'FOOD' );

INSERT
	INTO
		ers_reimbursement_type
	VALUES ( 4,
	'OTHER' );

INSERT
	INTO
		ers_reimbursement_status
	VALUES ( 1,
	'PENDING' );

INSERT
	INTO
		ers_reimbursement_status
	VALUES ( 2,
	'APPROVED' );

INSERT
	INTO
		ers_reimbursement_status
	VALUES ( 3,
	'DENIED' );

INSERT
	INTO
		ers_user_roles
	VALUES ( 1,
	'EMPLOYEE' );

INSERT
	INTO
		ers_user_roles
	VALUES ( 2,
	'FINANCE_MANAGER' );

INSERT
	INTO
		ers_users
	VALUES ( 0,
	'jrivera',
	'Password1',
	'Jose',
	'Rivera',
	'jose@yahoo.com',
	2 );

COMMIT;