package ru.assume.reactivepostgre.user_test.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;

import static java.util.logging.Level.FINE;
import static reactor.core.publisher.Flux.empty;

@Slf4j
@Component
public class TestServiceGateway {

    private final WebClient webClient;

    public TestServiceGateway(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public <T> Flux<T> getDomain(Class<T> type, String urlAddress, String userId) {
        URI url = UriComponentsBuilder.fromUriString(urlAddress).build(userId);
        log.debug("Getting domain from {}", url);

        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(type)
                .log(log.getName(), FINE)
                .onErrorResume(error -> empty());
    }

}
