package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Song;
import org.springframework.http.ResponseEntity;

public interface GenreService {
    ResponseEntity findAll() throws Exception;
    ResponseEntity findById(Long id);
    ResponseEntity save(Genre genre) throws Exception;
    ResponseEntity update(Long id, Genre genre) throws Exception;
    ResponseEntity delete(Long id) throws Exception;
}
