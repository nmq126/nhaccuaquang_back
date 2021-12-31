package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.PlaylistDetailRepository;
import com.nhaccuaquang.musique.repository.PlaylistRepository;
import com.nhaccuaquang.musique.repository.SongRepository;
import com.nhaccuaquang.musique.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseHandler findAll() throws Exception {
        try {
            List<Playlist> playlists = playlistRepository.findAll();
            if (playlists.isEmpty()) {
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Playlist list is empty")
                        .build();
            }
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("playlists", playlists)
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseHandler save(Playlist playlist) throws Exception {
        try {
            playlistRepository.save(playlist);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Playlist created successfully")
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseHandler findById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("playlist", playlist.get())
                    .build();
        } else {
            throw new NotFoundException("Playlist id not found");
        }
    }

    @Override
    public ResponseHandler update(Long id, Playlist playlist) throws Exception {

        Optional<Playlist> playlistData = playlistRepository.findById(id);
        if (!playlistData.isPresent()) throw new NotFoundException("Playlist id not found");

        Playlist updatedPlaylist = playlistData.get();
        updatedPlaylist.setName(playlist.getName());
        updatedPlaylist.setThumbnail(playlist.getThumbnail());
        updatedPlaylist.setPrivate(playlist.isPrivate());
        try {
            playlistRepository.save(updatedPlaylist);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Playlist updated successfully")
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ResponseHandler delete(Long id) throws Exception {
        Optional<Playlist> playlistData = playlistRepository.findById(id);
        if (playlistData.isPresent()) {
            try {
                playlistRepository.deleteById(id);
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Playlist deleted succesfully")
                        .build();
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
    public ResponseHandler removeSongFromPlaylist(Long playlistId, Long songId) throws Exception {
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
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("This song is not in the playlist")
                    .build();
        }

        try {
            PlaylistDetailKey key = new PlaylistDetailKey(songId, playlistId);
            playlistDetailRepository.deleteById(songId,playlistId);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Song removed successfully")
                    .build();
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception();
        }
    }

    //find all playlist detail of a playlist by its id
    @Override
    public ResponseHandler findDetailById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("playlist", playlistDetailRepository.findByIdPlaylistId(id))
                    .build();
        } else {
            throw new NotFoundException("Playlist id not found");
        }
    }


//    public Set<PlaylistDetail> findDetailById2(Long id){
//        return playlistRepository.findById(id).orElse(null).getPlaylistDetails();
//    }
}
