package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.dto.AlbumDto;
import com.nhaccuaquang.musique.entity.dto.ArtistDto;
import com.nhaccuaquang.musique.entity.dto.SongDetailDto;
import com.nhaccuaquang.musique.entity.dto.SongDto;
import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.*;
import com.nhaccuaquang.musique.service.SongService;
import com.nhaccuaquang.musique.specification.SearchBody;
import com.nhaccuaquang.musique.specification.SearchCriteria;
import com.nhaccuaquang.musique.specification.SongSpecification;
import com.nhaccuaquang.musique.util.DateTimeHandler;
import com.nhaccuaquang.musique.util.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.*;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    DateTimeHandler dateTimeHandler;

    @Override
    public ResponseEntity findAll(SearchBody searchBody) throws Exception {

        Specification specification = Specification.where(null);
        if (searchBody.getStatus() != -1) {
            specification = specification.and(new SongSpecification(new SearchCriteria("status", ":", searchBody.getStatus())));
        }
        if (searchBody.getName() != null && searchBody.getName().length() > 0) {
            specification = specification.and(new SongSpecification(new SearchCriteria("name", ":", searchBody.getName())));
        }
        if (searchBody.getGenre_id() != -1) {
            specification = specification.and(new SongSpecification(new SearchCriteria("genreId", ":", searchBody.getGenre_id())));
        }
        if (searchBody.getId() != null) {
            specification = specification.and(new SongSpecification(new SearchCriteria("id", ":", searchBody.getId())));
        }
        if (searchBody.getFrom() != null) {
            LocalDate date = dateTimeHandler.convertStringToLocalDate(searchBody.getFrom());
            specification = specification.and(new SongSpecification(new SearchCriteria("releasedAt", ">", date)));
        }
        if (searchBody.getTo() != null) {
            LocalDate date = dateTimeHandler.convertStringToLocalDate(searchBody.getTo());
            specification = specification.and(new SongSpecification(new SearchCriteria("releasedAt", "<", date)));
        }
        try {
            Sort sort = Sort.by(Sort.Order.desc("songId"));
            Pageable pageable = PageRequest.of(searchBody.getPage() - 1, searchBody.getLimit(), sort);
            Page<Song> songs = songRepository.findAll(specification, pageable);
            if (songs.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("No song satisfied")
                        .build(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("songs", songs)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }


//    @Override
//    public ResponseHandler findAll() throws Exception {
//        try {
//            List<Song> songs = songRepository.findAll();
//            if (songs.isEmpty()) {
//                return  ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
//                        .withStatus(HttpStatus.NO_CONTENT.value())
//                        .withMessage("Song list is empty")
//                        .build();
//            }
//            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
//                    .withStatus(HttpStatus.OK.value())
//                    .withMessage("Okela")
//                    .withData("songs", songs)
//                    .build();
//        } catch (Exception e) {
//            throw new Exception();
//        }
//    }

    @Override
    public ResponseEntity findById(Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            Optional<Album> album = albumRepository.findById(song.get().getAlbumId());
            List<ArtistDto> artists = new ArrayList<>();
            for (Artist artist: song.get().getArtists()) {
                Optional<Artist> artistExist = artistRepository.findById(artist.getArtistId());
                if (!artistExist.isPresent()) throw new NotFoundException("Artist id not found");
                artists.add(new ArtistDto(artistExist.get()));
            }
            if (!album.isPresent()) throw new NotFoundException("Album id not found");
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Song found")
                    .withData("song", new SongDetailDto(song.get()))
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Song id not found");
        }
    }

    @Override
    public ResponseEntity save(Song song) throws Exception {
        List<String> errorList = new ArrayList<>();
        if (!genreRepository.existsById(song.getGenreId()))
            errorList.add("Genre id " + song.getGenreId() + " not found");
        if (!albumRepository.existsById(song.getAlbumId()))
            errorList.add("Album id " + song.getAlbumId() + " not found");
        if (songRepository.existsByAlbumIdAndSongNumber(song.getAlbumId(), song.getSongNumber()))
            errorList.add("Duplicate song number this album");
        Set<Artist> artists = song.getArtists();
        Set<Long> ids = new HashSet<>();
        for (Artist artist : artists) {
            ids.add(artist.getArtistId());
            if (!artistRepository.existsById(artist.getArtistId()))
                errorList.add("Artist id" + artist.getArtistId() + " not found");
        }
        if (ids.size() != artists.size())
            errorList.add("Duplicate artist");
        if (errorList.size() > 0) {
            return new ResponseEntity(new RESTResponse.Error()
                    .setStatus(HttpStatus.BAD_REQUEST.value())
                    .setMessage("Bad Request!")
                    .addMultipleError(errorList)
                    .build(), HttpStatus.BAD_REQUEST);
        }
        try {
            song.addArtistToSong(artists);
            songRepository.save(song);
            return new ResponseEntity(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Created successfully")
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity update(Long id, Song song) throws Exception {
        Optional<Song> songData = songRepository.findById(id);
        if (!songData.isPresent()) throw new NotFoundException("Song id not found");
        Optional<Genre> genreData = genreRepository.findById(song.getGenreId());
        if (!genreData.isPresent()) throw new NotFoundException("Genre id not found");

        Song updatedSong = songData.get();
        updatedSong.setName(song.getName());
        updatedSong.setHref(song.getHref());
        updatedSong.setReleasedAt(song.getReleasedAt());
        updatedSong.setGenreId(song.getGenreId());
        updatedSong.setStatus(song.getStatus());
        try {
            songRepository.save(updatedSong);
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
        Optional<Song> songData = songRepository.findById(id);
        if (songData.isPresent()) {
            try {
                songRepository.deleteById(id);
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Deleted succesfully")
                        .build(), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Song id not found");
    }

    @Override
    public ResponseEntity addSongToPlaylist(Long playlistId, Long songId) throws Exception {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (!playlist.isPresent()) throw new NotFoundException("Playlist id not found");
        Playlist realPlaylist = playlist.get();

        Optional<Song> song = songRepository.findById(songId);
        if (!song.isPresent()) throw new NotFoundException("Song id not found");
        Song realSong = song.get();

        Set<PlaylistDetail> playlistDetailList = realPlaylist.getPlaylistDetails();
        for (PlaylistDetail playlistDetail : playlistDetailList) {
            if (playlistDetail.getId().getSongId() == songId) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withMessage("Song already existed")
                        .build(), HttpStatus.BAD_REQUEST);
            }
        }

        PlaylistDetail playlistDetail = new PlaylistDetail();
        playlistDetail.setId(new PlaylistDetailKey(songId, playlistId));
        playlistDetail.setSong(realSong);
        playlistDetail.setPlaylist(realPlaylist);
        realPlaylist.addPlaylistDetailSet(playlistDetail);
        realPlaylist.setUpdatedAt(new Date());


        try {
            playlistRepository.save(realPlaylist);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Song added successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
