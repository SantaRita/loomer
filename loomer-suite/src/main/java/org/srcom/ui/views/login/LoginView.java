package org.srcom.ui.views.login;

import ch.carnet.kasparscherrer.LanguageSelect;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
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
import java.util.HashMap;
import java.util.Locale;


@CssImport(value = "./styles/components/myLoginViewStyles.css", themeFor = "vaadin-login-overlay-wrapper")
@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements LocaleChangeObserver {
    public static final String ROUTE = "login";

    LoginI18n i18n;
    private LanguageSelect langSelect;

    private final LoginOverlay login = new LoginOverlay();

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, //
                     CustomRequestCache requestCache) throws URISyntaxException {

        i18n = createLoginI18n();
        login.setI18n(i18n);
        login.addForgotPasswordListener(e->{
            Notification.show("Forgot password not yet handled", 120, Notification.Position.TOP_CENTER);
        });
        login.setOpened(true);
        login.addLoginListener(e -> { //
            try {
                // try to authenticate with given credentials, should always return not null or throw an {@link AuthenticationException}
;                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //



                // inicializamos variables en memoria

                  /*  HashMap pob =  new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS","F_GET_LSTPOBLACIONES","-1");
                    //List<Map> valor = (List<Map>) usuario.get("RETURN");
                    UI.getCurrent().getSession().setAttribute("poblaciones", pob);*/


                    HashMap div = new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS", "F_GET_DIVISAS");
                    UI.getCurrent().getSession().setAttribute("divisas",div);


                    String prv = new EjecutaPac().EjecutaPacLista("PAC_SHWEB_LISTAS", "F_GET_LSTPROVINCIAS", "-1");
                    UI.getCurrent().getSession().setAttribute("provincias",prv);


                    String data = new EjecutaPac().EjecutaPacLista("PAC_SHWEB_LISTAS", "F_GET_LSTCOMPANIAS", "-1" );
                    UI.getCurrent().getSession().setAttribute("companias",data);


                    HashMap ctr = new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS", "F_GET_LSTCONTRATOS", "-1");
                    UI.getCurrent().getSession().setAttribute("contratos",ctr);


                    HashMap pais=  new EjecutaPac().EjecutaPac("PAC_SHWEB_LISTAS", "F_GET_LSTPAISES");
                    UI.getCurrent().getSession().setAttribute("paises",pais);

                    String motivos = new EjecutaPac().EjecutaPacLista("PAC_SHWEB_LISTAS", "F_QUERY","SELECT ATRIBUTO, VALORC FROM SHWEB_VALORESFIJOS WHERE TIPO = 'TIPOALTAPGE' "
                                + " and ATRIBUTO <> 'APIREST' order by atributo");
                    UI.getCurrent().getSession().setAttribute("motivosalta",motivos);



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


        boolean useLanguageCookies = true;

        langSelect = new LanguageSelect(useLanguageCookies, new Locale("en"));


        Button button = new Button("Register");

        add(login, button);

        Object a = null;


        //String salida = new EjecutaPac().EjecutaPac( "hola", "adios" );

    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        i18n = createLoginI18n();
        login.setI18n(i18n);
        createLoginI18n();
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