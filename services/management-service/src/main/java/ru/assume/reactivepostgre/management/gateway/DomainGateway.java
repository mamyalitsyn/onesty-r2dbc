package ru.assume.reactivepostgre.management.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.management.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.management.model.ParameterDomainManagement;
import ru.assume.reactivepostgre.management.model.RubricDomainManagement;

import java.net.URI;
import java.util.List;

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

    public Flux<CategoryDomainManagement> createCategories(List<CategoryDomainManagement> categories) {
        URI url = UriComponentsBuilder.fromUriString("http://category-service:8080/categoriesAdd").build().toUri();
        log.debug("persisting new categories");
        var body = BodyInserters.fromValue(categories);
        log.debug(body.toString());
        return webClient.post().uri(url).body(body).retrieve().bodyToFlux(CategoryDomainManagement.class).log(log.getName(), FINE).onErrorResume(error -> empty());
    }

    public Flux<RubricDomainManagement> createRubrics(List<RubricDomainManagement> rubrics) {
        URI url = UriComponentsBuilder.fromUriString("http://rubric-service:8080/rubricsAdd").build().toUri();
        log.debug("persisting new rubrics");
        var body = BodyInserters.fromValue(rubrics);
        log.debug(body.toString());
        return webClient.post().uri(url).body(body).retrieve().bodyToFlux(RubricDomainManagement.class).log(log.getName(), FINE).onErrorResume(error -> empty());
    }

    public Flux<ParameterDomainManagement> createParameters(List<ParameterDomainManagement> parameters) {
        URI url = UriComponentsBuilder.fromUriString("http://parameter-service:8080/parametersAdd").build().toUri();
        log.debug("persisting new parameters");
        var body = BodyInserters.fromValue(parameters);
        return webClient.post().uri(url).body(body).retrieve().bodyToFlux(ParameterDomainManagement.class).log(log.getName(), FINE).onErrorResume(error -> empty());
    }
}
