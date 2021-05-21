package org.srcom.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@CommonsLog
public class EjecutaPac {

    @Autowired
    private Environment env;


    // llamada que devuelve los registros en un string, lo utilizamos para lLENAR LOS GRIDS.
    final String baseUrl = "http://localhost:8081/loomer/api/ejecutadb?paquete=PAC_SHWEB_LISTAS&funcion=F_QUERY";


    public String EjecutaPacStr(String funcion, String paquete, Object... parametros)  {

            return resultado(funcion, paquete, parametros);

    }

    // llamada que devuelve los registros en un hashmap
    public HashMap EjecutaPac(String funcion, String paquete, Object... parametros)  {


            HashMap<String, Object> retMap = new Gson().fromJson(
                    resultado(funcion, paquete, parametros), new TypeToken<HashMap<String, Object>>() {}.getType()
            );

            return retMap;

    }

    // llamada privada genérica que devuelve el resultado del sql
    private String resultado(String funcion, String paquete, Object... parametros) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(baseUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            String requestJson = "[{";


            for ( Object obj: parametros ) {
                //if obj.getClass().toString().equals()
                requestJson += "\"valor\" :  \"" + ((String)obj).toString()  +"\" ";
            }

            requestJson += "}]";



            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);


            String answer = restTemplate.postForObject(uri, entity, String.class);


            log.info(paquete + "__" + funcion + " :" + answer);

            return answer;


        } catch ( URISyntaxException e) {
            log.error(e.toString());

        }

        return null;

    }

}
