package com.nhaccuaquang.musique.controller;


import com.nhaccuaquang.musique.entity.Album;
import com.nhaccuaquang.musique.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${api.album.path}")
@CrossOrigin("${url.front.cors}")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllAlbums() throws Exception {
        return albumService.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getAlbumById(@PathVariable(name = "id") Long id) throws Exception {
        return albumService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAlbum(@RequestBody @Valid Album album) throws Exception {
        return albumService.save(album);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAlbum(@PathVariable(name = "id") Long id, @RequestBody @Valid Album album) throws Exception {
        return albumService.update(id, album);
    }
}
