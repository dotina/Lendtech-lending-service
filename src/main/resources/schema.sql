--This is the tbl_loan_applicant tables schema
create table if not exists tbl_loan_applicant
(
    id                 bigserial
        constraint tbl_loan_applicant_pk
            primary key,
    created_at         timestamp default CURRENT_TIMESTAMP,
    updated_at         timestamp default CURRENT_TIMESTAMP,
    conversation_id    varchar(50),
    deleted_status_id      bigint default 0 not null,
    document_number    varchar(50),
    document_type      varchar(50),
    email              varchar(100),
    first_name         varchar(50),
    last_name          varchar(50),
    msisdn             varchar(250),
    remarks            varchar(200),
    status_id          bigint
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
    deleted_status_id         bigint default 0 not null,
    conversation_id         varchar(50) not null,
    credit_score            integer,
    installment_amount      double precision,
    loan_amount_balance     double precision,
    next_due_date    timestamp,
    loan_completion_date    timestamp,
    loan_creation_date      timestamp,
    loan_limit              double precision,
    principle_amount        double precision,
    disbursed_amount        double precision,
    loan_interest_rate        double precision,
    active_status           boolean default false not null,
    remarks                 varchar(200),

    loan_applicant_id       bigint
        constraint tbl_loan_tbl_loan_applicant_id_fk
            references tbl_loan_applicant,
    bank_lending_transaction_id    varchar(20)
--    constraint tbl_loan_check
--        check (date(next_due_date) > date(loan_start_date))
);

--The loan transaction tables
create table if not exists tbl_transaction
(
    id                      bigserial
        constraint tbl_transaction_pk
            primary key,
    created_at              timestamp default CURRENT_TIMESTAMP,
    updated_at              timestamp default CURRENT_TIMESTAMP,
    deleted_status_id         bigint default 0 not null,
    conversation_id         varchar(50) not null,
    amount      double precision,
    remarks                 varchar(200),

    loan_id       bigint
        constraint tbl_transaction_tbl_loan_id_fk
            references tbl_transaction,
    bank_lending_transaction_id    varchar(20)
);