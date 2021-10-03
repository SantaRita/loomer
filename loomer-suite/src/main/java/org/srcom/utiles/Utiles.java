package org.srcom.utiles;

import org.json.JSONObject;

public class Utiles {

    public static String Vacio (JSONObject obj, String key ) {

        if(!obj.isNull(key)) {
            return obj.getString(key);
        }
        else
            return null;}

    public static Integer VacioInt (JSONObject obj, String key ) {

        if(!obj.isNull(key)) {
            return obj.getInt(key);
        }
        else
            return null;}
}
