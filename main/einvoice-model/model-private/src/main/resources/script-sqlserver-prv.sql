create table BL_DOCUMENT (
    DOCUMENT_ID numeric(19,0) not null,
    CLIENT_ID varchar(20),
    DOCUMENT_DATE varchar(40),
    DOCUMENT_NUMBER varchar(20),
    DOCUMENT_TYPE varchar(10),
    DOCUMENT_HASH varchar(1000),
    SIGNATURE varchar(2000),
    DOCUMENT_STATUS varchar(255),
    DOCUMENT_STEP varchar(255),
    primary key (DOCUMENT_ID)
);

create table BL_DOCUMENT_ATTR (
    ATTR_NAME varchar(50) not null,
    ATTR_VALUE varchar(1000),
    DOCUMENT_ID numeric(19,0) not null,
    primary key (ATTR_NAME, DOCUMENT_ID)
);

create table BL_DOCUMENT_AUX (
    AUX_NAME varchar(50) not null,
    AUX_LENGTH varchar(50),
    AUX_ORDER numeric(19,0),
    AUX_VALUE varchar(1000),
    DOCUMENT_ID numeric(19,0) not null,
    primary key (DOCUMENT_ID, AUX_NAME)
);

create table BL_DOCUMENT_DATA (
    DATA_NAME varchar(50) not null,
    RAW_DATA image,
    REPLICATE bit,
    SOURCE varchar(1000),
    STATUS varchar(20),
    DOCUMENT_ID numeric(19,0) not null,
    primary key (DATA_NAME, DOCUMENT_ID)
);

create table BL_DOCUMENT_LEG (
    AUX_NAME varchar(50) not null,
    AUX_VALUE_ADD varchar(1000),
    AUX_ORDER numeric(19,0),
    AUX_VALUE varchar(1000),
    DOCUMENT_ID numeric(19,0) not null,
    primary key (DOCUMENT_ID, AUX_NAME)
);

create table BL_DOCUMENT_RESP (
    ATTR_NAME varchar(50) not null,
    REPLICATE bit,
    ATTR_VALUE varchar(1000),
    DOCUMENT_ID numeric(19,0) not null,
    primary key (ATTR_NAME, DOCUMENT_ID)
);

create table BL_ITEM (
    ITEM_ID numeric(19,0) not null,
    DOCUMENT_ID numeric(19,0) not null,
    primary key (ITEM_ID, DOCUMENT_ID)
);

create table BL_ITEM_ATTR (
    ATTR_NAME varchar(50) not null,
    ATTR_VALUE varchar(1000),
    ITEM_ID numeric(19,0) not null,
    DOCUMENT_ID numeric(19,0) not null,
    primary key (ATTR_NAME, ITEM_ID, DOCUMENT_ID)
);

create table BL_ITEM_AUX (
    AUX_NAME varchar(255) not null,
    AUX_LENGTH varchar(255),
    AUX_ORDER numeric(19,0),
    AUX_VALUE varchar(255),
    ITEM_ID numeric(19,0) not null,
    DOCUMENT_ID numeric(19,0) not null,
    primary key (ITEM_ID, DOCUMENT_ID, AUX_NAME)
);

create table BL_MESSAGE_TRACE (
    id numeric(19,0) not null,
    MESSAGE_TIME datetime,
    MESSAGE_VALUE text,
    MESSAGE_TYPE varchar(255),
    DOCUMENT_ID numeric(19,0),
    primary key (id)
);

create table CF_KEYSTORE (
    ID varchar(40) not null,
    KEYSTORE_DATA image,
    PROTECTION varchar(255),
    primary key (ID)
);

create table CF_SECURITYVALUES (
    userId varchar(40) not null,
    ALIAS varchar(100),
    PROTECTION varchar(100),
    KEYSTORE_ID varchar(40),
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
    references BL_ITEM

alter table BL_MESSAGE_TRACE 
    add constraint FK_nio06s6yl1srl63qduu5gyqj1 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;

alter table CF_SECURITYVALUES 
    add constraint FK_gnk0ry7g64wy2ienbcgqkwsnl 
    foreign key (KEYSTORE_ID) 
    references CF_KEYSTORE;

create table BL_DOCUMENT_SEQ (
     next_val numeric(19,0) 
);

insert into BL_DOCUMENT_SEQ values ( 1 );

create table BL_MESSAGE_TRACE_SEQ (
     next_val numeric(19,0) 
);

insert into BL_MESSAGE_TRACE_SEQ values ( 1 );
