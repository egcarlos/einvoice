
create table BL_DOCUMENT (
    DOCUMENT_ID number(19,0) not null,
    CLIENT_ID varchar2(20 char),
    DOCUMENT_DATE varchar2(40 char),
    DOCUMENT_NUMBER varchar2(20 char),
    DOCUMENT_TYPE varchar2(10 char),
    HASH varchar2(1000 char),
    PDF_URL varchar2(1000 char),
    SIGNATURE varchar2(2000 char),
    DOCUMENT_STATUS varchar2(255 char),
    DOCUMENT_STEP varchar2(255 char),
    XML_URL varchar2(1000 char),
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


create table BL_DOCUMENT_LEG (
    AUX_NAME varchar2(50 char) not null,
    AUX_VALUE_ADD varchar2(1000 char),
    AUX_ORDER number(19,0),
    AUX_VALUE varchar2(1000 char),
    DOCUMENT_ID number(19,0) not null,
    primary key (DOCUMENT_ID, AUX_NAME)
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


alter table BL_DOCUMENT_ATTR 
    add constraint FK_eb6ax0w3lj5wycrj36n76wgwv 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_AUX 
    add constraint FK_mj4fyxv2uhid08wjipamx1sdl 
    foreign key (DOCUMENT_ID) 
    references BL_DOCUMENT;


alter table BL_DOCUMENT_LEG 
    add constraint FK_dxi66uk6am81rqdsduf3e4dlh 
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


create sequence BL_DOCUMENT_SEQ start with 1 increment by 50;


create sequence BL_MESSAGE_TRACE_SEQ start with 1 increment by 50;