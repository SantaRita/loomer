package org.srcom.loomerdb.servicerest;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HashMap ejecutadb(@RequestParam String paquete,
                             @RequestParam String funcion,
                             @RequestBody Object... parameters) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException {



        String classPath = "org.srcom.loomerdb.server.jdbc.objetosdb." + paquete.toUpperCase();

        Connection con = ds.getConnection();

        Class<?> cl = Class.forName(classPath);
        Constructor<?> cons = cl.getDeclaredConstructor(new Class[] {Connection.class});

        Object objetoDb = cons.newInstance(new Object[]{con});


        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        Object[] parameterInput = new Object [parameters.length];


        for (int i = 0; i < parameters.length; i++) {

            Map<String, Object> json = (Map<String, Object>) parameters[i];

            if (parameters[i].getClass().equals(Integer.class)) {
                parameterTypes[i] = BigDecimal.class;
                parameterInput[i] = new BigDecimal((Integer) json.get("valor"));
            } else {
                parameterTypes[i] = json.get("valor").getClass();
                parameterInput[i] = json.get("valor");
            }
        }


        Method method = objetoDb.getClass().getDeclaredMethod("ejecuta" + paquete.toUpperCase() + "__" + funcion.toUpperCase(),parameterTypes);
        log.info( method.getName());

        method.setAccessible(true);
        HashMap hm = (HashMap) method.invoke(objetoDb,parameterInput);

        con.close();
        return new HashMap(hm);
    }
}
