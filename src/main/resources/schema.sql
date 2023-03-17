--This is the tbl_loan_applicant tables schema
create table if not exists tbl_loan_applicant
(
    id                 bigserial
        constraint tbl_loan_applicant_pk
            primary key,
    created_at         timestamp default CURRENT_TIMESTAMP,
    updated_at         timestamp default CURRENT_TIMESTAMP,
    conversation_id    varchar(50),
    deleted_at         timestamp default CURRENT_TIMESTAMP,
    document_number    varchar(50),
    document_type      varchar(50),
    email              varchar(100),
    first_name         varchar(50),
    last_name          varchar(50),
    msisdn             varchar(250),
    remarks            varchar(200),
    status_id          bigint
        constraint tbl_loan_applicant_tbl_status_id_fk
            references tbl_status,
    sponsor_program_id integer default 0 not null
);

create unique index tbl_loan_applicant_msisdn_uindex
    on tbl_loan_applicant (msisdn);

create unique index tbl_loan_applicant_conversation_id_uindex
    on tbl_loan_applicant (conversation_id);

create index tbl_loan_applicant_msisdn_idx
    on tbl_loan_applicant (msisdn);


--The loan tables
create table if not exists tbl_loan
(
    id                      bigserial
        constraint tbl_loan_pk
            primary key,
    created_at              timestamp default CURRENT_TIMESTAMP,
    updated_at              timestamp default CURRENT_TIMESTAMP,
    deleted_at         timestamp default CURRENT_TIMESTAMP,
    conversation_id         varchar(50),
    credit_score            integer,
    deposit_amount          double precision,
    installment_amount      double precision,
    loan_amount_balance     double precision,
    loan_completion_date    timestamp,
    loan_creation_date      timestamp,
    loan_limit              double precision,
    loan_start_date         timestamp,
    next_due_date           timestamp,
    principle_amount        double precision,
    remarks                 varchar(200),
    statement_id            varchar(50),

    loan_applicant_id       bigint
        constraint tbl_loan_tbl_loan_applicant_id_fk
            references tbl_loan_applicant,
    payment_plan_id         bigint
        constraint tbl_loan_tbl_payment_plan_id_fk
            references device_financing.tbl_payment_plan,
    status_id               bigint
        constraint tbl_loan_tbl_status_id_fk
            references device_financing.tbl_status,
    mpesa_transaction_id    varchar(20),
    constraint tbl_loan_check
        check (date(next_due_date) > date(loan_start_date))
);