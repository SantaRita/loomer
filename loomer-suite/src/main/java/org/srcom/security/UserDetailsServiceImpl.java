package org.srcom.security;

import java.net.URISyntaxException;
import java.util.*;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.srcom.loomerapi.Generico0;
import org.srcom.rest.EjecutaPac;

import javax.annotation.PostConstruct;

// implementación para recuperar el usuario de la base de datos

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Map<String, User> roles = new HashMap<>();

    @PostConstruct
    public void init() {

        roles.put("admin", new User("admin", "{noop}admin1", getAuthority("ROLE_ADMIN")));
        roles.put("user", new User("ama_admon", "{noop}inicio", getAuthority("ROLE_USER")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        String usuEncontrado = null;

        HashMap usuario = new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS","F_QUERY","Select cdusuari from ge_users where upper(cdusuari) = '" + username.toUpperCase() + "'");


        List<Map> valor = (List<Map>) usuario.get("RETURN");


        usuEncontrado = valor.get(0).get("CDUSUARI").toString();
        System.out.println("Elk valro es:" +  usuEncontrado );

        return roles.get(usuEncontrado);
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}