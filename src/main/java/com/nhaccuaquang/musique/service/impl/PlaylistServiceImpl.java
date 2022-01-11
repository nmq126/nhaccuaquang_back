package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.PlaylistDetailRepository;
import com.nhaccuaquang.musique.repository.PlaylistRepository;
import com.nhaccuaquang.musique.repository.SongRepository;
import com.nhaccuaquang.musique.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    SongRepository songRepository;
    @Autowired
    PlaylistDetailRepository playlistDetailRepository;

    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Playlist> playlists = playlistRepository.findAll();
            if (playlists.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Playlist list is empty")
                        .build(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("playlists", playlists)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity save(Playlist playlist) throws Exception {
        try {
            playlistRepository.save(playlist);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Playlist created successfully")
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("playlist", playlist.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Playlist id not found");
        }
    }

    @Override
    public ResponseEntity update(Long id, Playlist playlist) throws Exception {

        Optional<Playlist> playlistData = playlistRepository.findById(id);
        if (!playlistData.isPresent()) throw new NotFoundException("Playlist id not found");

        Playlist updatedPlaylist = playlistData.get();
        updatedPlaylist.setName(playlist.getName());
        updatedPlaylist.setThumbnail(playlist.getThumbnail());
        updatedPlaylist.setPrivate(playlist.isPrivate());
        try {
            playlistRepository.save(updatedPlaylist);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Playlist updated successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ResponseEntity delete(Long id) throws Exception {
        Optional<Playlist> playlistData = playlistRepository.findById(id);
        if (playlistData.isPresent()) {
            try {
                playlistRepository.deleteById(id);
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Playlist deleted succesfully")
                        .build(), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Playlist id not found");
    }

//    @Override
//    public ResponseHandler addSongToPlaylist(Long playlistId, Long songId) throws Exception {
//        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
//        if (!playlist.isPresent()) throw new NotFoundException("Playlist id not found");
//        Playlist realPlaylist = playlist.get();
//
//        Optional<Song> song = songRepository.findById(songId);
//        if (!song.isPresent()) throw new NotFoundException("Song id not found");
//        Song realSong = song.get();
//
//        Set<PlaylistDetail> playlistDetailList = realPlaylist.getPlaylistDetails();
//        for (PlaylistDetail playlistDetail:playlistDetailList){
//            if (playlistDetail.getId().getSongId() == songId){
//                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
//                        .withStatus(HttpStatus.BAD_REQUEST.value())
//                        .withMessage("Song already existed")
//                        .build();
//            }
//        }
//
//        PlaylistDetail playlistDetail = new PlaylistDetail();
//        playlistDetail.setId(new PlaylistDetailKey(songId, playlistId));
//        playlistDetail.setSong(realSong);
//        playlistDetail.setPlaylist(realPlaylist);
//        realPlaylist.addPlaylistDetailSet(playlistDetail);
//
//        try {
//            playlistRepository.save(realPlaylist);
//            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
//                    .withStatus(HttpStatus.OK.value())
//                    .withMessage("Song added successfully")
//                    .build();
//        } catch (Exception e) {
//            throw new Exception();
//        }
//    }

    @Override
    public ResponseEntity removeSongFromPlaylist(Long playlistId, Long songId) throws Exception {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (!playlist.isPresent()) throw new NotFoundException("Playlist id not found");
        Playlist realPlaylist = playlist.get();

        Optional<Song> song = songRepository.findById(songId);
        if (!song.isPresent()) throw new NotFoundException("Song id not found");

        Set<PlaylistDetail> playlistDetailList = realPlaylist.getPlaylistDetails();
        boolean existence = false;
        for (PlaylistDetail playlistDetail:playlistDetailList){
            if (playlistDetail.getId().getSongId() != songId) {
                continue;
            }
            existence = true;
        }
        if (!existence){
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("This song is not in the playlist")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        try {
            PlaylistDetailKey key = new PlaylistDetailKey(songId, playlistId);
            realPlaylist.removeSongFromPlaylist(playlistDetailRepository.findById(key).get());
            playlistDetailRepository.deleteById(key);
            return new
                    ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Song removed successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception();
        }
    }

    //find all playlist detail of a playlist by its id
    @Override
    public ResponseEntity findDetailById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("playlist", playlistDetailRepository.findByIdPlaylistId(id))
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Playlist id not found");
        }
    }

    @Override
    public ResponseEntity findDetailById1(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("playlist", playlistRepository.findPlaylistDetailByPlaylistId(id))
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Playlist id not found");
        }
    }


//    public Set<PlaylistDetail> findDetailById2(Long id){
//        return playlistRepository.findById(id).orElse(null).getPlaylistDetails();
//    }
}
