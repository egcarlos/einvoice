-- 
-- LABTECH (info@labtech.pe)
--
-- 13 febrero 2015, 13:08
-- 
-- TABLA: SPE_EINVOICEDETAIL
-- 
-- Obs. Script regenerado a partir del insert y las columnas
-- 

create table SPE_EINVOICEDETAIL (

    CEMPRESA varchar2(255 char) not null,
    CORDEN number(19,0) not null,
    DID varchar2(255 char) not null,
    DCODPRODUCTO varchar2(255 char) not null,
    DDESPRODUCTO varchar2(1700 char),
    DCANPRODUCTO number(19,2),
    DUNIPRODUCTO varchar2(3 char),
    DVVENTAUNITARIO number(19,2),
    DPVENTAUNITARIO number(19,2),
    DCODPVENTAUNITARIO varchar2(2 char),
    DIREFERENCIAUNITARIO number(19,2),
    DCODIREFERENCIAUNITARIO varchar2(2 char),
    DVVENTA number(19,2),
    DCARGO number(19,2),
    DDESCUENTO number(19,2),
    DTIPIGV varchar2(2 char),
    DIGV number(19,2),
    DTIPISC varchar2(2 char),
    DISC number(19,2),
    DAUX1 varchar2(100 char),
    DAUX2 varchar2(100 char),
    DAUX3 varchar2(100 char),
    DAUX4 varchar2(100 char),
    DAUX5 varchar2(100 char),
    DAUX6 varchar2(100 char),
    DAUX7 varchar2(100 char),
    DAUX8 varchar2(100 char),
    DAUX9 varchar2(100 char),
    DAUX10 varchar2(100 char),
    DAUX11 varchar2(100 char),
    DAUX12 varchar2(100 char),
    DAUX13 varchar2(100 char),
    DAUX14 varchar2(100 char),

    primary key (CEMPRESA, CORDEN, DCODPRODUCTO, DID)
)