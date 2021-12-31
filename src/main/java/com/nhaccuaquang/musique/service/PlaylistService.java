package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.ResponseHandler;

import java.util.Set;

public interface PlaylistService {
    ResponseHandler findAll() throws Exception;
    ResponseHandler findById(Long id);
    ResponseHandler save(Playlist playlist) throws Exception;
    ResponseHandler update(Long id, Playlist playlist) throws Exception;
    ResponseHandler delete(Long id) throws Exception;
    ResponseHandler findDetailById(Long id);
//    ResponseHandler addSongToPlaylist(Long playlistId, Long songId) throws Exception;
    ResponseHandler removeSongFromPlaylist(Long playlistId, Long songId) throws Exception;
}
