package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.GenreRepository;
import com.nhaccuaquang.musique.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Override
    public ResponseHandler findAll() throws Exception {
        try {
            List<Genre> genres = genreRepository.findAll();
            if (genres.isEmpty()) {
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Genre list is empty")
                        .build();
            }
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("genres", genres)
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseHandler findById(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("genre", genre.get())
                    .build();
        } else {
            throw new NotFoundException("Genre id not found");
        }
    }

    @Override
    public ResponseHandler save(Genre genre) throws Exception {
        try {
            genreRepository.save(genre);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Created successfully")
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseHandler update(Long id, Genre genre) throws Exception {

        Optional<Genre> genreData = genreRepository.findById(id);
        if (!genreData.isPresent()) throw new NotFoundException("Genre id not found");

        Genre updatedGenre = genreData.get();
        updatedGenre.setName(genre.getName());
        updatedGenre.setDescription(genre.getDescription());
        try {
            genreRepository.save(updatedGenre);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Updated successfully")
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ResponseHandler delete(Long id) throws Exception {
        Optional<Genre> genreData = genreRepository.findById(id);
        if (genreData.isPresent()) {
            try {
                genreRepository.deleteById(id);
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Deleted succesfully")
                        .build();
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Genre id not found");
    }
}
