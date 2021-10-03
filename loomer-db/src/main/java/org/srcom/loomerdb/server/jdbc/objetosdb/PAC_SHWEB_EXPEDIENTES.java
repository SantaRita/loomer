package org.srcom.loomerdb.server.jdbc.objetosdb; //WLS-Ready

import oracle.jdbc.driver.OracleConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.srcom.loomerdb.server.jdbc.util.ConversionUtil;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PAC_SHWEB_EXPEDIENTES extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_EXPEDIENTES.class);


    private Connection conn = null;



    public PAC_SHWEB_EXPEDIENTES(Connection con) throws SQLException {

        this.conn = con;
    }


    // CONTRATOS POLIZAS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_CONTRATOS(String pFILTROS, java.math.BigDecimal pVERBAJA, String pUSUARIO ) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_CONTRATOS(pFILTROS, pVERBAJA, pUSUARIO );
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_CONTRATOS( String pFILTROS, java.math.BigDecimal pVERBAJA, String pUSUARIO ) throws Exception {
        System.out.println("Entrat f_get_contratos filtros:"+pFILTROS+ "VerBaja:"+pVERBAJA);
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_CONTRATOS(?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pFILTROS);
        cStmt.setObject (3, pVERBAJA);
        cStmt.setObject (5, pUSUARIO);
        cStmt.registerOutParameter(4, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(4));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }

        ////System.out.println("1_Salida contratos:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida contratos:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }

    // EXPEDIENTES
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_EXPEDIENTES( String pCONTRATO1, java.math.BigDecimal pCONTRATO2, String pPOLIZA) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_EXPEDIENTES( pCONTRATO1, pCONTRATO2, pPOLIZA);
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_EXPEDIENTES( String pCONTRATO1, java.math.BigDecimal pCONTRATO2, String pPOLIZA) throws Exception {
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_EXPEDIENTES( ?,?,?,? )}";
        //System.out.println("Entrada expediente");
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRATO1);
        cStmt.setObject (3, pCONTRATO2);
        cStmt.setObject (4, pPOLIZA);
        cStmt.registerOutParameter(5, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(5));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }
        //System.out.println("2_Salida_exp:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida_exp:"+retVal);
        cStmt.close();
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }


    // GET_TITULAR
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_TITULAR( String pCDCONTRA, java.math.BigDecimal pRGSUMPLEM, String pCONCLI) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_TITULAR( pCDCONTRA, pRGSUMPLEM, pCONCLI);
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_TITULAR( String pCDCONTRA, java.math.BigDecimal pRGSUMPLEM, String pCONCLI) throws Exception {
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_TITULAR( ?,?,?,? )}";
        //System.out.println("Entrada get_titular");
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCDCONTRA);
        cStmt.setObject (3, pRGSUMPLEM);
        cStmt.setObject (4, pCONCLI);
        cStmt.registerOutParameter(5, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(5));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }
        //System.out.println("2_Salida_gettitualr:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida_gettitular:"+retVal);
        cStmt.close();
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }

    // GET_TITULAR
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_CAPITALES( String pTIPOALTA, String pFILTRO, java.math.BigDecimal pCAUSA, String pGARANTIA, java.math.BigDecimal pCDASISTE) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_CAPITALES( pTIPOALTA, pFILTRO, pCAUSA, pGARANTIA, pCDASISTE);
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_CAPITALES( String pTIPOALTA, String pFILTRO, java.math.BigDecimal pCAUSA, String pGARANTIA, java.math.BigDecimal pCDASISTE) throws Exception {
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_CAPITALES( ?,?,?,?,?,?)}";
        //System.out.println("Entrada get_capitales:"+pFILTRO);
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pTIPOALTA);
        cStmt.setObject (3, pFILTRO);
        if (pCAUSA.intValue()==-1) {
            cStmt.setObject (4, null);
        }
        else {
            cStmt.setObject (4, pCAUSA);
        }
        if (pGARANTIA.equals("-1")) {
            cStmt.setObject (5, null);
        }
        else {
            cStmt.setObject (5, pGARANTIA);
        }

        if (pCDASISTE.equals("-1")) {
            cStmt.setObject (6, null);
        }
        else {
            cStmt.setObject (6, pCDASISTE);
        }

        cStmt.registerOutParameter(7, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(4));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }
        //System.out.println("2_Salida_getcapitales:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida_capitales:"+retVal);
        cStmt.close();
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }


    // GUARDAR_EXPEDIENTE
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_EXPEDIENTE( ArrayList pDATOS_ASISTE,
                                                                       ArrayList pDATOS_EXPED,
                                                                       ArrayList pDATOS_CONTACTOS, String pFINAL,
                                                                       java.math.BigDecimal pTRAMIT,
                                                                       String pAVISO) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_EXPEDIENTE( pDATOS_ASISTE, pDATOS_EXPED, pDATOS_CONTACTOS, pFINAL, pTRAMIT, pAVISO );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_EXPEDIENTE( ArrayList pDATOS_ASISTE,
                                                                     ArrayList pDATOS_EXPED,
                                                                     ArrayList pDATOS_CONTACTOS,
                                                                     String pFINAL,
                                                                     java.math.BigDecimal pTRAMIT,
                                                                     String pAVISO )  throws Exception {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_EXPEDIENTE(?,?,?,?,?,?,?)}";


        try {
            if (conn.isWrapperFor(OracleConnection.class)){

                OracleConnection oracleConnection= conn.unwrap(OracleConnection.class);
            }else{

            }

            CallableStatement cStmt= conn.prepareCall(callQuery);



            String[] listaAsiste = (String[]) pDATOS_ASISTE.toArray(new String[pDATOS_ASISTE.size()]);
            String[] listaExped = (String[]) pDATOS_EXPED.toArray(new String[pDATOS_EXPED.size()]);
            String[] listaContactos = (String[]) pDATOS_CONTACTOS.toArray(new String[pDATOS_CONTACTOS.size()]);




            //ArrayList listaAsiste = new ArrayList();
            //listaAsiste.add("AAA1");
            //listaAsiste.add("BBB2");

            //Object[] listaAsiste = new Object[] {new String("Usuario"), new String("Password") };

            Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaAsiste);
            Array b = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaExped);
            Array c = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaContactos);

            cStmt.setArray(2, a);
            cStmt.setArray(3, b);
            cStmt.setArray(4, c);
            cStmt.setObject(5, pFINAL);
            cStmt.setObject(6, pTRAMIT);
            cStmt.registerOutParameter(7, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
            if ( pAVISO.equals("-1")) {
                cStmt.setObject(8, null);
                System.out.println(" El aviso ENVAIMOS -1 ");
            }
            else {
                System.out.println(" El aviso a grabar " + pAVISO);
                cStmt.setObject(8, pAVISO);
            }


            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

            System.out.println("Vamos a guardar el expediente");
            cStmt.execute();
            System.out.println("Despues de guardar el expediente");
            HashMap retVal=new HashMap();

            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Campos Expediente:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("ERROR guardar expediente :"+e);

            e.printStackTrace();
            return null;
        }

    }

    // GUARDAR CAPITALES
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CAPITALES( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, ArrayList pCAPITALES ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CAPITALES( pCDASISTE, pNUORDEN, pCAPITALES );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CAPITALES( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, ArrayList pCAPITALES ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_CAPITALES(?,?,?,?)}";


        CallableStatement cStmt;

        String[] listaCapitales = (String[]) pCAPITALES.toArray(new String[pCAPITALES.size()]);

        Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaCapitales);

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setArray(4, a);
            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "RETURN"
            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Guardar capitales:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }




    }

    // GUARDAR STAND BY  F_GUARDAR_STANDBY
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_STANDBY( String pTIPO, java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, String pFINICIO, String pFFIN
            ,java.math.BigDecimal pTIPOEXP, java.math.BigDecimal pTIPOSTANDBY) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_STANDBY( pTIPO, pCDASISTE, pNUORDEN, pFINICIO, pFFIN, pTIPOEXP, pTIPOSTANDBY );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_STANDBY( String pTIPO, java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, String pFINICIO, String pFFIN
            ,java.math.BigDecimal pTIPOEXP, java.math.BigDecimal pTIPOSTANDBY) throws Exception  {

        //System.out.println("Entramos a guardar_standby");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_STANDBY(?,?,?,?,?,?,?,?)}";


        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pTIPO);
            cStmt.setObject (3, pCDASISTE);
            cStmt.setObject (4, pNUORDEN);
            cStmt.setObject (5, pFINICIO);
            cStmt.setObject (6, pFFIN);
            cStmt.setObject (7, pTIPOEXP);
            cStmt.setObject (8, pTIPOSTANDBY);
            cStmt.registerOutParameter(9, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "RETURN"
            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Campos Standby:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }




    }


    // GUARDAR CONTACTOS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CONTACTOS( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, ArrayList pCONTACTOS ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CONTACTOS( pCDASISTE, pNUORDEN, pCONTACTOS );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_CONTACTOS( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, ArrayList pCONTACTOS ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_CONTACTOS(?,?,?)}";


        CallableStatement cStmt;

        String[] listaCapitales = (String[]) pCONTACTOS.toArray(new String[pCONTACTOS.size()]);

        Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaCapitales);

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setArray(4, a);
            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Guardar CONTACTOS:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }




    }

    // GUARDAR DOCUMENTOS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_DOC(java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, String pCDPROVE,
                                                               String pTIPODOC, String pOBS, java.math.BigDecimal pGEDORIGEN,
                                                               java.math.BigDecimal pGEDTIPO, java.math.BigDecimal pGEDSUBTIPO, String pFICHERO ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_DOC( pCDASISTE, pNUORDEN, pCDPROVE, pTIPODOC,
                pOBS, pGEDORIGEN, pGEDTIPO, pGEDSUBTIPO, pFICHERO );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_DOC( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pNUORDEN, String pCDPROVE,
                                                              String pTIPODOC, String pOBS, java.math.BigDecimal pGEDORIGEN,
                                                              java.math.BigDecimal pGEDTIPO, java.math.BigDecimal pGEDSUBTIPO, String pFICHERO ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_DOC(?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setObject (4, null);   // De momento siempre pasamos un null en PCDPROVEE
            cStmt.setObject (5, "D"); // Siempre va una D en pTIPODOC);
            cStmt.setObject (6, pOBS);  // longitud maxima 50
            cStmt.setObject (7, pGEDORIGEN);
            cStmt.setObject (8, pGEDTIPO);
            if ( pGEDSUBTIPO== BigDecimal.valueOf(-1)) {
                cStmt.setObject (9, null);
            }
            else {
                cStmt.setObject (9, pGEDSUBTIPO);
            }

            cStmt.setObject (10, pFICHERO);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Guardar documentos:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // BORRAR_DOC
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC(java.math.BigDecimal pCDCARGA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC( pCDCARGA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC( java.math.BigDecimal pCDCARGA ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_BORRAR_DOC(?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCARGA);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Borrar documentos:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // PROVEEDORES OFRECIDOS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_PROVEEDORES_OFRECIDOS(String pCDCONTRA
            ,java.math.BigDecimal pRGSUPLEM, String pCDPAIS,
                                                                         java.math.BigDecimal pTPZONA, String pCDZONA,  java.math.BigDecimal pCDSERVIC,
                                                                         java.math.BigDecimal pCDASISTE, String pURGENTE,
                                                                         String pFEINICITA, String pFEFINCITA) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_PROVEEDORES_OFRECIDOS( pCDCONTRA, pRGSUPLEM, pCDPAIS, pTPZONA,
                pCDZONA, pCDSERVIC, pCDASISTE, pURGENTE,  pFEINICITA,  pFEFINCITA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_PROVEEDORES_OFRECIDOS(String pCDCONTRA
            ,java.math.BigDecimal pRGSUPLEM, String pCDPAIS,
                                                                       java.math.BigDecimal pTPZONA, String pCDZONA,  java.math.BigDecimal pCDSERVIC,
                                                                       java.math.BigDecimal pCDASISTE, String pURGENTE,
                                                                       String pFEINICITA, String pFEFINCITA) throws Exception  {


        //System.out.println("Entramos a proveedores ofrecidos");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_PROVEEDORES_OFRECIDOS(?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM);
            cStmt.setObject (4, pCDPAIS);
            cStmt.setObject (5, pTPZONA);
            cStmt.setObject (6, pCDZONA);  // longitud maxima 50
            cStmt.setObject (7, pCDSERVIC);
            cStmt.setObject (8, pCDASISTE);
            cStmt.setObject (9, pURGENTE);
            System.out.println("Dentro 1");
            if ( !pFEINICITA.equals("NULO") ) {

                System.out.println("xDentro 2- " + pFEINICITA);
                cStmt.setObject (10, pFEINICITA);
                cStmt.setObject (11, pFEFINCITA);

            }
            else {
                cStmt.setObject (10, null);
                cStmt.setObject (11, null);
            }


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida Guardar Prov.Ofrecidos:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // PENDIENTE ASIGNAR
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_PENDIENTE_ASIGNAR(java.math.BigDecimal pCDASISTE,
                                                                     java.math.BigDecimal pNUORDEN, String pGARANT, String pCOBERT, String pCDSERVIC,
                                                                     java.math.BigDecimal pCDPRESTA, java.math.BigDecimal pRGCLIENT, java.math.BigDecimal pIMPORTE 	) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_PENDIENTE_ASIGNAR( pCDASISTE, pNUORDEN, pGARANT, pCOBERT,
                pCDSERVIC, pCDPRESTA, pRGCLIENT, pIMPORTE );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_PENDIENTE_ASIGNAR(java.math.BigDecimal pCDASISTE,
                                                                   java.math.BigDecimal pNUORDEN, String pGARANT, String pCOBERT, String pCDSERVIC,
                                                                   java.math.BigDecimal pCDPRESTA, java.math.BigDecimal pRGCLIENT, java.math.BigDecimal pIMPORTE) throws Exception  {


        //System.out.println("Entramos a PENDIENTE ASIGNAR");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_PENDIENTE_ASIGNAR(?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setObject (4, pGARANT);
            cStmt.setObject (5, pCOBERT);
            cStmt.setObject (6, pCDSERVIC);
            cStmt.setObject (7, pCDPRESTA);
            cStmt.setObject (8, pRGCLIENT);
            cStmt.setObject (9, pIMPORTE);


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida PENDIENTE ASIGNAR:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_PREASIGNAR_EXPED
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_PREASIGNAR_EXPED(java.math.BigDecimal pCDASISTE, java.math.BigDecimal pNUORDEN, String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPAIS, String pTPZONA, String pCDZONA,
                                                                    String pCDGARANT, String pCDCOBERT, String pCDSERVIC, java.math.BigDecimal pRGDPRESTA,
                                                                    java.math.BigDecimal pRGCLIENT, String pCDPROVEE,
                                                                    java.math.BigDecimal pRGTPRED, Double pIMPORTE,
                                                                    String pCITANOCONCER,
                                                                    String pFECHAINI,
                                                                    String pFECHAFIN,
                                                                    String pESASISTENCIA,
                                                                    String pMASPISTAS
    ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_PREASIGNAR_EXPED( pCDASISTE, pNUORDEN, pCDCONTRA, pRGSUPLEM, pCDPAIS,
                pTPZONA, pCDZONA, pCDGARANT, pCDCOBERT, pCDSERVIC, pRGDPRESTA, pRGCLIENT, pCDPROVEE, pRGTPRED, pIMPORTE,
                pCITANOCONCER,
                pFECHAINI,
                pFECHAFIN,
                pESASISTENCIA,
                pMASPISTAS);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_PREASIGNAR_EXPED(java.math.BigDecimal pCDASISTE, java.math.BigDecimal pNUORDEN,  String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPAIS, String pTPZONA, String pCDZONA,
                                                                  String pCDGARANT, String pCDCOBERT, String pCDSERVIC, java.math.BigDecimal pRGDPRESTA,
                                                                  java.math.BigDecimal pRGCLIENT, String pCDPROVEE,
                                                                  java.math.BigDecimal pRGTPRED, Double pIMPORTE,
                                                                  String pCITANOCONCER,
                                                                  String pFECHAINI,
                                                                  String pFECHAFIN,
                                                                  String pESASISTENCIA,
                                                                  String pMASPISTAS) throws Exception  {


        //System.out.println("Entramos a F_PREASIGNAR_EXPED");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_PREASIGNAR_EXPED(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setObject (4, pCDCONTRA);
            cStmt.setObject (5, pRGSUPLEM);
            cStmt.setObject (6, pCDPAIS);
            cStmt.setObject (7, pTPZONA);
            cStmt.setObject (8, pCDZONA);
            cStmt.setObject (9, pCDGARANT);
            cStmt.setObject (10, pCDCOBERT);
            cStmt.setObject (11, pCDSERVIC);
            cStmt.setObject (12, pRGDPRESTA);
            cStmt.setObject (13, pRGCLIENT);
            if ( pCDPROVEE.equals("-1") )  {
                cStmt.setObject (14, null);
            }  	else {
                cStmt.setObject (14, pCDPROVEE);
            }
            if ( pRGTPRED.intValue() == -1 )  {
                cStmt.setObject (15, pRGTPRED);
            } else {
                cStmt.setObject (15, pRGTPRED);
            }

            cStmt.setObject (16, pIMPORTE);

            System.out.println("La cita no concertada a base ded atos " + pCITANOCONCER);
            if ( pCITANOCONCER.equals("")) {
                System.out.println("Enviamos null");
                cStmt.setObject (17, null);
            }
            else {
                System.out.println("Enviamos lo que pone [" + pCITANOCONCER + "]");
                cStmt.setObject (17, pCITANOCONCER);
            }
            cStmt.setObject (18, pFECHAINI);
            cStmt.setObject (19, pFECHAFIN);
            cStmt.setObject (20, pESASISTENCIA);
            cStmt.setObject (21, pMASPISTAS);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida PREASIGNAR_EXPED:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_ASIGNAR_PROVEEDOR
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_ASIGNAR_PROVEEDOR(java.math.BigDecimal pCDASISTE,
                                                                     java.math.BigDecimal pNORDEN, String pCDCONTRA,
                                                                     java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGCLIEN, java.math.BigDecimal pCDSERVIC,
                                                                     String pCDGARANT, String pCDCOBERT, String pCDPROVEE, java.math.BigDecimal pRGPRESTA,
                                                                     java.math.BigDecimal pRED, String pCITA, String pINICITA, String pFINCITA,  Double pIMPORTE,
                                                                     String pRECHAZADO, java.math.BigDecimal pMOTIVORECHAZO, String pTEXTORECHAZO, String pMASPISTAS
    ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_ASIGNAR_PROVEEDOR( pCDASISTE, pNORDEN, pCDCONTRA, pRGSUPLEM,
                pRGCLIEN, pCDSERVIC, pCDGARANT, pCDCOBERT, pCDPROVEE, pRGPRESTA, pRED, pCITA, pINICITA, pFINCITA , pIMPORTE,
                pRECHAZADO, pMOTIVORECHAZO, pTEXTORECHAZO, pMASPISTAS);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_ASIGNAR_PROVEEDOR(java.math.BigDecimal pCDASISTE,
                                                                   java.math.BigDecimal pNORDEN, String pCDCONTRA,
                                                                   java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGCLIEN, java.math.BigDecimal pCDSERVIC,
                                                                   String pCDGARANT, String pCDCOBERT, String pCDPROVEE, java.math.BigDecimal pRGPRESTA,
                                                                   java.math.BigDecimal pRED, String pCITA, String pINICITA, String pFINCITA,  Double pIMPORTE,
                                                                   String pRECHAZADO, java.math.BigDecimal pMOTIVORECHAZO, String pTEXTORECHAZO,
                                                                   String pMASPISTAS) throws Exception  {


        //System.out.println("Entramos a F_ASIGNAR_PROVEEDOR");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_ASIGNAR_PROVEEDOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNORDEN);
            cStmt.setObject (4, pCDCONTRA);
            cStmt.setObject (5, pRGSUPLEM);
            cStmt.setObject (6, pRGCLIEN);
            cStmt.setObject (7, pCDSERVIC);
            cStmt.setObject (8, pCDGARANT);
            cStmt.setObject (9, pCDCOBERT);
            cStmt.setObject (10, pCDPROVEE);
            cStmt.setObject (11, pRGPRESTA);
            cStmt.setObject (12, pRED);
            cStmt.setObject (13, pCITA);
            cStmt.setObject (14, pINICITA);
            cStmt.setObject (15, pFINCITA);
            cStmt.setObject (16, pIMPORTE);

            if ( pRECHAZADO.equals("-1") )  {
                cStmt.setObject (17, null);
            }  	else {
                cStmt.setObject (17, pRECHAZADO);
            }

            if ( pMOTIVORECHAZO==new BigDecimal("-1") )  {
                cStmt.setObject (18, null);
            }  	else {
                cStmt.setObject (18, pMOTIVORECHAZO);
            }

            if ( pTEXTORECHAZO.equals("-1") )  {
                cStmt.setObject (19, null);
            }  	else {
                cStmt.setObject (19, pTEXTORECHAZO);
            }

            cStmt.setObject (20, pMASPISTAS ) ;

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_ASIGNAR_PROVEEDOR:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // GUARDAR_PRESTACION / SERVICIO
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESTACION(java.math.BigDecimal pCDASISTE,
                                                                      java.math.BigDecimal pNUORDEN, String pSERVICIO, ArrayList pDATOS, ArrayList pELECTRO 	) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESTACION( pCDASISTE, pNUORDEN, pSERVICIO, pDATOS, pELECTRO);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESTACION(java.math.BigDecimal pCDASISTE,
                                                                    java.math.BigDecimal pNUORDEN, String pSERVICIO, ArrayList pDATOS, ArrayList pELECTRO) throws Exception  {


        //System.out.println("Entramos a GUARDAR_PRESTACION");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_PRESTACION(?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            if (conn.isWrapperFor(OracleConnection.class)){

                OracleConnection oracleConnection= conn.unwrap(OracleConnection.class);
            }else{

            }
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setObject (4, pSERVICIO);

            String[] listaDatos = (String[]) pDATOS.toArray(new String[pDATOS.size()]);
            Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaDatos);
            cStmt.setArray(5, a);

            String[] listaElectro = (String[]) pELECTRO.toArray(new String[pELECTRO.size()]);
            Array b = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaElectro);
            cStmt.setArray(6, b);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida GUARDAR_PRESTACION:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_GET_PERSONA
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_PERSONA( String pNIF ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GET_PERSONA(pNIF );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_PERSONA( String pNIF ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_PERSONA(?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pNIF);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida f_get_persona:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_MOVIMIENTO_ECONOM
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_MOVIMIENTO_ECONOM( java.math.BigDecimal pCDASISTE,
                                                                      java.math.BigDecimal pNUORDEN, String pCDCONTRA, java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGCLIEN,
                                                                      String pCDGARANT, String pCDCOBERT, String pCDSERVIC,
                                                                      java.math.BigDecimal pRGPRESTA, Double pIMPORTE,
                                                                      String pCDTPMOVI, java.math.BigDecimal pRGPERSON ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_MOVIMIENTO_ECONOM( pCDASISTE, pNUORDEN, pCDCONTRA, pRGSUPLEM,
                pRGCLIEN, pCDGARANT, pCDCOBERT, pCDSERVIC, pRGPRESTA, pIMPORTE, pCDTPMOVI, pRGPERSON );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_MOVIMIENTO_ECONOM( java.math.BigDecimal pCDASISTE,
                                                                    java.math.BigDecimal pNUORDEN, String pCDCONTRA, java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGCLIEN,
                                                                    String pCDGARANT, String pCDCOBERT, String pCDSERVIC,
                                                                    java.math.BigDecimal pRGPRESTA, Double pIMPORTE,
                                                                    String pCDTPMOVI, java.math.BigDecimal pRGPERSON ) throws Exception  {

        System.out.println("********** mov econ ***************");
        System.out.println("********** mov econ ***************" + pIMPORTE);

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_MOVIMIENTO_ECONOM(?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNUORDEN);
            cStmt.setObject (4, pCDCONTRA);
            cStmt.setObject (5, pRGSUPLEM);
            cStmt.setObject (6, pRGCLIEN);
            cStmt.setObject (7, pCDGARANT);
            cStmt.setObject (8, pCDCOBERT);
            cStmt.setObject (9, pCDSERVIC);
            cStmt.setObject (10, pRGPRESTA);
            cStmt.setObject (11, pIMPORTE);
            cStmt.setObject (12, pCDTPMOVI);

            if ( pRGPERSON.intValue() == -1 )  {
                cStmt.setObject (13, null);
            } else {
                cStmt.setObject (13, pRGPERSON);
            }



            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_MOVIMIENTO_ECONOM:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_VALIDAR_FECHA_OCURRE
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_VALIDAR_FECHA_OCURRE( String pFHOCURRE,
                                                                         String pCDCONTRA, java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGTITULA,
                                                                         java.math.BigDecimal pRGCLIEN) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_VALIDAR_FECHA_OCURRE( pFHOCURRE, pCDCONTRA, pRGSUPLEM,
                pRGTITULA, pRGCLIEN);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_VALIDAR_FECHA_OCURRE( String pFHOCURRE,
                                                                       String pCDCONTRA, java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pRGTITULA,
                                                                       java.math.BigDecimal pRGCLIEN ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_VALIDAR_FECHA_OCURRE(?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pFHOCURRE);
            cStmt.setObject (3, pCDCONTRA);
            cStmt.setObject (4, pRGSUPLEM);
            if ( pRGTITULA.intValue() == -1 )  {
                cStmt.setObject (5, pRGTITULA);
            } else {
                cStmt.setObject (5, pRGTITULA);
            }
            cStmt.setObject (6, pRGCLIEN);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_fhocurrencia:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    // F_VALIDAR_SINIESTRO
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_VALIDAR_SINIESTRO( java.math.BigDecimal pRGCLIEN, String pVALORES ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_VALIDAR_SINIESTRO( pRGCLIEN, pVALORES);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_VALIDAR_SINIESTRO(java.math.BigDecimal pRGCLIEN, String pVALORES ) throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_VALIDAR_SINIESTRO(?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pRGCLIEN);
            cStmt.setObject (3, pVALORES);


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_VALIDAR_SINIESTRO:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    // GUARDAR_TITULAR

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_TITULAR( String pVALORES ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_TITULAR( pVALORES);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_TITULAR(String pVALORES )  throws Exception  {

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_TITULAR(?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pVALORES);


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_guardar_titular:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_ACCION_SINIESTRO_DUP

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_ACCION_SINIESTRO_DUP( java.math.BigDecimal pCDASISTE, java.math.BigDecimal pNORDEN ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_ACCION_SINIESTRO_DUP( pCDASISTE, pNORDEN);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_ACCION_SINIESTRO_DUP( java.math.BigDecimal pCDASISTE, java.math.BigDecimal pNORDEN )  throws Exception  {

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_ACCION_SINIESTRO_DUP(?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNORDEN );


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_ACCION_SINIESTRO_DUP:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_RECHAZAR_EXPEDIENTE

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_RECHAZAR_EXPEDIENTE( java.math.BigDecimal pCDASISTE
            , java.math.BigDecimal pNORDEN, String pCDCONTRA,
                                                                        java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pSUBCAUSA,
                                                                        java.math.BigDecimal pRGTRAMIT) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_RECHAZAR_EXPEDIENTE( pCDASISTE, pNORDEN, pCDCONTRA, pRGSUPLEM, pSUBCAUSA, pRGTRAMIT  );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_RECHAZAR_EXPEDIENTE( java.math.BigDecimal pCDASISTE
            , java.math.BigDecimal pNORDEN, String pCDCONTRA,
                                                                      java.math.BigDecimal pRGSUPLEM, java.math.BigDecimal pSUBCAUSA,
                                                                      java.math.BigDecimal pRGTRAMIT)  throws Exception  {

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_RECHAZAR_EXPEDIENTE(?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pNORDEN );
            cStmt.setObject (4, pCDCONTRA);
            cStmt.setObject (5, pRGSUPLEM );
            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER); // Valor de OUT
            cStmt.setObject (6, pSUBCAUSA );
            cStmt.setObject (7, pRGTRAMIT );


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_RECHAZAR_EXPEDIENTE:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_TIEMPOS_TRAMITACION

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_TIEMPOS_TRAMITACION() throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_TIEMPOS_TRAMITACION();
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_TIEMPOS_TRAMITACION( )  throws Exception  {

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_TIEMPOS_TRAMITACION(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, null);
            cStmt.setObject (3, null );
            cStmt.setObject (4, null );
            cStmt.setObject (5, "A");


            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida F_TIEMPOS_TRAMITACION:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    /*
     * ****************************************** G A R A N T I A S *****************************************************
     */

    // F_INFO_PROD_LIBERTY

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_LIBERTY( String pCDCONTRA,
                                                                      java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_LIBERTY( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_LIBERTY( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_LIBERTY(?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

            System.out.println("CDCONTRA: " + pCDCONTRA);
            System.out.println("RGSUPLEM: " + pRGSUPLEM);
            System.out.println("CDPOLIZA: " + pCDPOLIZA);

            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"
            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_EPIGRAFES"
            cStmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_LIMITES"
            cStmt.registerOutParameter(8, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_ATRIBUTOS"
            cStmt.registerOutParameter(9, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_CLAUSULAS"

            cStmt.execute();


            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }
            try { retVal.put("EPIGRAFES", cStmt.getObject(6)); } catch (SQLException e) { retVal.put("EPIGRAFES", null); }
            try { retVal.put("LIMITES", cStmt.getObject(7)); } catch (SQLException e) { retVal.put("LIMITES", null); }
            try { retVal.put("ATRIBUTOS", cStmt.getObject(8)); } catch (SQLException e) { retVal.put("ATRIBUTOS", null); }
            try { retVal.put("CLAUSULAS", cStmt.getObject(9)); } catch (SQLException e) { retVal.put("CLAUSULAS", null); }

            System.out.println("F_INFO_PROD_LIBERTY: "+retVal);


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_LIBERTY:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_AMGEN

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AMGEN( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AMGEN( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AMGEN( String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_AMGEN(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            System.out.println("Contra:" + pCDCONTRA);
            System.out.println("Suplem:" + pRGSUPLEM);
            System.out.println("Poliza:" + pCDPOLIZA);

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_RACSS:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_RACCS

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_RACCS( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_RACCS( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_RACCS( String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_RACCS(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_RACSS:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_STDER

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_STDER( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_STDER( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_STDER( String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_STDER(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_STDER:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_ASEFA

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ASEFA( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ASEFA( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ASEFA( String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_ASEFA(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_ASEFA:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_SURF

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_SURF( String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_SURF( pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_SURF(String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_SURF(?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (2, null );
            } else {
                cStmt.setObject (2, pCDPOLIZA );
            }

            cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_CAMPOS"
            cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GARANTIAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("CAMPOS", cStmt.getObject(3)); } catch (SQLException e) { retVal.put("CAMPOS", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(4)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }



            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_AGRUP:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    // F_INFO_PROD_AGRUP

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AGRUP( String pCDCONTRA,
                                                                    java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AGRUP( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_AGRUP( String pCDCONTRA,
                                                                  java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_AGRUP(?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_COBERTURAS"
            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_AMPLIACION"
            cStmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_PREGUNTAS"
            cStmt.registerOutParameter(8, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_HISTORIAL"
            cStmt.registerOutParameter(9, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_CLAUSULAS"

            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("COBERTURAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("COBERTURAS", null); }
            try { retVal.put("AMPLIACION", cStmt.getObject(6)); } catch (SQLException e) { retVal.put("AMPLIACION", null); }
            try { retVal.put("PREGUNTAS", cStmt.getObject(7)); } catch (SQLException e) { retVal.put("PREGUNTAS", null); }
            try { retVal.put("HISTORIAL", cStmt.getObject(8)); } catch (SQLException e) { retVal.put("HISTORIAL", null); }
            try { retVal.put("CLAUSULAS", cStmt.getObject(9)); } catch (SQLException e) { retVal.put("CLAUSULAS", null); }

            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_AGRUP:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_BBVA

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_BBVA( String pCDCONTRA,
                                                                   java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_BBVA( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_BBVA( String pCDCONTRA,
                                                                 java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_BBVA(?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_POLIZA"
            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_COBERTURAS"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("POLIZA", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("POLIZA", null); }
            try { retVal.put("COBERTURAS", cStmt.getObject(6)); } catch (SQLException e) { retVal.put("COBERTURAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_BBVA:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // F_INFO_PROD_ATLANTIS

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ATLANTIS( String pCDCONTRA,
                                                                       java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ATLANTIS( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_ATLANTIS( String pCDCONTRA,
                                                                     java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_ATLANTIS(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_POLIZA"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("GARANTIAS", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("GARANTIAS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_GASN:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    // F_INFO_PROD_GASN

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_GASN( String pCDCONTRA,
                                                                   java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_GASN( pCDCONTRA, pRGSUPLEM, pCDPOLIZA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_INFO_PROD_GASN( String pCDCONTRA,
                                                                 java.math.BigDecimal pRGSUPLEM, String pCDPOLIZA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INFO_PROD_GASN(?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDCONTRA);
            cStmt.setObject (3, pRGSUPLEM );
            if ( pCDPOLIZA.equals("-1")) {
                cStmt.setObject (4, null );
            } else {
                cStmt.setObject (4, pCDPOLIZA );
            }

            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_POLIZA"


            cStmt.execute();



            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("POLIZA", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("POLIZA", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            ////System.out.println("Salida F_INFO_PROD_GASN:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }


    // ***************************************************************prueba insertar - no se utiliza
    public void ejecutaPAC_SHWEB_EXPEDIENTES__INSERTAR( ArrayList pLISTA) throws Exception {
        callPAC_SHWEB_EXPEDIENTES__INSERTAR( pLISTA );
    }

    @SuppressWarnings("deprecation")
    private void callPAC_SHWEB_EXPEDIENTES__INSERTAR( ArrayList pLISTA  )  {
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_INSERTAR(?)}";

        //System.out.println("11111111111111111");
        try {
            if (conn.isWrapperFor(OracleConnection.class)){
                //System.out.println("SIIIIIIIIII conexión Oracle");
                OracleConnection oracleConnection= conn.unwrap(OracleConnection.class);
            }else{
                //System.out.println("NOOOOOOOOO conexión Oracle");
            }


            CallableStatement cStmt= conn.prepareCall(callQuery);
            ////System.out.println("3333");
            //Object[] itemAtributes = new Object[] {new String("Usuario"), new String("Password") };

            /*ArrayList lista = new ArrayList();
            lista.add("AAA1");
            lista.add("BBB2");

            String[] listaConvertida = (String[]) lista.toArray(new String[lista.size()]);*/

            String[] listaConvertida = (String[]) pLISTA.toArray(new String[pLISTA.size()]);

            //System.out.println("6666"+listaConvertida[0]+"..."+listaConvertida);
            Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE",listaConvertida);
            //System.out.println("7777");

            cStmt.setArray(2, a);

            //System.out.println("8888");

            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

            cStmt.execute();
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
        }

    }

    // ****************************** PRESUPUESTOS ********************************************

    // f_nuevo_presupuesto

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_NUEVO_PRESUPUESTO( java.math.BigDecimal pCDASISTE, String pCONSULTA, String pGARANTIA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_NUEVO_PRESUPUESTO( pCDASISTE , pCONSULTA, pGARANTIA);
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_NUEVO_PRESUPUESTO( java.math.BigDecimal pCDASISTE, String pCONSULTA, String pGARANTIA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_NUEVO_PRESUPUESTO(?,?,?,?,?,?,?)}";
        CallableStatement cStmt;

        try {



            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pGARANTIA);
            cStmt.setObject (6, pCONSULTA);


            cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER); // Valor de "P_IDPRES"
            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "P_MAIL"
            cStmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_CANAL"
            cStmt.registerOutParameter(8, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_GREMIOS"


            cStmt.execute();


            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("IDPRES", cStmt.getObject(4)); } catch (SQLException e) { retVal.put("IDPRES", null); }
            try { retVal.put("MAIL", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("MAIL", null); }
            try { retVal.put("CANAL", cStmt.getObject(7)); } catch (SQLException e) { retVal.put("CANAL", null); }
            try { retVal.put("GREMIOS", cStmt.getObject(8)); } catch (SQLException e) { retVal.put("GREMIOS", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            System.out.println("Salida F_NUEVO_PRESUPUESTO:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // f_detalle_presupuesto_1

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_1( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pGARANTIA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_1( pCDASISTE, pCDSERVIC, pGARANTIA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_1( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pGARANTIA )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_DETALLE_PRESUPUESTO_1(?,?,?,?)}";
        CallableStatement cStmt;

        try {



            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pCDSERVIC);
            cStmt.setObject (4, pGARANTIA);


            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_SERVICIOS"

            cStmt.execute();

            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("DETALLE1", cStmt.getObject(5)); } catch (SQLException e) { retVal.put("DETALLE1", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            System.out.println("Salida F_DETALLE_PRESUPUESTO_1:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }



    // f_detalle_presupuesto_2

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_2( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pDETALLE1 , String pGARANTIA ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_2( pCDASISTE, pCDSERVIC, pDETALLE1, pGARANTIA );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_DETALLE_PRESUPUESTO_2( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pDETALLE1, String pGARANTIA  )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_DETALLE_PRESUPUESTO_2(?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pCDSERVIC);
            cStmt.setObject (4, pDETALLE1 );
            cStmt.setObject (5, pGARANTIA );

            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR); // Valor de "P_OUT_DETALLE_2"

            cStmt.execute();

            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("DETALLE2", cStmt.getObject(6)); } catch (SQLException e) { retVal.put("DETALLE2", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            System.out.println("Salida F_DETALLE_PRESUPUESTO_2:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // f_detalle_IMPORTE_

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_DETALLE_IMPORTE( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pDETALLE1, String pDETALLE2  ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_DETALLE_IMPORTE( pCDASISTE, pCDSERVIC, pDETALLE1, pDETALLE2 );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_DETALLE_IMPORTE( java.math.BigDecimal pCDASISTE, String pCDSERVIC, String pDETALLE1, String pDETALLE2  )  throws Exception  {


        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_DETALLE_IMPORTE(?,?,?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pCDSERVIC);
            cStmt.setObject (4, pDETALLE1 );
            cStmt.setObject (5, pDETALLE2 );

            cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER); // Valor de "IMPORTE"

            cStmt.execute();

            HashMap retVal=new HashMap();
            try { retVal.put("RETURN", cStmt.getObject(1)); } catch (SQLException e) { retVal.put("RETURN", null); }
            try { retVal.put("IMPORTE", cStmt.getObject(6)); } catch (SQLException e) { retVal.put("IMPORTE", null); }


            HashMap subretVal = new HashMap();


            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            subretVal.put("RETURN",retVal);
            System.out.println("Salida F_DETALLE_IMPORTE:"+subretVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return subretVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // GUARDAR PRESUPUESTO


    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESUPUESTO( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pIDPRESU, String pIDCANAL
            ,String pMAIL, String pTELEFONO, String pESTADO, String pFHANULA, ArrayList pPRESU ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESUPUESTO( pCDASISTE, pIDPRESU, pIDCANAL,
                pMAIL, pTELEFONO, pESTADO, pFHANULA, pPRESU );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GUARDAR_PRESUPUESTO( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pIDPRESU, String pIDCANAL
            ,String pMAIL, String pTELEFONO, String pESTADO, String pFHANULA, ArrayList pPRESU ) throws Exception  {

        System.out.println("pac_shweb_expediente.f_guardar-presupuesto");

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GUARDAR_PRESUPUESTO(?,?,?,?,?,?,?,?)}";


        CallableStatement cStmt;

        String[] listaPresupuestos = (String[]) pPRESU.toArray(new String[pPRESU.size()]);

        Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaPresupuestos);

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pIDPRESU);
            cStmt.setObject (4, pIDCANAL);
            cStmt.setObject (5, pMAIL);
            cStmt.setObject (6, pTELEFONO);
            cStmt.setObject (7, pESTADO);
            cStmt.setObject (8, pFHANULA);
            cStmt.setArray(9, a);
            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            System.out.println("Salida Guardar PRESUPUESTO:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // GET_PRESUPUESTOS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_PRESUPUESTOS(java.math.BigDecimal pCDASISTE, java.math.BigDecimal pIDPRESU) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_PRESUPUESTOS(pCDASISTE, pIDPRESU );
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_PRESUPUESTOS(java.math.BigDecimal pCDASISTE, java.math.BigDecimal pIDPRESU ) throws Exception {
        ////System.out.println("Entrat f_get_PRESUPUESTOS filtros:"+pFILTROS+ "VerBaja:"+pVERBAJA);
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_PRESUPUESTOS(?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        if (pCDASISTE.intValue()==-1) {
            cStmt.setObject (2, null);
        }
        else {
            cStmt.setObject (2, pCDASISTE);
        }
        if (pIDPRESU.intValue()==-1) {
            cStmt.setObject (3, null);
        }
        else {
            cStmt.setObject (3, pIDPRESU);
        }

        cStmt.registerOutParameter(4, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(4));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }

        ////System.out.println("1_Salida contratos:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida PRESUPUESTOS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }


    // enviar_comunicado_presu

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__ENVIAR_COMUNICADO_PRESU(java.math.BigDecimal pIDPRES )  throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__ENVIAR_COMUNICADO_PRESU(pIDPRES);
    }


    private HashMap callPAC_SHWEB_EXPEDIENTES__ENVIAR_COMUNICADO_PRESU(java.math.BigDecimal pIDPRES) throws Exception  {


        //System.out.println("Entramos a PENDIENTE ASIGNAR");
        String callQuery="{call PAC_SHWEB_EXPEDIENTES.ENVIAR_COMUNICADO_PRESU(?,?,?)}";
        CallableStatement cStmt;

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
            cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
            cStmt.setObject (1, pIDPRES);

            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(2));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            try {
                retVal.put("TXTERROR", cStmt.getObject(3));
            }
            catch (SQLException e) {
                retVal.put("TXTERROR", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            //System.out.println("Salida PENDIENTE ASIGNAR:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // GENERAR_COBROS


    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GENERAR_COBROS( java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pIDPRESU, String pMETODOPAGO
            ,java.math.BigDecimal pTIPOPAGO, java.lang.Double pIMPORTEPAGO,  ArrayList pPRESU ) throws Exception {
        return callPAC_SHWEB_EXPEDIENTES__F_GENERAR_COBROS( pCDASISTE, pIDPRESU, pMETODOPAGO,
                pTIPOPAGO, pIMPORTEPAGO, pPRESU );
    }

    @SuppressWarnings("deprecation")
    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GENERAR_COBROS(java.math.BigDecimal pCDASISTE
            ,java.math.BigDecimal pIDPRESU, String pMETODOPAGO
            ,java.math.BigDecimal pTIPOPAGO, java.lang.Double pIMPORTEPAGO,  ArrayList pPRESU  ) throws Exception  {

        System.out.println("pac_shweb_expediente.f_GENERAR_COBROS");

        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GENERAR_COBROS(?,?,?,?,?,?)}";


        CallableStatement cStmt;

        String[] listaPresupuestos = (String[]) pPRESU.toArray(new String[pPRESU.size()]);

        Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("ARRAY_TABLE_EXPEDIENTES",listaPresupuestos);

        try {
            cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pIDPRESU);
            cStmt.setObject (4, pMETODOPAGO);
            cStmt.setObject (5, pTIPOPAGO);
            cStmt.setObject (6, pIMPORTEPAGO);
            cStmt.setArray(7, a);
            cStmt.execute();

            HashMap retVal=new HashMap();
            try {
                retVal.put("RETURN", cStmt.getObject(1));
            }
            catch (SQLException e) {
                retVal.put("RETURN", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
            System.out.println("Salida GENERAR_COBROS:"+retVal);
            cStmt.close();
            cStmt = null;
            conn.close();
            conn = null;
            return retVal;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //System.out.println("ERROR:"+e);
            e.printStackTrace();
            return null;
        }

    }

    // GET_COBROS
    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_GET_COBROS(java.math.BigDecimal pIDPRESU) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_GET_COBROS(pIDPRESU );
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_GET_COBROS(java.math.BigDecimal pIDPRESU ) throws Exception {
        ////System.out.println("Entrat f_get_PRESUPUESTOS filtros:"+pFILTROS+ "VerBaja:"+pVERBAJA);
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_GET_COBROS(?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        if (pIDPRESU.intValue()==-1) {
            cStmt.setObject (2, null);
        }
        else {
            cStmt.setObject (2, pIDPRESU);
        }

        cStmt.registerOutParameter(3, java.sql.Types.VARCHAR); // Valor de "MSGERROR"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        try {
            retVal.put("MSGERROR", cStmt.getObject(3));
        }
        catch (SQLException e) {
            retVal.put("MSGERROR", null);
        }

        ////System.out.println("1_Salida contratos:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida PRESUPUESTOS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }


    // F_ANADIR_PAGO_LOG
    /*FUNCTION f_anadir_pago_log (
    	      p_idcobro         IN       NUMBER ,
    	      p_cdasiste        IN       NUMBER,
    	      p_importe         IN       NUMBER,
    	      p_url             IN       VARCHAR2,
    	      p_usuario         IN       VARCHAR2,
    	      p_mensaje_error   OUT      VARCHAR2
    	   ) RETURN SYS_REFCURSOR IS*/

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_ANADIR_PAGO_LOG(java.math.BigDecimal pIDCOBRO, java.math.BigDecimal pCDASISTE,
                                                                   java.lang.Double pIMPORTEPAGO, String pURL, String pUSUARIO) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_ANADIR_PAGO_LOG(pIDCOBRO, pCDASISTE, pIMPORTEPAGO, pURL, pUSUARIO );
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_ANADIR_PAGO_LOG(java.math.BigDecimal pIDCOBRO, java.math.BigDecimal pCDASISTE,
                                                                 java.lang.Double pIMPORTEPAGO, String pURL, String pUSUARIO) throws Exception {
        System.out.println("Entrat F_ANADIR_PAGO_LOB");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_ANADIR_PAGO_LOG(?,?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.setObject (2, pIDCOBRO);
        cStmt.setObject (3, pCDASISTE);
        cStmt.setObject (4, pIMPORTEPAGO);
        cStmt.setObject (5, pURL);
        cStmt.setObject (6, pUSUARIO);

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }


        ////System.out.println("1_Salida contratos:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida F_ANADIR_PAGO_LOG:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }

    // F_CREAR_COMUNICACION
    /*  FUNCTION f_crear_comunicacion (
      p_cdasiste      IN   NUMBER,
      p_nuorden       IN   NUMBER,
      p_tpentsal      IN   VARCHAR2,
      p_dsorigen      IN   VARCHAR2,
      p_dsdestin      IN   VARCHAR2,
      p_swextern      IN   VARCHAR2,
      p_tpcomuni      IN   VARCHAR2,
      p_txcomuni      IN   VARCHAR2,
      p_cdprovee      IN   VARCHAR2,
      p_enviarci      IN   VARCHAR2,

      -- estos no los ponemos de momento, no son necesarios
      p_cdtipo        IN   NUMBER,
      p_cdtipologia   IN   NUMBER,
      p_rgpresta      IN   NUMBER,
      p_cdcobert      IN   VARCHAR2,
      p_cdgarant      IN   VARCHAR2,
      p_cdservic      IN   NUMBER,
      p_fhini_com     IN   DATE DEFAULT SYSDATE,
      p_fhfin_com     IN   DATE DEFAULT SYSDATE
   )
      RETURN NUMBER*/

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_CREAR_COMUNICACION(java.math.BigDecimal pCDASISTE
            , java.math.BigDecimal pNUORDEN
            , String pTPENTSAL
            , String pDSORIGEN
            , String pDSDESTIN
            , String pSWEXTERN
            , String pTPCOMUNI
            , String pTXCOMUNI
    ) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_CREAR_COMUNICACION(pCDASISTE, pNUORDEN, pTPENTSAL, pDSORIGEN,
                pDSDESTIN, pSWEXTERN, pTPCOMUNI, pTXCOMUNI);
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_CREAR_COMUNICACION(java.math.BigDecimal pCDASISTE
            , java.math.BigDecimal pNUORDEN
            , String pTPENTSAL
            , String pDSORIGEN
            , String pDSDESTIN
            , String pSWEXTERN
            , String pTPCOMUNI
            , String pTXCOMUNI
    ) throws Exception {
        System.out.println("Entrat F_CREAR_COMUNICACION");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_CREAR_COMUNICACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor de "RETURN"

        cStmt.setObject (2, pCDASISTE);
        cStmt.setObject (3, pNUORDEN);
        cStmt.setObject (4, pTPENTSAL);
        cStmt.setObject (5, pDSORIGEN);
        cStmt.setObject (6, pDSDESTIN);
        cStmt.setObject (7, pSWEXTERN);
        cStmt.setObject (8, pTPCOMUNI);
        cStmt.setObject (9, pTXCOMUNI);

        // campos en blanco
        cStmt.setObject (10, null);
        cStmt.setObject (11, null);
        cStmt.setObject (12, null);
        cStmt.setObject (13, null);
        cStmt.setObject (14, null);
        cStmt.setObject (15, null);
        cStmt.setObject (16, null);
        cStmt.setObject (17, null);

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }


        ////System.out.println("1_Salida contratos:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida F_CREAR_COMUNICACION:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }

    /*FUNCTION f_plantilla_perito (
      p_cdasiste                IN       NUMBER,
      p_nuorden                 IN       NUMBER,
      p_nota_importante         IN       VARCHAR2,
      p_informacion_siniestro   IN       VARCHAR2,
      p_trabajos_realizados     IN       VARCHAR2,
      p_mail                    IN       VARCHAR2,
      p_servicios               IN       VARCHAR2,
      p_plantilla               OUT      VARCHAR2
   )
      RETURN NUMBER*/

    public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_PLANTILLA_PERITO(java.math.BigDecimal pCDASISTE,
                                                                    String pNOTA, String pINFORMACION, String pTRABAJOS, String pMAIL, String pSERVICIOS, String pMOTIVOPERITO) throws Exception {
        return this.callPAC_SHWEB_EXPEDIENTES__F_PLANTILLA_PERITO(pCDASISTE,pNOTA, pINFORMACION, pTRABAJOS, pMAIL, pSERVICIOS, pMOTIVOPERITO );
    }

    private HashMap callPAC_SHWEB_EXPEDIENTES__F_PLANTILLA_PERITO(java.math.BigDecimal pCDASISTE,
                                                                  String pNOTA, String pINFORMACION, String pTRABAJOS, String pMAIL, String pSERVICIOS, String pMOTIVOPERITO) throws Exception {
        System.out.println("Entrat F_PLANTILLA_PERITO");
        String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_PLANTILLA_PERITO(?,?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor de "RETURN"
        cStmt.registerOutParameter(10, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "RETURN"

        cStmt.setObject (2, pCDASISTE);
        cStmt.setObject (3, new java.math.BigDecimal("1") );
        cStmt.setObject (4, pNOTA);
        cStmt.setObject (5, pINFORMACION);
        cStmt.setObject (6, pTRABAJOS);
        cStmt.setObject (7, pMAIL);
        cStmt.setObject (8, pSERVICIOS);
        cStmt.setObject (9, pMOTIVOPERITO);

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            System.out.println("Error" + e);
            retVal.put("RETURN", null);
        }

        try {
            retVal.put("PLANTILLA", cStmt.getObject(10));
        }
        catch (SQLException e) {
            retVal.put("PLANTILLA", null);
        }


        System.out.println("ANTES F_PLANTILLA_PERITO:"+retVal);
        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        System.out.println("Salida F_PLANTILLA_PERITO:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }

}
