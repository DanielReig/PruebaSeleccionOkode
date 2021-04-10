package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PeliculaService {

    @Value("${movieDatabaseApiKey}")
    private String apikey;

    WebClient.Builder webClientBuilder;

    private WebClient webClient;


    public PeliculaService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
    }

    public Pelicula buscarPeliculaById(int id){
        Mono<Pelicula> peliculaMono = webClient.get()
                .uri("/movie/{s}?api_key="+apikey,id)
                .retrieve()
                .bodyToMono(Pelicula.class);

        return peliculaMono.block();
    }

    public Pelicula[] buscarPeliculaByName(String name){
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/search/movie?api_key="+apikey+"&query={s}",name)
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }

    public Pelicula[] peliculasPopulares(){
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/discover/movie?api_key="+apikey+"&sort_by=popularity.desc")
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }
    public Pelicula[] peliculasPorGenero(String genero){
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/discover/movie?api_key="+apikey+"&with_genres=35&sort_by=popularity.desc")
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }
    public Pelicula[] peliculasByYear(int year){
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/discover/movie?api_key="+apikey+"&primary_release_year=2010&sort_by=popularity.desc")
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }

}
