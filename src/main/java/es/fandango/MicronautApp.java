package es.fandango;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Launcher App class
 */
@OpenAPIDefinition(
        info = @Info(
                title = "fandango",
                version = "0.1-SNAPSHOT",
                description = "Fandango Image & Files API",
                license = @License(name = "GPL-2.0", url = "https://www.gnu.org/licenses/old-licenses/gpl-2.0.html"),
                contact = @Contact(name = "Ricardo", email = "rflores@gloin.es")
        )
)
public class MicronautApp {

    public static void main(String[] args) {
        Micronaut.run(MicronautApp.class);
    }

}