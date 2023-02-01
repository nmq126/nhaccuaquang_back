package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.Account;
import com.nhaccuaquang.musique.entity.Artist;
import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.ArtistRepository;
import com.nhaccuaquang.musique.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Artist> artists = artistRepository.findAll();
            if (artists.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Artist list is empty")
                        .build(),HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Artists found")
                    .withData("artists", artists)
                    .build(),HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) throws Exception {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Artist found")
                    .withData("Artist", artist.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Artist id not found");
        }
    }

    @Override
    public ResponseEntity save(Artist artist) throws Exception {
        try {
            artistRepository.save(artist);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Created successfully")
                    .build(),HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity update(Long id, Artist artist) throws Exception {
        Optional<Artist> artistData = artistRepository.findById(id);
        if (!artistData.isPresent()) throw new NotFoundException("Artist id not found");

        Artist updatedArtist = artistData.get();
        updatedArtist.setName(artist.getName());
        updatedArtist.setThumbnail(artist.getThumbnail());
        updatedArtist.setDescription(artist.getDescription());
        try {
            artistRepository.save(updatedArtist);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Updated successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
