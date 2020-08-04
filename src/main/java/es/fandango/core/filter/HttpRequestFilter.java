package es.fandango.core.filter;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

@Slf4j(topic = "HttpRequest")
@Filter("/api/**")
public class HttpRequestFilter implements HttpServerFilter {

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return trace(request)
                .switchMap(aBoolean -> chain.proceed(request))
                .doOnNext(res -> res
                        .getHeaders()
                        .add("X-Trace-Enabled", "true")
                );
    }

    Flowable<Boolean> trace(HttpRequest<?> request) {
        return Flowable.fromCallable(() -> {
            log.info("[{}] -> {}",
                    request.getMethod(),
                    request.getPath()
            );
            return true;
        }).subscribeOn(Schedulers.io());
    }
}