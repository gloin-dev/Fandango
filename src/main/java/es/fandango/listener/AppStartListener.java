package es.fandango.listener;

import io.micronaut.configuration.mongo.reactive.health.MongoHealthIndicator;
import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.management.health.indicator.HealthResult;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

@Slf4j
@Singleton
public class AppStartListener {

  @Inject
  MongoHealthIndicator mongoHealthIndicator;

  @EventListener
  @Async
  public void onApplicationEvent(ServiceStartedEvent event) {
    Publisher<HealthResult> result = mongoHealthIndicator.getResult();
    Single
        .fromPublisher(result)
        .subscribe(
            (healthResult, throwable) ->
                log.info("MongoDB status : {} {}", healthResult, throwable)
        ).dispose();
  }
}
