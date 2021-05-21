package org.srcom.security;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.srcom.rest.EjecutaPac;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// implementación para recuperar el usuario de la base de datos

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Map<String, User> roles = new HashMap<>();

    @PostConstruct
    public void init() {

        System.out.println("init_____________________________");
        /*roles.put("admin", new User("admin", "{noop}admin1", getAuthority("ROLE_ADMIN")));
        roles.put("user", new User("ama_admon", "{noop}inicio", getAuthority("ROLE_USER")));*/
    }


    @Override
    public UserDetails loadUserByUsername(String username) {

        roles.clear();
        roles.put("admin", new User("admin", "{noop}admin1", getAuthority("ROLE_ADMIN")));
        roles.put("user", new User("ama_admon", "{noop}inicio", getAuthority("ROLE_USER")));
        
        String usuEncontrado = null;

        HashMap usuario = new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS","F_QUERY",
                "Select cdusuari, nbnombre usunom, nbapell1 usuape, nbapell2 usuape2" +
                        " from ge_users where upper(cdusuari) = '" + username.toUpperCase() + "'");

        List<Map> valor = (List<Map>) usuario.get("RETURN");


        usuEncontrado = valor.get(0).get("CDUSUARI").toString();

        VaadinSession.getCurrent().setAttribute("usuario", usuEncontrado);
        VaadinSession.getCurrent().setAttribute("usunom", valor.get(0).get("USUNOM").toString());
        VaadinSession.getCurrent().setAttribute("usuape1", valor.get(0).get("USUAPE").toString());
        VaadinSession.getCurrent().setAttribute("usuape2", valor.get(0).get("USUAPE2").toString());

        return roles.get("user");
    }



    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}