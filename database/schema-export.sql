
    create table band (
        id int4 not null,
        band varchar(255),
        primary key (id)
    );

    create table categorie (
        id int4 not null,
        leeftijdvan int4,
        leeftijdtot int4,
        band int4,
        primary key (id)
    );

    create table gebruiker (
        id int4 not null,
        naam varchar(255),
        gebruikersnaam varchar(255),
        wachtwoord varchar(255),
        organisatie int4,
        primary key (id)
    );

    create table gebruiker_roles (
        gebruiker int4 not null,
        role int4 not null,
        primary key (gebruiker, role)
    );

    create table ingedeeldekarateka (
        id int4 not null,
        karateka int4,
        poule int4,
        vanencompetitie int4,
        punten int4,
        betrouwbarepunten bool,
        primary key (id)
    );

    create table karateka (
        id int4 not null,
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

    create table organisatie (
        id int4 not null,
        naam varchar(255),
        primary key (id)
    );

    create table poule (
        id int4 not null,
        naam varchar(255),
        categorie int4,
        primary key (id)
    );

    create table role (
        id int4 not null,
        naam varchar(255),
        omschrijving varchar(255),
        primary key (id)
    );

    create table vanencompetitie (
        id int4 not null,
        lokatie varchar(255),
        datum timestamp,
        type varchar(255),
        organisatie int4,
        primary key (id)
    );

    alter table categorie 
        add constraint FK5D54E1372E52AEE5 
        foreign key (band) 
        references band;

    alter table gebruiker 
        add constraint FKACAC9A56598A3139 
        foreign key (organisatie) 
        references organisatie;

    alter table gebruiker_roles 
        add constraint FK693FF394B81BE711 
        foreign key (gebruiker) 
        references gebruiker;

    alter table gebruiker_roles 
        add constraint FK693FF3942E61A367 
        foreign key (role) 
        references role;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F8B17A9B13 
        foreign key (karateka) 
        references karateka;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F89D9AB183 
        foreign key (poule) 
        references poule;

    alter table ingedeeldekarateka 
        add constraint FKD61A20F8622803C7 
        foreign key (vanencompetitie) 
        references vanencompetitie;

    alter table karateka 
        add constraint FK3E6FD70C2E52AEE5 
        foreign key (band) 
        references band;

    alter table poule 
        add constraint FK65E824F196C74D3 
        foreign key (categorie) 
        references categorie;

    alter table vanencompetitie 
        add constraint FK546EB891598A3139 
        foreign key (organisatie) 
        references organisatie;
-- create sequence band_id_seq;
-- create sequence categorie_id_seq;
-- create sequence gebruiker_id_seq;
-- create sequence ingedeeldekarateka_id_seq;
-- create sequence karateka_id_seq;
-- create sequence organisatie_id_seq;
-- create sequence poule_id_seq;
-- create sequence role_id_seq;
-- create sequence vanencompetitie_id_seq;
