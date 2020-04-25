DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS visit;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee;

CREATE TABLE employee(
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR NOT NULL,
                         last_name VARCHAR NOT NULL,
                         phone VARCHAR NOT NULL UNIQUE,
                         address VARCHAR NOT NULL,
                         city VARCHAR NOT NULL
);

CREATE TABLE customer(
                         id SERIAL PRIMARY KEY,
                         employee_id SERIAL,
                         first_name VARCHAR NOT NULL,
                         last_name VARCHAR NOT NULL,
                         phone VARCHAR NOT NULL UNIQUE,
                         email VARCHAR NOT NULL UNIQUE,
                         FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE visit(
                      id SERIAL PRIMARY KEY,
                      customer_id SERIAL,
                      employee_id SERIAL,
                      date DATE ,
                      service VARCHAR NOT NULL,
                      description VARCHAR,
                      time_of_visit VARCHAR,
                      FOREIGN KEY (customer_id) REFERENCES customer(id),
                      FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE services(
                         id SERIAL PRIMARY KEY,
                         name VARCHAR NOT NULL,
                         price NUMERIC NOT NULL,
                         description VARCHAR NOT NULL
);

CREATE TABLE images(
                       id SERIAL PRIMARY KEY,
                       link VARCHAR,
                       services_id SERIAL,
                       FOREIGN KEY (services_id) REFERENCES services (id)
);

