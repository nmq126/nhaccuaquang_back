package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.GenreRepository;
import com.nhaccuaquang.musique.repository.PlaylistRepository;
import com.nhaccuaquang.musique.repository.SongRepository;
import com.nhaccuaquang.musique.service.SongService;
import com.nhaccuaquang.musique.specification.SearchBody;
import com.nhaccuaquang.musique.specification.SearchCriteria;
import com.nhaccuaquang.musique.specification.SongSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    PlaylistRepository playlistRepository;

    @Override
    public ResponseHandler findAll(SearchBody searchBody) throws Exception {

        Specification specification = Specification.where(null);
        //default status = true
        specification = specification.and(new SongSpecification(new SearchCriteria("status", ":", searchBody.isStatus())));
        if (searchBody.getName() != null && searchBody.getName().length() > 0){
            specification = specification.and(new SongSpecification(new SearchCriteria("name", ":", searchBody.getName())));
        }
        if (searchBody.getGenre_id() != null){
            specification = specification.and(new SongSpecification(new SearchCriteria("genre_id", ":", searchBody.getGenre_id())));
        }
        if (searchBody.getId() != null){
            specification = specification.and(new SongSpecification(new SearchCriteria("id", ":", searchBody.getId())));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (searchBody.getFrom() != null){
            try {
                Date date = sdf.parse(searchBody.getFrom());
                specification = specification.and(new SongSpecification(new SearchCriteria("releasedAt", ">", date)));
            }catch (Exception e){
            }
        }
        if (searchBody.getTo() != null){
            try {
                Date date = sdf.parse(searchBody.getTo());
                specification = specification.and(new SongSpecification(new SearchCriteria("releasedAt", "<", date)));
            }catch (ParseException e){
            }
        }
        try {
            Sort sort = Sort.by(Sort.Order.desc("id"));
            Pageable pageable = PageRequest.of(searchBody.getPage() - 1, searchBody.getLimit(), sort);
            Page<Song> songs = songRepository.findAll(specification, pageable);
//            List<Song> songs = songRepository.findAll(specification);
            if (songs.isEmpty()) {
                return  ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("No song satisfied")
                        .build();
            }
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("songs", songs)
                    .build();
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
    public ResponseHandler findById(Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("song", song.get())
                    .build();
        } else {
            throw new NotFoundException("Song id not found");
        }
    }

    @Override
    public ResponseHandler save(Song song) throws Exception {
        Optional<Genre> genreData = genreRepository.findById(song.getGenre_id());
        if (genreData.isPresent()) {
            try {
                songRepository.save(song);
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.CREATED.value())
                        .withMessage("Created successfully")
                        .build();
            } catch (Exception e) {
                throw new Exception();
            }
        } else throw new NotFoundException("Genre id not found");
    }

    @Override
    public ResponseHandler update(Long id, Song song) throws Exception {
        Optional<Song> songData = songRepository.findById(id);
        if (!songData.isPresent()) throw new NotFoundException("Song id not found");
        Optional<Genre> genreData = genreRepository.findById(song.getGenre_id());
        if (!genreData.isPresent()) throw new NotFoundException("Genre id not found");

        Song updatedSong = songData.get();
        updatedSong.setName(song.getName());
        updatedSong.setLink(song.getLink());
        updatedSong.setThumbnail(song.getThumbnail());
        updatedSong.setReleasedAt(song.getReleasedAt());
        updatedSong.setGenre_id(song.getGenre_id());
        updatedSong.setStatus(song.isStatus());
        try {
            songRepository.save(updatedSong);
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
        Optional<Song> songData = songRepository.findById(id);
        if (songData.isPresent()) {
            try {
                songRepository.deleteById(id);
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Deleted succesfully")
                        .build();
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Song id not found");
    }

    @Override
    public ResponseHandler addSongToPlaylist(Long playlistId, Long songId) throws Exception {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (!playlist.isPresent()) throw new NotFoundException("Playlist id not found");
        Playlist realPlaylist = playlist.get();

        Optional<Song> song = songRepository.findById(songId);
        if (!song.isPresent()) throw new NotFoundException("Song id not found");
        Song realSong = song.get();

        Set<PlaylistDetail> playlistDetailList = realPlaylist.getPlaylistDetails();
        for (PlaylistDetail playlistDetail:playlistDetailList){
            if (playlistDetail.getId().getSongId() == songId){
                return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withMessage("Song already existed")
                        .build();
            }
        }

        PlaylistDetail playlistDetail = new PlaylistDetail();
        playlistDetail.setId(new PlaylistDetailKey(songId, playlistId));
        playlistDetail.setSong(realSong);
        playlistDetail.setPlaylist(realPlaylist);
        realPlaylist.addPlaylistDetailSet(playlistDetail);

        try {
            playlistRepository.save(realPlaylist);
            return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Song added successfully")
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
