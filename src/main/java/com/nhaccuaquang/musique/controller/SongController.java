package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.service.PlaylistService;
import com.nhaccuaquang.musique.service.SongService;
import com.nhaccuaquang.musique.specification.SearchBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/songs")
@CrossOrigin("http://localhost:8081/")
public class SongController {
    @Autowired
    SongService songService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseHandler getAllSongs(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "status", defaultValue = "true") boolean status,
            @RequestParam(value = "genre_id", required = false) Long genre_id,
            @RequestParam(value = "id", required = false) Long id
    ) throws Exception {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                                    .withPage(page)
                                    .withLimit(limit)
                                    .withName(name)
                                    .withFrom(from)
                                    .withTo(to)
                                    .withStatus(status)
                                    .withGenre_id(genre_id)
                                    .withId(id)
                                    .build();
        return songService.findAll(searchBody);
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseHandler getAllSongs() throws Exception {
//        return songService.findAll();
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseHandler createSong(@RequestBody @Valid Song song) throws Exception {
        return songService.save(song);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseHandler getSongById(@PathVariable(name = "id") Long id) {
        return songService.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseHandler updateSong(@PathVariable(name = "id") Long id, @RequestBody @Valid Song song) throws Exception {
        return songService.update(id, song);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseHandler deleteSong(@PathVariable(name = "id") Long id) throws Exception {
        return songService.delete(id);
    }

    @RequestMapping(value = "{id}/add", method = RequestMethod.GET)
    public ResponseHandler addSongToPlaylist(@PathVariable(name = "id") Long songId, @RequestParam Long playlistId) throws Exception {
        return songService.addSongToPlaylist(playlistId, songId);
    }
}
