package org.srcom.loomerdb.server.jdbc.util;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


import lombok.extern.apachecommons.CommonsLog;
import oracle.jdbc.OracleResultSet;
import org.springframework.util.StringUtils;


@CommonsLog
public class ConversionUtil {

    public static String SIMPLENUMBERFORMAT="#.#";
    //public static String SCREENNUMBERFORMAT = "###,###.##";

    /**
     * Convierte un oracle.sql.STRUCT a un HashMap.
     * Requiere que se mantiene la conexion abierta durante esa operacion.
     * Funcion se ejecuta de forma recursiva / alterando con listOracleARRAY
     * @param mySTRUCT STRUCT de entrada
     * @return
     * @throws Exception
     */
    public HashMap hashOracleSTRUCT(oracle.sql.STRUCT mySTRUCT) throws Exception {
        return this.hashOracleSTRUCT(mySTRUCT, null);
        //return this.hashOracleSTRUCT(mySTRUCT, "__");
    }

    /**
     * Convierte un oracle.sql.STRUCT a un HashMap.
     * Requiere que se mantiene la conexion abierta durante esa operacion.
     * Funcion se ejecuta de forma recursiva / alterando con listOracleARRAY
     * @param mySTRUCT STRUCT de entrada
     * @param typenameSeparator Cadena con la cual se va a sustituir el "." entre Esquema-Prefix y nombre del Tipo de dato (por ejemplo: "__"). Un 'null' causa una no-utilizacion del esquema.
     * @return
     * @throws Exception
     */
    public HashMap hashOracleSTRUCT(oracle.sql.STRUCT mySTRUCT, String typenameSeparator) throws Exception {
        HashMap tempHash=null;
        if (mySTRUCT!=null) {
            oracle.sql.Datum[] dats=mySTRUCT.getOracleAttributes();
            tempHash=new HashMap();
            for (int i=1; i<=mySTRUCT.getDescriptor().getMetaData().getColumnCount(); i++) {
                String colName=mySTRUCT.getDescriptor().getMetaData().getColumnName(i);
                Object colObject=dats[i-1];
                if (colObject!=null)
                    colObject=dats[i-1].toJdbc();
                boolean done=false;
                if (!done&&colObject!=null&&colObject instanceof oracle.sql.ARRAY) {
                    colObject=listOracleARRAY((oracle.sql.ARRAY)colObject, typenameSeparator);
                    done=true;
                }
                if (!done&&colObject!=null&&colObject instanceof oracle.sql.STRUCT) {
                    colObject=hashOracleSTRUCT((oracle.sql.STRUCT)colObject, typenameSeparator);
                    done=true;
                }
                if (!done&&colObject!=null) {
                    // colObject=colObject; // ...o se que : pasa nada.
                    done=true;
                }
                if (!done) {
                    colObject=null;
                    done=true;
                }
                tempHash.put(colName.toUpperCase(), colObject);
            }
        }
        return tempHash;
    }

    /**
     * Convierte un oracle.sql.ARRAY a un ArrayList.
     * Requiere que se mantiene la conexion abierta durante esa operacion.
     * Funcion se ejecuta de forma recursiva / alterando con hashOracleSTRUCT
     * @param myARRAY ARRAY de etrada
     * @return
     * @throws Exception
     */
    public ArrayList listOracleARRAY(oracle.sql.ARRAY myARRAY) throws Exception {
        return this.listOracleARRAY(myARRAY, null);
        //return this.listOracleARRAY( myARRAY, "__");
    }

