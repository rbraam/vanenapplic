
    create table band (
        id serial not null,
        band varchar(255),
        primary key (id)
    );

    create table categorie (
        id serial not null,
        leeftijdvan int4,
        leeftijdtot int4,
        band int4,
        primary key (id)
    );

    create table ingedeeldekarateka (
        id serial not null,
        karateka int4,
        poule int4,
        vanencompetitie int4,
        punten int4,
        betrouwbarepunten bool,
        primary key (id)
    );

    create table karateka (
        id serial not null,
        voornaam varchar(255),
        achternaam varchar(255),
        tussenvoegsel varchar(255),
        geslacht varchar(255),
        geboortedatum timestamp,
        gewicht float8,
        beginpuntenku int4,
        beginpuntenka int4,
        band int4,
        primary key (id)
    );

    create table poule (
        id serial not null,
        naam varchar(255),
        categorie int4,
        primary key (id)
    );

    create table vanencompetitie (
        id serial not null,
        lokatie varchar(255),
        datum timestamp,
        type varchar(255),
        primary key (id)
    );

    alter table categorie 
        add constraint FK5D54E1372E52AEE5 
        foreign key (band) 
        references band;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F8B17A9B13 
        foreign key (karateka) 
        references karateka;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F8622803C7 
        foreign key (vanencompetitie) 
        references vanencompetitie;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F89D9AB183 
        foreign key (poule) 
        references poule;

    alter table karateka 
        add constraint FK3E6FD70C2E52AEE5 
        foreign key (band) 
        references band;

    alter table poule 
        add constraint FK65E824F196C74D3 
        foreign key (categorie) 
        references categorie;
-- create sequence band_id_seq;
-- create sequence categorie_id_seq;
-- create sequence ingedeeldekarateka_id_seq;
-- create sequence karateka_id_seq;
-- create sequence poule_id_seq;
-- create sequence vanencompetitie_id_seq;
