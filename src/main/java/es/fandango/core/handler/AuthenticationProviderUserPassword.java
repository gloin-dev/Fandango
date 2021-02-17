package es.fandango.core.handler;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.Maybe;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Singleton
@ConfigurationProperties("authentication")
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @NotBlank
    public String user;

    @NotBlank
    public String password;

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            HttpRequest<?> httpRequest,
            AuthenticationRequest<?, ?> authenticationRequest
    ) {
        return Maybe.<AuthenticationResponse>create(emitter -> {
            if (authenticationRequest
                    .getIdentity()
                    .equals(user)
                    &&
                    authenticationRequest
                            .getSecret()
                            .equals(password)
            ) {
                emitter.onSuccess(new UserDetails(user, new ArrayList<>()));
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }).toFlowable();
    }
}