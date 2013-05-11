create table organisatie (
    id serial,
    naam varchar(255),
    primary key (id)
);

create table gebruiker (
    id serial,
    naam varchar(255),
    gebruikersnaam varchar(255),
    wachtwoord varchar(255),
    organisatie int4,
    primary key (id)
);

ALTER TABLE vanencompetitie ADD COLUMN organisatie integer;

alter table gebruiker 
        add constraint FKACAC9A56598A3139 
        foreign key (organisatie) 
        references organisatie;

alter table vanencompetitie 
        add constraint FK546EB891598A3139 
        foreign key (organisatie) 
        references organisatie;

