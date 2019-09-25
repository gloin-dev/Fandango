package es.fandango;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Launcher App class
 */
@OpenAPIDefinition(
        info = @Info(
                title = "fandango",
                version = "latest",
                description = "Fandango Image API"
        )
)
public class MicronautApp {

  public static void main(String[] args) {
    Micronaut.run(MicronautApp.class);
  }
}