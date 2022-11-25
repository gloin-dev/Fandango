package es.fandango.core.handler;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;


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
        return Mono.create(emitter -> {
            if (authenticationRequest.getIdentity().equals(user)
                    && authenticationRequest.getSecret().equals(password)) {
                emitter.success(AuthenticationResponse.success(user));
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}