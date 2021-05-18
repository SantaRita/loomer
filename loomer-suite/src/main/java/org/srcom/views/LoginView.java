package org.srcom.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.srcom.rest.EjecutaPac;
import org.srcom.security.CustomRequestCache;

import java.net.URISyntaxException;


@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";

    private final LoginOverlay login = new LoginOverlay();

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, //
                     CustomRequestCache requestCache) throws URISyntaxException {



        LoginI18n i18n = createLoginI18n();
        login.setI18n(i18n);
        login.addForgotPasswordListener(e->{
            Notification.show("Forgot password not yet handled", 120, Notification.Position.TOP_CENTER);
        });
        login.setOpened(true);
        login.addLoginListener(e -> { //
            try {
                // try to authenticate with given credentials, should always return not null or throw an {@link AuthenticationException}
                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //

                // if authentication was successful we will update the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication); //
                login.close(); //
                UI.getCurrent().navigate(requestCache.resolveRedirectUrl()); //

            } catch (AuthenticationException ex) { //
                // show default error message
                // Note: You should not expose any detailed information here like "username is known but password is wrong"
                // as it weakens security.
                login.setError(true);
            }
        });

        add(login);

        Object a = null;


        //String salida = new EjecutaPac().EjecutaPac( "hola", "adios" );

    }

    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();

	    i18n.setHeader(new LoginI18n.Header());
	    i18n.setForm(new LoginI18n.Form());
	    i18n.setErrorMessage(new LoginI18n.ErrorMessage());


        // define all visible Strings to the values you want
        // this code is copied from above-linked example codes for Login
        // in a truly international application you would use i.e. `getTranslation(USERNAME)` instead of hardcoded string values. Make use of your I18nProvider
        i18n.getHeader().setTitle(getTranslation("nomapp"));
        i18n.getHeader().setDescription(getTranslation("version"));
        i18n.getForm().setUsername(getTranslation("usuario")); // this is the one you asked for.
        i18n.getForm().setTitle(getTranslation("accesocuenta"));
        i18n.getForm().setSubmit(getTranslation("entrar"));
        i18n.getForm().setPassword(getTranslation("contraseña"));
        i18n.getForm().setForgotPassword(getTranslation("olvidopwd"));
        i18n.getErrorMessage().setTitle(getTranslation("usupwdinvalido"));
        i18n.getErrorMessage()
                .setMessage(getTranslation("verificarusupwd"));
        /*i18n.setAdditionalInformation(
                "Caso necessite apresentar alguma informação extra para o usuário"
                        + " (como credenciais padrão), este é o lugar.");*/
        return i18n;
    }
}