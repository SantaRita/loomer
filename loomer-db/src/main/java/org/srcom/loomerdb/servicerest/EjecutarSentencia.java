package org.srcom.loomerdb.servicerest;

import com.google.gson.Gson;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/loomer/api")
public class EjecutarSentencia {

    @Autowired
    DataSource ds;

    @RequestMapping(
            value = "/ejecutadb",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    @ResponseBody
    @Cacheable(value = "cache", condition="#funcion == 'F_GET_LSTPOBLACIONES'")
    public HashMap ejecutadb(@RequestParam String paquete,
                             @RequestParam String funcion,
                             @RequestBody Object... parameters) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException {

        Boolean tieneParametros  = true;

        System.out.println("Dentro ejecutadb" + paquete + " funcion " +  funcion + " parametros: " + parameters.length );

        String classPath = "org.srcom.loomerdb.server.jdbc.objetosdb." + paquete.toUpperCase();

        Connection con = ds.getConnection();

        Class<?> cl = Class.forName(classPath);
        Constructor<?> cons = cl.getDeclaredConstructor(new Class[] {Connection.class});

        Object objetoDb = cons.newInstance(new Object[]{con});


        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        Object[] parameterInput = new Object [parameters.length];



        //Leemos el json con los parametros

        for (int i = 0; i < parameters.length; i++) {

            Map<String, Object> json = (Map<String, Object>) parameters[i];

            System.out.println("Parametro" + json);

            System.out.println("Valor:" + json.get("valor"));

            if ( json.get("valor") == null ) {
                tieneParametros = false;
                break;
            }

            System.out.println("Tipo" + json.get("valor").getClass());
            if (json.get("valor").getClass().toString().equals("class java.lang.Integer")) {
                System.out.println("Es BigDecimal");
                parameterTypes[i] = BigDecimal.class;
                parameterInput[i] = new BigDecimal((Integer) json.get("valor"));
            } else {
                parameterTypes[i] = json.get("valor").getClass();
                parameterInput[i] = json.get("valor");
            }
        }

        Method method = null;
        HashMap hm;


        if ( tieneParametros )   {
            method = objetoDb.getClass().getDeclaredMethod("ejecuta" + paquete.toUpperCase() + "__" + funcion.toUpperCase(),parameterTypes);
            method.setAccessible(true);

            hm = (HashMap) method.invoke(objetoDb,parameterInput);


        } else {
            method = objetoDb.getClass().getDeclaredMethod("ejecuta" + paquete.toUpperCase() + "__" + funcion.toUpperCase());
            method.setAccessible(true);
            hm = (HashMap) method.invoke(objetoDb);

        }
        log.info( "Metodo " + method.toString());




        con.close();
        return new HashMap(hm);
    }
}
