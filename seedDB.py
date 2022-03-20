import psycopg
import os
from faker import Faker

fake = Faker()

pg_user = os.getenv('POSTGRES_USER')
pg_pass = os.getenv('POSTGRES_PASSWORD')
pg_host = os.getenv('POSTGRES_HOST')
pg_port = os.getenv('POSTGRES_PORT')

# Connect to an existing database
with psycopg.connect(
        host=pg_host,
        port=pg_port,
        user=pg_user,
        password=pg_pass,
) as conn:

    # Open a cursor to perform database operations
    with conn.cursor() as cur:
        cur.execute("""
                    create schema if not exists p1ERS;
                    create extension if not exists pgcrypto;
                    
                    drop table if exists p1ERS.ers_reimbursement_status cascade;                   

                    create table if not exists p1ERS.ers_reimbursement_status (
                        reimb_status_id SERIAL primary key,
                        reimb_status VARCHAR(10) not null
                        );

                    drop table if exists p1ERS.ers_reimbursement_type cascade;
                    create table if not exists p1ERS.ers_reimbursement_type (
                        reimb_type_id SERIAL primary key,
                        reimb_type VARCHAR(10) not null
                        );
                        
                    drop table if exists p1ERS.ers_user_roles cascade;
                    create table if not exists p1ERS.ers_user_roles (
                        ers_user_role_id SERIAL primary key,
                        user_role VARCHAR(10) not null
                        );
                        
                    drop table if exists p1ERS.ers_users cascade;
                    create table if not exists p1ERS.ers_users (
                        ers_users_id SERIAL primary key,
                        ers_username VARCHAR(50) unique not null,
                        ers_password text not null,
                        user_first_name VARCHAR(100) not null,
                        user_last_name VARCHAR(100) not null,
                        user_email VARCHAR(150) unique not null,
                        user_role_id integer not null,
                        
                        constraint user_roles_fk foreign key(user_role_id) references p1ERS.ers_user_roles(ers_user_role_id)
                        );
                        
                    drop table if exists p1ERS.ers_reimbursements cascade;
                    create table if not exists p1ERS.ers_reimbursements (
                        reimb_id SERIAL primary key,
                        reimb_amount double precision not null,
                        reimb_sumbitted timestamp not null,
                        reimb_resolved timestamp,
                        reimb_description VARCHAR(250),
                        reimb_receipt text,
                        reimb_author integer not null,
                        reimb_resolver integer not null,
                        reimb_status_id integer not null,
                        reimb_type_id integer not null,
                        
                        constraint ers_users_fk_auth foreign key(reimb_author) references p1ERS.ers_users(ers_users_id),
                        constraint ers_users_fk_reslvr foreign key(reimb_resolver) references p1ERS.ers_users(ers_users_id),
                        constraint ers_reimbursement_status_fk foreign key(reimb_status_id) references p1ERS.ers_reimbursement_status(reimb_status_id),
                        constraint ers_reimbursement_type_fk foreign key(reimb_type_id) references p1ERS.ers_reimbursement_type(reimb_type_id)
                        );
                    """)  
                    
        cur.execute("""
                    insert into p1ERS.ers_reimbursement_status (reimb_status)
                    values
                    ('PENDING'),
                    ('APPROVED'),
                    ('DENIED');

                    insert into p1ERS.ers_reimbursement_type (reimb_type)
                    values
                    ('LODGING'),
                    ('TRAVEL'),
                    ('FOOD'),
                    ('OTHER');

                    insert into p1ERS.ers_user_roles  (user_role)
                    values
                    ('MANAGER'),
                    ('EMPLOYEE');
                    
                    insert into p1ERS.ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
                    VALUES ('admin', crypt('admin', gen_salt('md5')), 'admin', 'admin', 'admin@admin.com', 1)
                    """)
        
        for i in range(100):
            username = fake.user_name()
            password = 'default'
            fname = fake.first_name()
            lname = fake.last_name()
            email = fake.email()
            role = 2
            cur.execute("""
                        INSERT INTO p1ERS.ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
                        VALUES (%s,crypt(%s, gen_salt('md5')),%s,%s,%s,%s)
                        """,
                        (username, password, fname, lname, email, role))
        conn.commit();