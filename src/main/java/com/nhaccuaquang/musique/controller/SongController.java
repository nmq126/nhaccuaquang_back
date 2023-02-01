package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.service.SongService;
import com.nhaccuaquang.musique.specification.SearchBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "${api.song.path}")
@CrossOrigin("http://localhost:8081/")
public class SongController {
    @Autowired
    SongService songService;

//    @PreAuthorize("hasAuthority('view:songs')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllSongs(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "status", defaultValue = "-1") int status,
            @RequestParam(value = "genre_id", defaultValue = "-1") Long genre_id,
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

//    @PreAuthorize("hasAuthority('view:songs')")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getSongById(@PathVariable(name = "id") Long id, Principal principal) {
        System.out.println(principal.toString());
        return songService.findById(id);
    }

//    @PreAuthorize("hasAuthority('create:songs')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createSong(@RequestBody @Valid Song song) throws Exception {
        return songService.save(song);
    }


    @PreAuthorize("hasAuthority('update:songs')")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateSong(@PathVariable(name = "id") Long id, @RequestBody @Valid Song song) throws Exception {
        return songService.update(id, song);
    }

    @PreAuthorize("hasAuthority('delete:songs')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSong(@PathVariable(name = "id") Long id) throws Exception {
        return songService.delete(id);
    }

    @PreAuthorize("hasAuthority('addToPlaylist:songs')")
    @RequestMapping(value = "{id}/add", method = RequestMethod.GET)
    public ResponseEntity addSongToPlaylist(@PathVariable(name = "id") Long songId, @RequestParam Long playlistId) throws Exception {
        return songService.addSongToPlaylist(playlistId, songId);
    }
}
