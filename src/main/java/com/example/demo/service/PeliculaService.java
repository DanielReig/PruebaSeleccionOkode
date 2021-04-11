package com.example.demo.service;

import com.example.demo.model.BusquedaGenero;
import com.example.demo.model.BusquedaPelicula;
import com.example.demo.model.Genero;
import com.example.demo.model.Pelicula;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class PeliculaService {

    @Value("${movieDatabaseApiKey}")
    private String apikey;

    WebClient.Builder webClientBuilder;

    private WebClient webClient;
    Map<String,Genero> mapGeneros;


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
        mapGeneros = obtenerGeneros();
        int idGenero = mapGeneros.get(genero.toUpperCase()).getId();
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/discover/movie?api_key="+apikey+"&with_genres={i}&sort_by=popularity.desc", idGenero)
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }
    public Pelicula[] peliculasByYear(int year){
        Mono<BusquedaPelicula> busquedaPeliculaMono = webClient.get()
                .uri("/discover/movie?api_key="+apikey+"&primary_release_year={i}&sort_by=popularity.desc", year)
                .retrieve()
                .bodyToMono(BusquedaPelicula.class);
        Pelicula[] peliculas = busquedaPeliculaMono.block().getResults();
        return peliculas;
    }

    private Map<String,Genero> obtenerGeneros(){
        Mono<BusquedaGenero> busquedaGeneros = webClient.get()
                .uri("https://api.themoviedb.org/3/genre/movie/list?api_key="+apikey)
                .retrieve()
                .bodyToMono(BusquedaGenero.class);
        Genero[] generos = busquedaGeneros.block().getGenres();
        Map<String,Genero> map = new HashMap<>();
        for(int i = 0;i<generos.length;i++){
            map.put(generos[i].getName().toUpperCase(Locale.ROOT),generos[i]);
        }
        return map;
    }

}