    /**
     * Convierte un oracle.sql.ARRAY a un ArrayList.
     * Requiere que se mantiene la conexion abierta durante esa operacion.
     * Funcion se ejecuta de forma recursiva / alterando con hashOracleSTRUCT
     * @param myARRAY ARRAY de etrada
     * @param typenameSeparator Cadena con la cual se va a sustituir el "." entre Esquema-Prefix y nombre del Tipo de dato (por ejemplo: "__"). Un 'null' causa una no-utilizacion del esquema.
     * @return
     * @throws Exception
     */
    public ArrayList listOracleARRAY(oracle.sql.ARRAY myARRAY, String typenameSeparator) throws Exception {
        ArrayList retVal=new ArrayList();
        HashMap tempHash=null;
        Object[] values=(Object[])myARRAY.getArray();
        int columnCount=0;
        for (int v=0; v<values.length; v++) {
            tempHash=new HashMap();
            Object myValue=values[v];
            boolean done=false;
            if (!done&&myValue!=null&&myValue instanceof oracle.sql.STRUCT) {
                oracle.sql.STRUCT myStruct=(oracle.sql.STRUCT)myValue;
                String sqlTypeName=myStruct.getSQLTypeName();
                if (typenameSeparator==null) {
                    if (sqlTypeName.indexOf(".")>0)
                        sqlTypeName=sqlTypeName.substring(sqlTypeName.indexOf(".")+1);
                }
                else {
                    sqlTypeName=StringUtils.replace(sqlTypeName, ".", typenameSeparator);
                }
                //tempHash.put( StringUtils.replace(myStruct.getSQLTypeName(),".",typenameSeparator), this.hashOracleSTRUCT(myStruct, typenameSeparator));
                tempHash.put(sqlTypeName, this.hashOracleSTRUCT(myStruct, typenameSeparator));
                done=true;
            }
            if (!done&&myValue!=null&&myValue instanceof oracle.sql.ARRAY) {
                oracle.sql.ARRAY myArray=(oracle.sql.ARRAY)myValue;
                String baseTypName=myArray.getBaseTypeName();
                if (typenameSeparator==null) {
                    if (baseTypName.indexOf(".")>0)
                        baseTypName=baseTypName.substring(baseTypName.indexOf(".")+1);
                }
                else {
                    baseTypName=StringUtils.replace(baseTypName, ".", typenameSeparator);
                }
                //tempHash.put( StringUtils.replace(myArray.getBaseTypeName(),".",typenameSeparator), this.listOracleARRAY( myArray, typenameSeparator));
                tempHash.put(baseTypName, this.listOracleARRAY(myArray, typenameSeparator));
                done=true;
            }
            if (!done&&myValue!=null) {
                tempHash.put("OBJECT", myValue);
                done=true;
            }
            if (!done) {
                tempHash.put("OBJECT", null);
                done=true;
            }
            retVal.add(tempHash);
        }
        return retVal;
    }

    public HashMap convertOracleObjects(HashMap oracleObjectsMap) throws Exception {
        Object[] keySetArray=oracleObjectsMap.keySet().toArray();
        HashMap objectsMap=new HashMap();
        boolean done=false;
        for (int i=0; i<keySetArray.length; i++) {
            done=false;
            String className="";
            try {
                className=oracleObjectsMap.get(keySetArray[i].toString()).getClass().getName();
                //logger.debug(className);
            }
            catch (NullPointerException e) {
                //logger.warn(keySetArray[i].toString()+" es nulo");
            }

            if (!done&&oracleObjectsMap.get(keySetArray[i].toString()) instanceof oracle.sql.STRUCT) {
                objectsMap.put(keySetArray[i].toString(), this.hashOracleSTRUCT((oracle.sql.STRUCT)oracleObjectsMap.get(keySetArray[i].toString()), null));
                done=true;
            }
            if (!done&&oracleObjectsMap.get(keySetArray[i].toString()) instanceof oracle.sql.ARRAY) {
                objectsMap.put(keySetArray[i].toString(), this.listOracleARRAY((oracle.sql.ARRAY)oracleObjectsMap.get(keySetArray[i].toString()), null));
                done=true;
            }

            if (!done&&className.indexOf("OracleResultSet")>=0) {
                objectsMap.put(keySetArray[i].toString(), this.oracleresultSet2ArrayList((OracleResultSet)oracleObjectsMap.get(keySetArray[i].toString())));
                done=true;
            }
            if (!done&&className.indexOf("ResultSet")>=0&&className.indexOf("Oracle")<0) {
                objectsMap.put(keySetArray[i].toString(), this.resultSet2ArrayList((ResultSet)oracleObjectsMap.get(keySetArray[i].toString())));
                done=true;
            }
            if (!done&&oracleObjectsMap.get(keySetArray[i].toString()) instanceof oracle.sql.CLOB) {
                objectsMap.put(keySetArray[i].toString(), this.clob2String((oracle.sql.CLOB)oracleObjectsMap.get(keySetArray[i].toString())));
                done=true;
            }

            if (!done) {
                // si aun no lo hemos convertido, lo pasamos a saco
                objectsMap.put(keySetArray[i].toString(), oracleObjectsMap.get(keySetArray[i].toString()));
                done=true;
            }
        }
        return objectsMap;
    }

