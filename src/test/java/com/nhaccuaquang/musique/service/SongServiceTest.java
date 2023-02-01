package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    private SongRepository songRepositoryTest;

    @Captor
    private ArgumentCaptor<Song> songArgumentCaptor;

    @Mock
    private SongService songService;

    @Test
    void canAddSong() {
        Song song = new Song();
        song.setName("Vengeance");
        song.setGenre_id(1L);
        song.setThumbnail("1");
        song.setLink("1");
        song.setStatus(1);
        song.setReleasedAt(LocalDate.now());

        //
        try {
            songService.save(song);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        songArgumentCaptor = ArgumentCaptor.forClass(Song.class);

        BDDMockito.then(songRepositoryTest).should().save(songArgumentCaptor.capture());
//        Mockito.verify(songRepositoryTest).save(songArgumentCaptor.capture());
        Song capturedSong = songArgumentCaptor.getValue();
        assertThat(capturedSong).isEqualTo(song);
    }
}