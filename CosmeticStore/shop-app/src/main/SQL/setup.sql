drop database if exists Shop;
create database Shop;
use Shop;

drop table if exists Types;
create table Types (
    id int primary key auto_increment,
    name varchar(20)        -- może nie 20, nwm
);

drop table if exists Brands;
create table Brands (
    id int primary key auto_increment,
    name varchar(30)
);

drop table if exists Colors;
create table Colors (
    id int primary key auto_increment,
    name varchar(30)
);

drop table if exists CoverageLevels;
create table CoverageLevels (
    id int primary key auto_increment,
    numericValue int,
    name varchar(30)
);

drop table if exists Products;
create table Products (
    id int primary key auto_increment,
    color int,
    brand int,
    coverageLevel int,
    type int,

    name varchar(100),      -- tego nie było w projekcie

    foreign key (color) references Colors(id),
    foreign key (brand) references Brands(id),
    foreign key (coverageLevel) references CoverageLevels(id),
    foreign key (type) references Types(id)
);

drop table if exists Offer;
create table Offer (
    id int primary key auto_increment,
    pricePerUnit decimal(5, 2),
    unitsInStock int check (unitsInStock>=0),
    product int,

    foreign key (product) references Products(id) 
);

drop table if exists Clients;
create table Clients (
    id int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    address varchar(100)
);

drop table if exists Invoices;
create table Invoices (
    id int primary key auto_increment,
    dateIssued date,
    client int,
    confirmed boolean default false,

    foreign key (client) references Clients(id)
);

drop table if exists InvoiceLine;
create table InvoiceLine (
    id int primary key auto_increment,
    pricePerUnit decimal(5, 2),
    units int,
    product int,
    invoice int,

    foreign key (product) references Products(id),
    foreign key (invoice) references Invoices(id)
);
