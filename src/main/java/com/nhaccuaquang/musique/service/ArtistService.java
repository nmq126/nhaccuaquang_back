package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Artist;
import org.springframework.http.ResponseEntity;

public interface ArtistService {
    ResponseEntity findAll() throws Exception;
    ResponseEntity findById(Long id) throws Exception;
    ResponseEntity save(Artist artist) throws Exception;
    ResponseEntity update(Long id, Artist artist) throws Exception;
}
