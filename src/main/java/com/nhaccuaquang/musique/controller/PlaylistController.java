package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/playlists")
@CrossOrigin("http://localhost:8081/")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseHandler getAllPlaylists() throws Exception {
        return playlistService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseHandler createPlaylist(@RequestBody @Valid Playlist playlist) throws Exception {
        return playlistService.save(playlist);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseHandler getPlaylistById(@PathVariable(name = "id") Long id) {
        return playlistService.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseHandler updatePlaylist(@PathVariable(name = "id") Long id, @RequestBody @Valid Playlist playlist) throws Exception {
        return playlistService.update(id, playlist);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseHandler deletePlaylist(@PathVariable(name = "id") Long id) throws Exception {
        return playlistService.delete(id);
    }

//    @RequestMapping(value = "{id}/add", method = RequestMethod.POST)
//    public ResponseHandler addSongToPlaylist(@PathVariable(name = "id") Long playlistId, @RequestParam Long songId) throws Exception {
//        return playlistService.addSongToPlaylist(playlistId, songId);
//    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public ResponseHandler removeSongFromPlaylist(@PathVariable(name = "id") Long playlistId, @RequestParam Long songId) throws Exception {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }

    @RequestMapping(value = "{id}/detail", method = RequestMethod.GET)
    public ResponseHandler getPlaylistDetail(@PathVariable(name = "id") Long id) {
        return playlistService.findDetailById(id);
    }


//    @RequestMapping(value = "{id}/detail2", method = RequestMethod.GET)
//    public Set<PlaylistDetail> getPlaylistDetail2(@PathVariable(name = "id") Long id) {
//        return playlistService.findDetailById2(id);
//    }
}
