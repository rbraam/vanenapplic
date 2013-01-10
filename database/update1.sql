ALTER TABLE karateka
   ADD COLUMN beginpuntenku integer;
ALTER TABLE karateka
   ADD COLUMN beginpuntenka integer;
ALTER TABLE karateka
   ALTER COLUMN beginpuntenku SET DEFAULT 0;
ALTER TABLE karateka
   ALTER COLUMN beginpuntenka SET DEFAULT 0;
   
ALTER TABLE ingedeeldekarateka
   ADD COLUMN punten integer;
   
   
UPDATE karateka
   SET beginpuntenku=0
 WHERE beginpuntenku is null;
UPDATE karateka
   SET beginpuntenka=0
 WHERE beginpuntenka is null;
 
ALTER TABLE ingedeeldekarateka ADD COLUMN betrouwbarepunten boolean DEFAULT true;
