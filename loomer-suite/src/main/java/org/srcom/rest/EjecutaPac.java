package org.srcom.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.srcom.loomerapi.Generico0;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class EjecutaPac {

    @Autowired
    private Environment env;

    /*static String EjecutaPac(String hola, String adios) {
        return ("funciona");
    }*/


    public HashMap EjecutaPac(String funcion, String paquete, Object... parametros)  {

        try {
            System.out.println("kkkkllamada a ejecuta pac url ");
            RestTemplate restTemplate = new RestTemplate();
            final String baseUrl = "http://localhost:8081/loomer/api/ejecutadb?paquete=PAC_SHWEB_LISTAS&funcion=F_QUERY";
            URI uri = new URI(baseUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            String requestJson = "[{";


            for ( Object obj: parametros ) {
                //if obj.getClass().toString().equals()
                System.out.println("Clase:" + obj.getClass().toString() );
                requestJson += "\"valor\" :  \"" + ((String)obj).toString()  +"\" ";
            }

            requestJson += "}]";



            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

            String answer = restTemplate.postForObject(uri, entity, String.class);
            System.out.println(answer);

            HashMap<String, Object> retMap = new Gson().fromJson(
                    answer, new TypeToken<HashMap<String, Object>>() {}.getType()
            );


            System.out.println("Respuesta mapa:" +  retMap);

            return retMap;

        } catch ( URISyntaxException e) {

        }

        return null;

    }

}
