SELECT * FROM tbproducto;

SELECT * FROM tbproducto WHERE PRECIO_LISTA BETWEEN 28.49 AND 28.52;

SELECT * FROM tbproducto WHERE PRECIO_LISTA >= 28.49 AND PRECIO_LISTA <= 28.52;

SELECT * FROM tbproducto WHERE ENVASE = 'Lata' OR ENVASE = 'Botella PET' ;

SELECT * FROM tbproducto WHERE PRECIO_LISTA >= 15 AND PRECIO_LISTA <= 25;

SELECT * FROM tbproducto WHERE (PRECIO_LISTA >= 15 AND TAMANO = '1 Litro') OR (ENVASE = 'Lata' OR ENVASE = 'Botella PET');

SELECT * FROM tabla_de_vendedores WHERE DE_VACACIONES = 1 AND YEAR(FECHA_ADMISION) < 2016;

SELECT NOMBRE, MATRICULA FROM tabla_de_vendedores;