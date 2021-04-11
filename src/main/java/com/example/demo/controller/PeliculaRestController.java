package com.example.demo.controller;

import com.example.demo.service.PeliculaService;
import com.example.demo.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PeliculaRestController {

    @Autowired
    private PeliculaService service;

    @GetMapping("/buscarId")
    public Pelicula buscarId(@RequestParam(value = "id", defaultValue = "550") int id) {
        Pelicula pelicula = service.buscarPeliculaById(id);
        return pelicula;
    }

    @GetMapping("/buscarNombre")
    public Pelicula[] buscarNombre(@RequestParam(value = "name", defaultValue = "Godzilla") String name) {
        Pelicula[] peliculas = service.buscarPeliculaByName(name);
        return peliculas;
    }

    @GetMapping("/populares")
    public Pelicula[] populares() {
        Pelicula[] peliculas = service.peliculasPopulares();
        return peliculas;
    }
    @GetMapping("/genero")
    public Pelicula[] buscarGenero(@RequestParam(value = "genero", defaultValue = "comedy") String genero) {
        Pelicula[] peliculas = service.peliculasPorGenero(genero);
        return peliculas;
    }
    @GetMapping("/year")
    public Pelicula[] populares(@RequestParam(value = "year", defaultValue = "2021") int year) {
        Pelicula[] peliculas = service.peliculasByYear(year);
        return peliculas;
    }
}