    /**
     * Convierte un CLOB en un String.
     * @param clob CLOB de entrada
     * @return
     * @throws Exception
     */
    public static String clob2String(oracle.sql.CLOB clob) throws Exception {
        StringBuffer retVal=new StringBuffer();
        if (clob==null)
            return null;
        Reader clobStream=clob.getCharacterStream();
        // Read from the Clob stream and write to the stringbuffer  
        int nchars=0; // Number of characters read
        //Buffer holding characters being transferred  
        char[] buffer=new char[10];
        while ((nchars=clobStream.read(buffer))!=-1)
            retVal.append(buffer, 0, nchars); // Write to StringBuffer
        clobStream.close(); // Close the Clob input stream.....................

        return retVal.toString();
    }

    /**
     * Convierte un ResultSet en un ArrayList de HashMaps, donde la claves de los HashMaps son los nombre de las columnas (en MAYUSCULAS).
     * Se copia el tipo de dato que retorna JDBC tal y cual.
     * @param rs Resultset de entrada
     * @return
     * @throws Exception
     */
    public static ArrayList resultSet2ArrayList(ResultSet rs) throws Exception {
        int columnCount=rs.getMetaData().getColumnCount();
        String[] columnNames=new String[columnCount];
        for (int i=0; i<columnCount; i++) {
            columnNames[i]=rs.getMetaData().getColumnName(i+1).toUpperCase();
        }
        ArrayList retVal=new ArrayList();
        HashMap item=null;
        while (rs.next()) {
            item=new HashMap();
            for (int i=0; i<columnCount; i++) {
                item.put(columnNames[i], rs.getObject(i+1));
                if (rs.getObject(i+1) instanceof oracle.sql.CLOB) {
                    item.put(columnNames[i], ConversionUtil.clob2String((oracle.sql.CLOB)rs.getObject(i+1)));
                }
            }
            retVal.add(item);
        }

        // si no se cierra el ResultsetSet puede ser que se quedan cursores abiertos...
        try {
            rs.close();
        }
        catch (Exception e) {
            log.error("Error durante el intento de cerrar un ResultSet.", e);
        }

        return retVal;
    }

    public static ArrayList oracleresultSet2ArrayList(OracleResultSet rs) throws Exception {
        int columnCount=rs.getMetaData().getColumnCount();
        String[] columnNames=new String[columnCount];
        for (int i=0; i<columnCount; i++) {
            columnNames[i]=rs.getMetaData().getColumnName(i+1).toUpperCase();
        }
        ArrayList retVal=new ArrayList();
        HashMap item=null;
        while (rs.next()) {
            item=new HashMap();
            for (int i=0; i<columnCount; i++) {
                item.put(columnNames[i], rs.getObject(i+1));
                if (rs.getObject(i+1) instanceof oracle.sql.CLOB) {
                    item.put(columnNames[i], ConversionUtil.clob2String((oracle.sql.CLOB)rs.getObject(i+1)));
                }
            }
            retVal.add(item);
        }

        // si no se cierra el ResultsetSet puede ser que se quedan cursores abiertos...
        try {
            rs.close();
        }
        catch (Exception e) {
            log.error("Error durante el intento de cerrar un OracleResultSet.", e);
        }

        return retVal;
    }

