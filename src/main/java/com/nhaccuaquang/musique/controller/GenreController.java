package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/genres")
@CrossOrigin("http://localhost:8081/")
public class GenreController {

    @Autowired
    GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseHandler getAllGenres() throws Exception {
        return genreService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseHandler createGenre(@RequestBody @Valid Genre genre) throws Exception {
        return genreService.save(genre);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseHandler getGenreById(@PathVariable(name = "id") Long id) {
        return genreService.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseHandler updateGenre(@PathVariable(name = "id") Long id, @RequestBody @Valid Genre genre) throws Exception {
        return genreService.update(id, genre);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseHandler deleteGenre(@PathVariable(name = "id") Long id) throws Exception {
        return genreService.delete(id);
    }
}
