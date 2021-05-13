package org.srcom.loomerdb.servicerest;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.srcom.loomerdb.server.jdbc.objetosdb.*;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@CommonsLog
@RestController
@RequestMapping("/loomer/api")
public class EjecutarSentencia {

    @Autowired
    DataSource ds;

    //@GetMapping("/ejecutadb")
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

        log.info("ejecuta" + paquete.toUpperCase() + "__" + funcion.toUpperCase());

        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        Object[] parameterInput = new Object [parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getClass().equals(Integer.class)) {
                parameterTypes[i] = BigDecimal.class;
                parameterInput[i] = new BigDecimal((Integer) parameters[i]);
            } else {
                parameterTypes[i] = parameters[i].getClass();
                parameterInput[i] = parameters[i];
            }
        }


        Method method = objetoDb.getClass().getDeclaredMethod("ejecuta" + paquete.toUpperCase() + "__" + funcion.toUpperCase(),parameterTypes);
        method.setAccessible(true);
        HashMap hm = (HashMap) method.invoke(objetoDb,parameterInput);

        con.close();
        return new HashMap(hm);
    }
}
