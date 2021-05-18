package org.srcom.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.srcom.views.LoginView;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {

    private SecurityUtils() {
        // Util methods only
    }

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //
        return authentication != null //
                && !(authentication instanceof AnonymousAuthenticationToken) //
                && authentication.isAuthenticated(); //
    }

    public static boolean isAccessGranted(Class<?> securedClass) {
        System.out.println("Dentro de isAccessGranted ");
        if (LoginView.class.equals(securedClass)) { //
            return true;
        }

        if (!isUserLoggedIn()) { //
            return false;
        }

        // Allow if no roles are required.
        Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {

            // [...]
            return true;
        }

        return true;
    }

}
