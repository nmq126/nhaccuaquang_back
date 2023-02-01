package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Album;
import org.springframework.http.ResponseEntity;

public interface AlbumService {
    ResponseEntity findAll() throws Exception;
    ResponseEntity findById(Long id) throws Exception;
    ResponseEntity save(Album album) throws Exception;
    ResponseEntity update(Long id, Album album) throws Exception;
}
