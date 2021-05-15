package org.srcom.loomerapi.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class EjecutaPac {

    @Value("${database.service.url}")
    static String urlDb;

    /*static String EjecutaPac(String hola, String adios) {
        return ("funciona");
    }*/


    public static String EjecutaPac ( String funcion, String paquete, Object ... parametros ) {

        System.out.println("llamada a ejecuta pac url " + urlDb);
        RestTemplate plantilla = new RestTemplate();
        String resultado = plantilla.getForObject(urlDb, String.class);
        System.out.println(resultado);
        return ("nos vamos");
    }

}
