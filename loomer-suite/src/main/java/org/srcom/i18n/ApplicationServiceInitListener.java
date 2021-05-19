package org.srcom.i18n;

import ch.carnet.kasparscherrer.LanguageSelect;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Locale;

import static java.lang.System.setProperty;

@Component
public class ApplicationServiceInitListener
    implements VaadinServiceInitListener {

  /*@Override
  public void serviceInit(ServiceInitEvent e) {
  }*/


  @Override
  public void serviceInit(ServiceInitEvent event) {

      LanguageSelect.readLanguageCookies(event);


      setProperty("vaadin.i18n.provider", VaadinI18NProvider.class.getName());

    event.getSource().addSessionInitListener(sessionInitEvent -> {
        final VaadinRequest request = sessionInitEvent.getRequest();
        final VaadinSession session = sessionInitEvent.getSession();

        // TODO: change the cookie name to the name that you actually use ;)
        try {
            Cookie localeCookie = Arrays.stream(request.getCookies()).
                    filter(c -> c.getName().equals("myPreferredLocale")).findFirst().orElse(null);
            if(localeCookie != null){
                session.setLocale(new Locale(localeCookie.getValue()));
            }
        }catch ( Exception e) {

        }


      });


    }


}
