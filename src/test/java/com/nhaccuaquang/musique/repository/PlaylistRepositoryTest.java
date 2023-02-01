package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.dto.SongDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class PlaylistRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    @CsvSource
    void itShouldReturnAListOfSong() {
        //given

        //when
        List<SongDto> playlistDetails = playlistRepository.findPlaylistDetailByPlaylistId(1L);

        //then
        assertThat(playlistDetails);
    }
}