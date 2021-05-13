package org.srcom.loomerapi;

import lombok.extern.apachecommons.CommonsLog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommonsLog
public class Service {



        private Generico0DTO getDTO(Object obj) {

            Generico0DTO dto = new Generico0DTO();
            List<Generico0.MapObject> listMapObjects = new ArrayList<Generico0.MapObject>();

            if (obj != null) {

                if (obj instanceof List<?>) {
                    for (Map m : (List<Map>) obj) {
                        Generico0.MapObject mapObject = new Generico0.MapObject();
                        mapObject.setMap(m);
                        listMapObjects.add(mapObject);
                    }
                } else if (obj instanceof Map) {

                    Generico0.MapObject mapObject = new Generico0.MapObject();
                    mapObject.setMap((Map) obj);
                    listMapObjects.add(mapObject);

                } else if (obj instanceof BigDecimal) {

                    Generico0.MapObject mapObject = new Generico0.MapObject();
                    Map map = new HashMap<String, BigDecimal>();
                    map.put("RETURN", obj);
                    mapObject.setMap(map);
                    listMapObjects.add(mapObject);
                }
            }
            dto.setMapObject(listMapObjects);
            return dto;
        }

        public Generico0 ejecutaPAC(String pac, String function, Object... parameters) {
            Map map = null;
            try {

                String methodName = "ejecuta" + pac + "__" + function;

                Class<?>[] parameterTypes = new Class<?>[parameters.length];
                Object[] parameterInput = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].getClass().equals(Integer.class)) {
                        parameterTypes[i] = BigDecimal.class;
                        parameterInput[i] = new BigDecimal((Integer) parameters[i]);
                    } else {
                        parameterTypes[i] = parameters[i].getClass();
                        parameterInput[i] = parameters[i];
                    }
                }


                //if (tratarMensajes) {
                //    Object obj = Util.tratarRETURNyMENSAJES(map);
                //    return getDTO(obj);
                //} else {
                    return getDTO(map);
                //}


            } catch (Exception e) {
                System.out.println("Error_Service:"+e);
                //log.error(e);
                return null;
            }
        }


}

