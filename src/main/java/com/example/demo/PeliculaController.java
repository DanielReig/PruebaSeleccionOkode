package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    @GetMapping("/greeting")
    public Pelicula greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return service.nuevaPeli();
    }

    @GetMapping("/buscarId")
    public Mono<Pelicula> buscarId(@RequestParam(value = "name", defaultValue = "World") String name) {
        Mono<Pelicula> peliculaMono = service.buscarPeliculaById(2);
        Pelicula p = peliculaMono.block();
        return peliculaMono;
    }
}
