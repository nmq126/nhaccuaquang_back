package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/genres")
@CrossOrigin("http://localhost:8081/")
public class GenreController {

    @Autowired
    GenreService genreService;

    @PreAuthorize("hasAuthority('view:genres')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllGenres() throws Exception {
        return genreService.findAll();
    }

    @PreAuthorize("hasAuthority('create:genres')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createGenre(@RequestBody @Valid Genre genre) throws Exception {
        return genreService.save(genre);
    }

    @PreAuthorize("hasAuthority('view:genres')")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getGenreById(@PathVariable(name = "id") Long id) {
        return genreService.findById(id);
    }

    @PreAuthorize("hasAuthority('update:genres')")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGenre(@PathVariable(name = "id") Long id, @RequestBody @Valid Genre genre) throws Exception {
        return genreService.update(id, genre);
    }

    @PreAuthorize("hasAuthority('delete:genres')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGenre(@PathVariable(name = "id") Long id) throws Exception {
        return genreService.delete(id);
    }
}
