package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PeliculaService {

    @Value("${movieDatabaseApiKey}")
    private String apikey;

    WebClient.Builder webClientBuilder;

    private WebClient webClient;

    public PeliculaService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org").build();
    }

    public Pelicula nuevaPeli(){
        return new Pelicula(1,"123", "Peliculasa");
    }

    public Mono<Pelicula> buscarPeliculaById(int id){
        return webClient.get()
                .uri("/3/movie/550?api_key="+apikey)
                .retrieve()
                .bodyToMono(Pelicula.class);
    }

}
