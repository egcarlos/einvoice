-- 
-- PROCEDIMIENTO ALMACENADO PARA EMITIR UN RESUMEN DE COMPROBANTES.
-- 
-- ESTE PROCEDIMIENTO NO ES MULTIEMPRESA. SOLO APLICA PARA IMPLEMENTACIONES DEL
-- TIPO UNA INSTALACION A UN RUC.
-- 
create or replace PROCEDURE SP_GENERATESUMMARY (
    P_FECHAEMISION VARCHAR2
) as
    v_fechaemision           varchar2(1000);
    v_resumenId              varchar2(1000);
    v_flag                   int;
    v_tipoDocumentoEmisor    varchar2(1000);
    v_numeroDocumentoEmisor  varchar2(1000);
    v_razonSocialEmisor      varchar2(1000);
    v_fechaGeneracionResumen varchar2(1000);
    v_correoEmisor           varchar2(1000);
    v_resumenTipo            varchar2(1000);
    v_inhabilitado           varchar2(1000);
    v_estadoregistro         varchar2(1000);
BEGIN
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY BEGIN');
    
    v_fechaemision := NVL(P_FECHAEMISION,TO_CHAR(SYSDATE-1,'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY FECHAEMISION           ' || v_fechaemision);
    
    select 'RC-' || TO_CHAR(SYSDATE,'YYYYMMDD') || '-' || TRIM(TO_CHAR(count (*)+1, '000')) into v_resumenId from SPE_SUMMARYHEADER where FECHAGENERACIONRESUMEN = TO_CHAR(SYSDATE,'YYYY-MM-DD');
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY RESUMENID              ' || v_resumenId);
    
    select TO_CHAR(SYSDATE,'YYYY-MM-DD') into v_fechaGeneracionResumen from dual;
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY FECHAGENERACIONRESUMEN ' || v_fechaGeneracionResumen);
    
    select 'RC' into v_resumenTipo from dual;
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY RESUMENTIPO            ' || v_resumenTipo);
    
    select '1' into v_inhabilitado from dual;
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY INHABILITADO           ' || v_inhabilitado);
    
    select 'N' into v_estadoregistro from dual;
    DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY ESTADOREGISTRO         ' || v_estadoregistro);
    
    SELECT COUNT(NUMERODOCUMENTOEMISOR) INTO v_flag FROM SPE_EINVOICEHEADER WHERE SERIENUMERO like 'B%' AND FECHAEMISION = V_FECHAEMISION AND rownum <= 1;
    if v_flag = 1  then
        select TIPODOCUMENTOEMISOR,   NUMERODOCUMENTOEMISOR,   RAZONSOCIALEMISOR,   CORREOEMISOR
        into   v_tipoDocumentoEmisor, v_numeroDocumentoEmisor, v_razonSocialEmisor, v_correoEmisor
        from   SPE_EINVOICEHEADER
        where  SERIENUMERO like 'B%' AND FECHAEMISION = V_FECHAEMISION and rownum <= 1;
        
        DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY TIPODOCUMENTOEMISOR    ' || v_tipoDocumentoEmisor);
        DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY NUMERODOCUMENTOEMISOR  ' || v_numeroDocumentoEmisor);
        DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY RAZONSOCIALEMISOR      ' || v_razonSocialEmisor);
        DBMS_OUTPUT.PUT_LINE('SP_GENERATESUMMARY CORREOEMISOR           ' || v_correoEmisor);
        
        --INSERTANDO LA CABECERA
        insert into SPE_SUMMARYHEADER(
            -- llave primaria TIPODOCUMENTOEMISOR, NUMERODOCUMENTOEMISOR, RESUMENID
            TIPODOCUMENTOEMISOR,
            NUMERODOCUMENTOEMISOR,
            -- El resumenId se debe armar con el siguiente formato
            -- RC-YYYYMMDD-NNNNN
            -- YYYYMMDD: es el campo FECHAGENERACIONRESUMEN sin guiones
            -- NNNNN: (hasta cinco de tope) la secuencia en el día
            RESUMENID,
            -- es la fecha de liquidacion, coincide con el capo fechaEmision de la tabla SPE_EINVOICEHEADER
            FECHAEMISIONCOMPROBANTE,
            --se usa sysdate para la fecha de generación
            FECHAGENERACIONRESUMEN,
            RAZONSOCIALEMISOR,
            CORREOEMISOR,
            RESUMENTIPO,
            INHABILITADO,
            BL_ESTADOREGISTRO
        ) values (
            v_tipoDocumentoEmisor,
            v_numeroDocumentoEmisor,
            v_resumenId,
            v_fechaemision,
            v_fechaGeneracionResumen,
            v_razonSocialEmisor,
            v_correoEmisor,
            v_resumenTipo,
            v_inhabilitado,
            v_estadoregistro
        );
        
        COMMIT;
        
        INSERT INTO SPE_SUMMARYDETAIL (
            TIPODOCUMENTOEMISOR, NUMERODOCUMENTOEMISOR, RESUMENID, NUMEROFILA, TIPODOCUMENTO, TIPOMONEDA, SERIEGRUPODOCUMENTO, NUMEROCORRELATIVOINICIO, NUMEROCORRELATIVOFIN,
            TOTALVALORVENTAOPGRAVADACONIGV,
            TOTALVALORVENTAOPINAFECTASIGV,
            TOTALVALORVENTAOPEXONERADASIGV,
            TOTALVALORVENTAOPGRATUITAS,
            TOTALOTROSTRIBUTOS,
            TOTALOTROSCARGOS,
            TOTALVENTA,
            TOTALISC,
            TOTALIGV
        )
        SELECT 
            h.TIPODOCUMENTOEMISOR, h.NUMERODOCUMENTOEMISOR, v_resumenId, ROWNUM, h.TIPODOCUMENTO, h.TIPOMONEDA, SUBSTR(h.SERIENUMERO,1,4), SUBSTR(h.SERIENUMERO,6,13), SUBSTR(h.SERIENUMERO,6,13),
            h.TOTALVALORVENTANETOOPGRAVADAS,
            h.TOTALVALORVENTANETOOPNOGRAVADA,
            h.TOTALVALORVENTANETOOPEXONERADA,
            h.TOTALVALORVENTANETOOPGRATUITAS,
            NVL(h.TOTALOTROSTRIBUTOS, '0.00'),
            NVL(h.TOTALOTROSCARGOS, '0.00'),
            h.TOTALVENTA,
            NVL(h.TOTALISC, '0.00'),
            h.TOTALIGV            
        FROM
            SPE_EINVOICEHEADER h
        WHERE
            h.TIPODOCUMENTOEMISOR = v_tipoDocumentoEmisor
            AND h.NUMERODOCUMENTOEMISOR = v_numeroDocumentoEmisor
            AND SERIENUMERO like 'B%'
            AND FECHAEMISION = V_FECHAEMISION;
        
        COMMIT;
        
        UPDATE
            SPE_SUMMARYHEADER h SET h.BL_ESTADOREGISTRO = 'A'
        WHERE
            h.BL_ESTADOREGISTRO = 'N'
            AND h.TIPODOCUMENTOEMISOR = v_tipoDocumentoEmisor
            AND h.NUMERODOCUMENTOEMISOR = v_numeroDocumentoEmisor
            AND h.RESUMENID = v_resumenId;
        COMMIT;
    end if;
END;
