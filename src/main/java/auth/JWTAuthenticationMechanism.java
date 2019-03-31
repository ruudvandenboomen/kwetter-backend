package auth;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@ApplicationScoped
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String BEARER = "Bearer ";

    @Inject
    IdentityStore identityStore;

    @Inject
    JWTStore jwtStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res, HttpMessageContext context) throws AuthenticationException {

        String authorizationHeader = req.getHeader(AUTHORIZATION);
        Credential credential = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length());

            try {
                credential = this.jwtStore.getCredential(token);
            } catch (Exception ex) {
                Logger.getLogger(JWTAuthenticationMechanism.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (credential != null) {
            return context.notifyContainerAboutLogin(this.identityStore.validate(credential));
        } else {
            if (req.getRequestURI().contains("login") || req.getRequestURI().contains("register") || req.getRequestURI().contains("openapi.json") || req.getRequestURI().contains("index.html") || req.getRequestURI().contains("swagger")) {
                return context.doNothing();
            }
            return context.responseUnauthorized();
        }
    }

}
