package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Song;

public interface GenreService {
    ResponseHandler findAll() throws Exception;
    ResponseHandler findById(Long id);
    ResponseHandler save(Genre genre) throws Exception;
    ResponseHandler update(Long id, Genre genre) throws Exception;
    ResponseHandler delete(Long id) throws Exception;
}
