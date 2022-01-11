package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/playlists")
@CrossOrigin("http://localhost:8081/")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @PreAuthorize("hasAuthority('view:playlists')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllPlaylists() throws Exception {
        return playlistService.findAll();
    }

    @PreAuthorize("hasAuthority('create:playlists')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createPlaylist(@RequestBody @Valid Playlist playlist) throws Exception {
        return playlistService.save(playlist);
    }

    @PreAuthorize("hasAuthority('view:playlists')")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getPlaylistById(@PathVariable(name = "id") Long id) {
        return playlistService.findById(id);
    }

    @PreAuthorize("hasAuthority('update:playlists')")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updatePlaylist(@PathVariable(name = "id") Long id, @RequestBody @Valid Playlist playlist) throws Exception {
        return playlistService.update(id, playlist);
    }

    @PreAuthorize("hasAuthority('delete:playlists')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlaylist(@PathVariable(name = "id") Long id) throws Exception {
        return playlistService.delete(id);
    }

//    @RequestMapping(value = "{id}/add", method = RequestMethod.POST)
//    public ResponseHandler addSongToPlaylist(@PathVariable(name = "id") Long playlistId, @RequestParam Long songId) throws Exception {
//        return playlistService.addSongToPlaylist(playlistId, songId);
//    }

    @PreAuthorize("hasAuthority('removeSong:playlists')") 
    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public ResponseEntity removeSongFromPlaylist(@PathVariable(name = "id") Long playlistId, @RequestParam Long songId) throws Exception {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }

    @PreAuthorize("hasAuthority('view:playlists')")
    @RequestMapping(value = "{id}/detail", method = RequestMethod.GET)
    public ResponseEntity getPlaylistDetail(@PathVariable(name = "id") Long id) {
        return playlistService.findDetailById(id);
    }

    @PreAuthorize("hasAuthority('view:playlists')")
    @RequestMapping(value = "{id}/detail1", method = RequestMethod.GET)
    public ResponseEntity getPlaylistDetail1(@PathVariable(name = "id") Long id) {
        return playlistService.findDetailById1(id);
    }


//    @RequestMapping(value = "{id}/detail2", method = RequestMethod.GET)
//    public Set<PlaylistDetail> getPlaylistDetail2(@PathVariable(name = "id") Long id) {
//        return playlistService.findDetailById2(id);
//    }
}
