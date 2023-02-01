package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.Artist;
import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${api.artist.path}")
@CrossOrigin("${url.front.cors}")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllArtists() throws Exception {
        return artistService.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getArtistById(@PathVariable(name = "id") Long id) throws Exception {
        return artistService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createArtist(@RequestBody @Valid Artist artist) throws Exception {
        return artistService.save(artist);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateArtist(@PathVariable(name = "id") Long id, @RequestBody @Valid Artist artist) throws Exception {
        return artistService.update(id, artist);
    }
}
