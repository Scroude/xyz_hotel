CREATE TABLE user
(
    id        bigint,
    lastname  varchar(30),
    firstname varchar(30),
    password  varchar(500),
    email     varchar(50),
    phone     varchar(10),
    primary key (id)
);

CREATE TABLE wallet
(
    id       bigint,
    id_user  bigint,
    amount   double,
    currency varchar(20),
    primary key (id),
    foreign key (id_user) references user (id)
);

CREATE TABLE room
(
    id               bigint,
    bed              varchar(30),
    wifi             boolean,
    tv               varchar(30),
    minibar          boolean,
    air_conditionner boolean,
    bathtub          boolean,
    terrace          boolean,
    price            double
);

CREATE TABLE reservation
(
    id        bigint,
    id_user   bigint,
    price     double,
    status    int,
    date      date,
    is_halfed boolean
);

CREATE TABLE payment
(
    id             bigint,
    id_reservation bigint,
    id_user        bigint,
    amount         double
);