    /**
     * Convierte una String con fecha formateada como dd/MM/yyyy 
     * a un String con formato TimeStamp de Oracle.
     * @param ddMMyyyy La String con fecha en formato dd/MM/yyyy
     * @return Un String con fecha en formato yyyy-MM-dd hh:mm:ss
     */
    public static String getTimeStamp(String ddMMyyyy) {
        if (LoomerBaseService.isEmpty(ddMMyyyy))
            return null;
        try {
            String day=ddMMyyyy.split("/")[0].trim();
            String month=ddMMyyyy.split("/")[1].trim();
            String year=ddMMyyyy.split("/")[2].trim();

            return year+"-"+month+"-"+day+" 00:00:00";
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Convierte un Date con fecha formateada como dd/MM/yyyy 
     * a un String con formato TimeStamp de Oracle.
     * @param ddMMyyyy La String con fecha en formato dd/MM/yyyy
     * @return Un String con fecha en formato yyyy-MM-dd hh:mm:ss
     */
    public static String getTimeStamp(java.util.Date ddMMyyyy) {
        if (ddMMyyyy!=null)
            return null;

        return getTimeStamp(LoomerBaseService.datePrintfddMMyyyy(ddMMyyyy));
    }


    /**
     * Dado un String conteniendo c�digo XML v�lido, devuelve el objeto resultado
     * de "unmarshalear" el XML a objetos Java.
     * 
     * El fichero XML debe ser un XML normal, con un atributo "jclass" por
     * cada nodo, que especifique la clase Java del atributo.
     * 
     * Para conseguir un XML de prueba, ejecutar 
     * <code>ConversionUtil.printAsXML(Object o, String rootElement)</code>.
     * 
     * @param XML_STRING String conteniendo un fichero XML.
     * @return Object El objeto creado que representa el XML.
     */
    /*public Object createObjectFromXMLString(String XML_STRING) {
        return new XML2ObjectFactory().createObjectFromXMLString(XML_STRING);
    }*/

    /**
     * Dado un fichero XML existente, devuelve el objeto resultado
     * de "unmarshalear" el XML a objetos Java.
     * 
     * El fichero XML debe ser un XML normal, con un atributo "jclass" por
     * cada nodo, que especifique la clase Java del atributo.
     * 
     * Para conseguir un XML de prueba, ejecutar 
     * <code>ConversionUtil.printAsXML(Object o, String rootElement)</code>.
     * 
     * @param XML_FILE_NAME Path hacia el fichero XML.
     * @return Object El objeto creado que representa el XML.
     */
    /*public static Object createObjectFromXMLFile(String XML_FILE_NAME) {
        return new XML2ObjectFactory().createObjectFromXMLFile(XML_FILE_NAME);
    }


    public static String printAsXML(Object o, String rootElement) {
        return new ConversionUtil().printAsXML(o, rootElement, true, false, "yyyy-MM-dd'T'HH:mm:ss.SSS'+01:00'");
    }*/

    /**
     * Produce un XML-String (windows-1252) de un objeto ArrayList o HashMap
     * @param o ArrayList o HashMap
     * @param rootElement Nombre del elemento raiz del XML-String
     * @param showJclass Presentar o no la clase Java en XML (default true)
     * @param showNull Presentar o no un indicador de NULLs en XML (default true)
     * @param timestampPattern Plantilla para representar timeStamps, notaci�n Java (default "yyyy-MM-dd'T'HH:mm:ss.SSS'+01:00'")
     * @return
     */
    public String printAsXML(Object o, String rootElement, boolean showJclass, boolean showNull, String timestampPattern, boolean autoNodeNames) {
        StringBuffer retVal=new StringBuffer();
        // retVal.append("<?xml version=\"1.0\" encoding=\"windows-1252\"?>\n");
        retVal.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
        retVal.append("<").append(rootElement).append(" jclass=\"java.util.HashMap\">\n");
        retVal.append(asXML(o, showJclass, showNull, timestampPattern, autoNodeNames));
        retVal.append("</").append(rootElement).append(">");
        return retVal.toString();
    }

    public String printAsXML(Object o, String rootElement, boolean showJclass, boolean showNull, String timestampPattern) {
        return printAsXML(o, rootElement, showJclass, showNull, timestampPattern, false);
    }

    /**
     * Produce un XML-String (windows-1252) de un objeto ArrayList o HashMap sin rootElement
     * 
     * 9557�20090326�ecg�escapear en el toString al final &,< y >
     * 
     * @param o ArrayList o HashMap
     * @param showJclass Presentar o no la clase Java en XML (default true)
     * @param showNull Presentar o no un indicador de NULLs en XML (default true)
     * @param timestampPattern Plantilla para representar timeStamps, notaci�n Java (default "yyyy-MM-dd'T'HH:mm:ss.SSS'+01:00'")
     * @return
     */
    private String asXML(Object o, boolean showJclass, boolean showNull, String timestampPattern, boolean autoNodeNames) {
        String retVal="";
        boolean done=false;
        if (o instanceof ArrayList) {
            done=true;
            ArrayList t=(ArrayList)o;
            if (t!=null) {
                if (autoNodeNames)
                    retVal=retVal+"<__node type='COLLECTION'>\n";
                for (int i=0; i<t.size(); i++) {
                    Object oSub=t.get(i);
                    String jclassString="";
                    if (showJclass)
                        jclassString="jclass=\""+((oSub!=null)?oSub.getClass().getName():"(NULL)")+"\"";
                    String nullString="";
                    if (showNull)
                        nullString="null=\""+((oSub==null)?"true\"":"false\"");
                    retVal=retVal+"<element "+jclassString+" "+nullString+">"+asXML(oSub, showJclass, showNull, timestampPattern, autoNodeNames)+"</element>\n";
                }
                if (autoNodeNames)
                    retVal=retVal+"</__node>\n";
            }
            else {
            }
        }
        if (o instanceof HashMap) {
            done=true;
            HashMap t=(HashMap)o;
            if (t!=null) {
                if (autoNodeNames)
                    retVal=retVal+"<__node type='OBJECT'>\n";
                Object[] myKeySetArray=t.keySet().toArray();
                for (int i=0; i<myKeySetArray.length; i++) {
                    String keyName=(String)myKeySetArray[i];
                    Object oSub=t.get(keyName);
                    String jclassString="";
                    if (showJclass)
                        jclassString="jclass=\""+((oSub!=null)?oSub.getClass().getName():"(NULL)")+"\"";
                    String nullString="";
                    if (showNull)
                        nullString=" null=\""+((oSub==null)?"true\"":"false\"");
                    retVal=retVal+"<"+keyName+" "+jclassString+nullString+">"+asXML(oSub, showJclass, showNull, timestampPattern, autoNodeNames)+"</"+keyName+">\n";
                }
                if (autoNodeNames)
                    retVal=retVal+"</__node>\n";
            }
            else {
            }
        }

        if (o instanceof java.sql.Timestamp) {
            done=true;
            SimpleDateFormat sdf=new SimpleDateFormat(timestampPattern);
            retVal=retVal+((o!=null)?sdf.format(o):"");
            // Complete date plus hours, minutes, seconds and a decimal fraction of a
            // second
            // YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)

        }

        if (o instanceof BigDecimal||o instanceof Integer||o instanceof Double||o instanceof Float) {
            done=true;
            //retVal + ((o != null) ? (new DecimalFormat(this.SIMPLENUMBERFORMAT).format(o)) : 
            // "");
            retVal=retVal+((o!=null)?o:"");
        }

        if (!done) {
            done=true;
            String miToString=(o!=null)?o.toString():"";

            // 9557�20090326�ecg�escapear en el toString al final &,< y >
            miToString= StringUtils.replace(miToString, "&", "&amp;");
            miToString=StringUtils.replace(miToString, "<", "&lt;");
            miToString=StringUtils.replace(miToString, ">", "&gt;");

            retVal=retVal+miToString;
        }
        return retVal;
    }

    /**
     * Funci�n que devuelve un BigDecimal a partir de un String
*     * @return BigDecimal
     */
    public static BigDecimal toBigDecimal(String decimal) {
        BigDecimal numero=null;
        try {
            numero=new BigDecimal(decimal);
        }
        catch (NumberFormatException e) {
            numero=null;
        }
        return numero;
    }


    /**
     * TRATAMIENTO DE LOS LOCALES
     */
    public static

    Map locales=new HashMap();

    static {
        locales.put("ca_ES", BigDecimal.ONE);
        locales.put("es_ES", BigDecimal.valueOf(2));
        locales.put("es_CL", BigDecimal.valueOf(3));
        locales.put("pt_PT", BigDecimal.valueOf(4));
        //locales.put("hr_HR", BigDecimal.valueOf(5)); //Linea momentanea para que el idioma ingl�s formatee los numeros como en espa�a y continu� saliendo el idioma en ingl�s        
        locales.put("en_UK", BigDecimal.valueOf(5));
        locales.put("nl_BE", BigDecimal.valueOf(6));
        locales.put("fr_BE", BigDecimal.valueOf(7));
        //ecg20111114 problemas con el formateo americano de es_CO, pongamos 8 como danes locales.put("es_CO", BigDecimal.valueOf(8)); //ecg20111109 8:es_CO, AXIS3276
        locales.put("da_DA", BigDecimal.valueOf(8)); //ecg20111114 pongamos 8 (colombiano) como danes


        Set keys=locales.keySet();
        Iterator<String> it=keys.iterator();
        Map tmpLocales=new HashMap();

        while (it.hasNext()) {
            String key=it.next();
            tmpLocales.put(locales.get(key), key);
        }

        if (tmpLocales!=null)
            locales.putAll(tmpLocales);
    }








    public static ArrayList o2list(Object o) {
        return o2list(o, null);
    }

    public static ArrayList o2list(Object o, String prefijo) {
        ArrayList retVal=new ArrayList();
        HashMap m=null;
        if (o!=null) {
            if (o instanceof List) {
                List mio=(List)o;
                for (int i=0; i<mio.size(); i++) {

                }
            }
            if (o instanceof Map) {
                Map mio=(Map)o;
                Object[] keySetArray=mio.keySet().toArray();
                for (int i=0; i<keySetArray.length; i++) {
                    String tempKey=(String)keySetArray[i];
                    if (mio.get(tempKey)==null) {
                        m=new HashMap();
                        m.put("K", tempKey);
                        m.put("V", "__null__");
                        retVal.add(m);
                    }
                    else {
                        if (mio.get(tempKey) instanceof List||mio.get(tempKey) instanceof Map) {
                            retVal.addAll(o2list(mio, ""));
                        }
                        else {
                            m=new HashMap();
                            m.put("K", ((prefijo!=null)?prefijo+".":"")+tempKey);
                            m.put("V", mio.get(tempKey).toString());
                            retVal.add(m);
                        }
                    }
                }
            }
            else {
                m=new HashMap();
                m.put("K", ((prefijo!=null)?prefijo:""));
                m.put("V", o.toString());
                retVal.add(m);
            }
        }
        return retVal;
    }

    public static final NumberFormat usNumberFormat=NumberFormat.getInstance(Locale.US);
    public static final SimpleDateFormat SDF_yyyyMMddHHmmssSSS=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat SDF_yyyyMMdd=new SimpleDateFormat("yyyyMMdd");




    public static final NumberFormat jsonNumberFormat=NumberFormat.getInstance(Locale.US);
    static {
        jsonNumberFormat.setGroupingUsed(false);
    }


    /**
     * Convierte un tipico JsonObject de la web (o que sea) a un objeto Java.
     * Recursivo!
     * Tratamiento especial para: NULL, HashMap, ArrayList, Numericos, Fechas. El resto: toString()'s
     * @param o
     * @return
     * @throws Exception
     */
    /*private Object asJavaObject(Object o) throws Exception {
        if (o==null)
            return null;
        if (o instanceof JSONArray) {
            ArrayList retVal=new ArrayList();
            JSONArray t=(JSONArray)o;
            for (int i=0; i<t.length(); i++) {
                retVal.add(asJavaObject(t.get(i)));
            }
            return retVal;
        }
        if (o instanceof JSONObject) {
            HashMap retVal=new HashMap();
            JSONObject t=(JSONObject)o;
            String[] myNames=JSONObject.getNames(t);
            for (int i=0; i<myNames.length; i++) {
                String keyName=myNames[i];
                Object oSub=t.get(keyName);
                retVal.put(keyName, asJavaObject(oSub));
            }
            return retVal;
        }
        if (o instanceof BigDecimal||o instanceof Integer||o instanceof Double||o instanceof Float) {
            return jsonNumberFormat.format(o);
        }
        if (o instanceof java.sql.Date) { // el orden relativo oa TimeStamp y Date importa!
            return SDF_yyyyMMdd.format(o);
        }
        if (o instanceof java.util.Date) { // el orden relativo oa TimeStamp y Date importa!
            return SDF_yyyyMMddHHmmssSSS.format(o);
        }
        if (o instanceof java.sql.Timestamp) {
            return SDF_yyyyMMddHHmmssSSS.format(o);
        }
        if (o instanceof String) {
            return o.toString();
        }

        return o;

    }*/

    /**
     * Convierte un tipico HashMapArrayList de la BBDD (o que sea) a un JSONObject.
     * Recursivo!
     * Tratamiento especial para: NULL, HashMap, ArrayList, Numericos, Fechas. El resto: toString()'s
     * @param
     * @return
     * @throws Exception
     */
    /*private Object asJSONObject(Object o) throws Exception {
        if (o==null)
            return JSONObject.NULL;
        if (o instanceof ArrayList) {
            JSONArray retVal=new JSONArray();
            ArrayList t=(ArrayList)o;
            for (int i=0; i<t.size(); i++) {
                retVal.put(asJSONObject(t.get(i)));
            }
            return retVal;
        }
        if (o instanceof HashMap) {
            JSONObject retVal=new JSONObject();
            HashMap t=(HashMap)o;
            Object[] myKeySetArray=t.keySet().toArray();
            for (int i=0; i<myKeySetArray.length; i++) {
                String keyName=(String)myKeySetArray[i];
                Object oSub=t.get(keyName);
                retVal.put(keyName, asJSONObject(oSub));
            }
            return retVal;
        }
        if (o instanceof BigDecimal||o instanceof Integer||o instanceof Double||o instanceof Float) {
            return jsonNumberFormat.format(o);
        }
        if (o instanceof java.sql.Date) { // el orden relativo oa TimeStamp y Date importa!
            return SDF_yyyyMMdd.format(o);
        }
        if (o instanceof java.util.Date) { // el orden relativo oa TimeStamp y Date importa!
            return SDF_yyyyMMddHHmmssSSS.format(o);
        }
        if (o instanceof java.sql.Timestamp) {
            return SDF_yyyyMMddHHmmssSSS.format(o);
        }


       // return o;
    }*/

    public static void main(String[] args) throws Exception {

        log.debug(locales);
    }
}

