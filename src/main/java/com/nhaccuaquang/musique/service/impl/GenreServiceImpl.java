package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.GenreRepository;
import com.nhaccuaquang.musique.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Genre> genres = genreRepository.findAll();
            if (genres.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Genre list is empty")
                        .build(),HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("genres", genres)
                    .build(),HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("genre", genre.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Genre id not found");
        }
    }

    @Override
    public ResponseEntity save(Genre genre) throws Exception {
        try {
            genreRepository.save(genre);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Created successfully")
                    .build(),HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity update(Long id, Genre genre) throws Exception {

        Optional<Genre> genreData = genreRepository.findById(id);
        if (!genreData.isPresent()) throw new NotFoundException("Genre id not found");

        Genre updatedGenre = genreData.get();
        updatedGenre.setName(genre.getName());
        updatedGenre.setDescription(genre.getDescription());
        try {
            genreRepository.save(updatedGenre);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Updated successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ResponseEntity delete(Long id) throws Exception {
        Optional<Genre> genreData = genreRepository.findById(id);
        if (genreData.isPresent()) {
            try {
                genreRepository.deleteById(id);
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Deleted succesfully")
                        .build(), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Genre id not found");
    }
}
