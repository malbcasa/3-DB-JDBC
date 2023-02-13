create database control_de_stock;

 use control_de_stock;
 
 create table producto (
 id INT AUTO_INCREMENT,
 nombre VARCHAR(50) NOT NULL,
 descripcion VARCHAR(255),
 cantidad INT NOT NULL DEFAULT 0,
 PRIMARY KEY(id)
 ) Engine = InnoDB;
 
 select * from producto;
 
 insert into producto (nombre, descripcion, cantidad) values('Mesa', 'Mesa de 4 lugares', 10);
 insert into producto (nombre, descripcion, cantidad) values('Celular', 'Celular Samsung', 50);
 
  select * from producto;
 
 