package ru.assume.reactivepostgre.management.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

import static java.util.logging.Level.FINE;

@Slf4j
@Component
public class DomainGateway {

    private final WebClient webClient;

    @Autowired
    public DomainGateway(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public <T> Flux<T> createEntities(List<T> entities, String url, Class<T> entityClass) {
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
        log.debug("Persisting new entities to {}", url);
        var body = BodyInserters.fromValue(entities);

        return webClient.post()
                .uri(uri)
                .body(body)
                .retrieve()
                .bodyToFlux(entityClass)
                .log(log.getName(), FINE)
                .onErrorResume(error -> {
                    log.error("Error persisting entities: {}", error.getMessage());
                    return Flux.empty();
                });
    }
}
