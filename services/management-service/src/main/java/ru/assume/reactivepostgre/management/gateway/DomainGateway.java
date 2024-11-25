package ru.assume.reactivepostgre.management.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.management.model.ParameterDomain;
import ru.assume.reactivepostgre.management.model.QuestionDomainManagement;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.util.logging.Level.FINE;
import static reactor.core.publisher.Mono.empty;

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

    public Flux<ParameterDomain> getCurrentParameters() {
        URI url = UriComponentsBuilder.fromUriString("http://parameter-service:8080/parametersDomain").build().toUri();
        log.debug("getting current parameters");
        return webClient.get().uri(url).retrieve().bodyToFlux(ParameterDomain.class).log(log.getName(), FINE).onErrorResume(error -> empty());
    }

    public Mono<Void> persistTestParameters(Map<String, List<QuestionDomainManagement.Parameter>> parameters) {
        URI url = UriComponentsBuilder.fromUriString("http://test-service:8080/updateTestParameters").build().toUri();
        log.debug("persisting new test parameters");
        var body = BodyInserters.fromValue(parameters);
        return webClient.post().uri(url).body(body).retrieve().bodyToMono(Void.class).log(log.getName(), FINE).onErrorResume(error -> empty());
    }
}
