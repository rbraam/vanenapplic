create table gebruiker_roles (
gebruiker int4 not null,
role int4 not null,
primary key (gebruiker, role)
);

create table role (
id serial,
naam varchar(255),
omschrijving varchar(255),
primary key (id)
);

alter table gebruiker_roles 
        add constraint FK693FF394B81BE711 
        foreign key (gebruiker) 
        references gebruiker;

    alter table gebruiker_roles 
        add constraint FK693FF3942E61A367 
        foreign key (role) 
        references role;
        
        INSERT INTO role(
            naam, omschrijving)
    VALUES ('beheerder','beheerder van vanencompetities');

INSERT INTO role(
            naam, omschrijving)
    VALUES ('superbeheerder','superbeheerder van vanenapplic');
