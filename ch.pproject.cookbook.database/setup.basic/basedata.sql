-- Einheiten: id's 1-100
INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (1, 'Liter','l',1, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (2, 'Deziliter','dl', 1, 10);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (3, 'Centiliter','cl', 1, 100);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (4, 'Milliliter','ml', 1, 1000);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (5, 'Kilogramm','kg',5, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (6, 'Gramm','g', 5, 1000);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (7, 'Milligramm','mg', 5, 1000000);
        
INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (8, 'Stück','Stk', 8, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (9, 'Esslöffel','EL', 9, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (10, 'Teelöffel','TL', 10, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (11, 'Prise','Pr', 11, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (12, 'Messerspitze','Msp', 12, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (13, 'Blatt','Bl', 13, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (14, 'Knolle','Kn', 14, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (15, 'Tasse','T', 15, 1);

INSERT INTO measure (measure_nr, measure_name, acronym, reference_measure_nr, factor)
		VALUES (16, 'Bund','Bd', 16, 1);

UPDATE MEASURE SET DISPLAY_NAME = CONCAT('', measure_name, CONCAT(' (', acronym, ')'));

INSERT INTO ingredient (ingredient_nr, ingredient_name)
		VALUES (nextval('cbseq'), 'Mehl');

INSERT INTO ingredient_measure (ingredient_nr, measure_nr) 
		VALUES (curval('cbseq'), 5);

INSERT INTO ingredient_measure (ingredient_nr, measure_nr) 
		VALUES (curval('cbseq'), 6);

INSERT INTO ingredient_measure (ingredient_nr, measure_nr) 
		VALUES (curval('cbseq'), 7);

