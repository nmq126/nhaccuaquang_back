package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.Album;
import com.nhaccuaquang.musique.entity.Artist;
import com.nhaccuaquang.musique.entity.Genre;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.AlbumRepository;
import com.nhaccuaquang.musique.repository.ArtistRepository;
import com.nhaccuaquang.musique.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Album> albums = albumRepository.findAll();
            if (albums.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Album list is empty")
                        .build(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Albums found")
                    .withData("artists", albums)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) throws Exception {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Album found")
                    .withData("Album", album.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Album id not found");
        }
    }

    @Override
    public ResponseEntity save(Album album) throws Exception {
        Optional<Artist> artistData = artistRepository.findById(album.getArtistId());
        if (artistData.isPresent()) {
            try {
                albumRepository.save(album);
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.CREATED.value())
                        .withMessage("Created successfully")
                        .build(),HttpStatus.CREATED);
            } catch (Exception e) {
                throw new Exception();
            }
        }else throw new NotFoundException("Artist id not found");
    }

    @Override
    public ResponseEntity update(Long id, Album album) throws Exception {
        Optional<Album> albumData = albumRepository.findById(id);
        if (!albumData.isPresent()) throw new NotFoundException("Album id not found");

        Album updatedAlbum = albumData.get();
        updatedAlbum.setName(album.getName());
        updatedAlbum.setImage(album.getImage());
        updatedAlbum.setDescription(album.getDescription());
        updatedAlbum.setReleasedAt(album.getReleasedAt());
        updatedAlbum.setArtistId(album.getArtistId());
        try {
            albumRepository.save(updatedAlbum);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Updated successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
