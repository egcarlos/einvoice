
-- borrado del esquema privado
-- drop table BL_DOCUMENT cascade constraints;
-- drop table BL_DOCUMENT_ATTR cascade constraints;
-- drop table BL_DOCUMENT_AUX cascade constraints;
-- drop table BL_DOCUMENT_DATA cascade constraints;
-- drop table BL_DOCUMENT_LEG cascade constraints;
-- drop table BL_DOCUMENT_RESP cascade constraints;
-- drop table BL_ITEM cascade constraints;
-- drop table BL_ITEM_ATTR cascade constraints;
-- drop table BL_ITEM_AUX cascade constraints;
-- drop table BL_MESSAGE_TRACE cascade constraints;
-- drop table CF_KEYSTORE cascade constraints;
-- drop table CF_SECURITYVALUES cascade constraints;
-- drop sequence BL_DOCUMENT_SEQ;
-- drop sequence BL_MESSAGE_TRACE_SEQ;

create table BL_DOCUMENT (
    DOCUMENT_ID number(19,0) not null,
    CLIENT_ID varchar2(20 char),
    DOCUMENT_DATE varchar2(40 char),
    DOCUMENT_NUMBER varchar2(20 char),
    DOCUMENT_TYPE varchar2(10 char),
    DOCUMENT_HASH varchar2(1000 char),
    SIGNATURE varchar2(2000 char),
    DOCUMENT_STATUS varchar2(255 char),
    DOCUMENT_STEP varchar2(255 char),
    primary key (DOCUMENT_ID)
);


create table BL_DOCUMENT_ATTR (
    ATTR_NAME varchar2(50 char) not null,
    ATTR_VALUE varchar2(1000 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (ATTR_NAME, DOCUMENT_ID)
);

create table BL_DOCUMENT_AUX (
    AUX_NAME varchar2(50 char) not null,
    AUX_LENGTH varchar2(50 char),
    AUX_ORDER number(19,0),
    AUX_VALUE varchar2(1000 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (DOCUMENT_ID, AUX_NAME)
);


create table BL_DOCUMENT_DATA (
    DATA_NAME varchar2(50 char) not null,
    RAW_DATA blob,
    REPLICATE number(1,0),
    SOURCE varchar2(1000 char),
    STATUS varchar2(20 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (DATA_NAME, DOCUMENT_ID)
);


create table BL_DOCUMENT_LEG (
    AUX_NAME varchar2(50 char) not null,
    AUX_VALUE_ADD varchar2(1000 char),
    AUX_ORDER number(19,0),
    AUX_VALUE varchar2(1000 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (DOCUMENT_ID, AUX_NAME)
);


create table BL_DOCUMENT_RESP (
    ATTR_NAME varchar2(50 char) not null,
    REPLICATE number(1,0),
    ATTR_VALUE varchar2(1000 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (ATTR_NAME, DOCUMENT_ID)
);


create table BL_ITEM (
    ITEM_ID number(19,0) not null,
    DOCUMENT_ID number(19,0) not null,
    primary key (ITEM_ID, DOCUMENT_ID)
);


create table BL_ITEM_ATTR (
    ATTR_NAME varchar2(50 char) not null,
    ATTR_VALUE varchar2(1000 char),
    ITEM_ID number(19,0) not null,
    DOCUMENT_ID number(19,0) not null,
    primary key (ATTR_NAME, ITEM_ID, DOCUMENT_ID)
);


create table BL_ITEM_AUX (
    AUX_NAME varchar2(255 char) not null,
    AUX_LENGTH varchar2(255 char),
    AUX_ORDER number(19,0),
    AUX_VALUE varchar2(255 char),
    ITEM_ID number(19,0) not null,
    DOCUMENT_ID number(19,0) not null,
    primary key (ITEM_ID, DOCUMENT_ID, AUX_NAME)
);


create table BL_MESSAGE_TRACE (
    id number(19,0) not null,
    MESSAGE_TIME timestamp,
    MESSAGE_VALUE clob,
    MESSAGE_TYPE varchar2(255 char),
    DOCUMENT_ID number(19,0),
    primary key (id)
);


create table CF_KEYSTORE (
    ID varchar2(40 char) not null,
    KEYSTORE_DATA blob,
    PROTECTION varchar2(255 char),
    primary key (ID)
);


create table CF_SECURITYVALUES (
    userId varchar2(40 char) not null,
    ALIAS varchar2(100 char),
    PROTECTION varchar2(100 char),
    KEYSTORE_ID varchar2(40 char),
    primary key (userId)
);


alter table BL_DOCUMENT_ATTR 
    add constraint FK_eb6ax0w3lj5wycrj36n76wgwv 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_AUX 
    add constraint FK_mj4fyxv2uhid08wjipamx1sdl 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_DATA 
    add constraint FK_hfwic86hen7o9gqyul29frbwc 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_LEG 
    add constraint FK_dxi66uk6am81rqdsduf3e4dlh 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_RESP 
    add constraint FK_1rtmyldo0uetb1oobrayw1of 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_ITEM 
    add constraint FK_qr8qndjcxbhb9gli1scjb9vl0 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_ITEM_ATTR 
    add constraint FK_e5vxp0t6s1a5dhaq01u7c58b1 
    foreign key (ITEM_ID, DOCUMENT_ID) 
    references BL_ITEM;


alter table BL_ITEM_AUX 
    add constraint FK_o71qg0muewnxmwjk8qseby1vf 
    foreign key (ITEM_ID, DOCUMENT_ID) 
    references BL_ITEM;


alter table BL_MESSAGE_TRACE 
    add constraint FK_nio06s6yl1srl63qduu5gyqj1 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table CF_SECURITYVALUES 
    add constraint FK_gnk0ry7g64wy2ienbcgqkwsnl 
    foreign key (KEYSTORE_ID) 
    references CF_KEYSTORE;


create sequence BL_DOCUMENT_SEQ start with 1 increment by 50;


create sequence BL_MESSAGE_TRACE_SEQ start with 1 increment by 50;

