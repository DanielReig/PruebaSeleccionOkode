package com.example.demo.model;

public class BusquedaGenero
{
    private Genero[] genres;

    public Genero[] getGenres ()
    {
        return genres;
    }

    public void setGenres (Genero[] genres)
    {
        this.genres = genres;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [genres = "+genres+"]";
    }
}

