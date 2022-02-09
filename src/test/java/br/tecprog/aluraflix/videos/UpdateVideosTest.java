package br.tecprog.aluraflix.videos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
public class UpdateVideosTest {
    @MockBean
    VideoRepository videoRepository;

    @Autowired
    VideosController videosController;

    @Test
    public void updateVideoCorrectInformation() {
        final long videoId = 1L;
        final Video video = new Video(videoId, "Title", "description", "http://test.com");
        Mockito.when(videoRepository.save(video)).thenReturn(video);
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.times(1)).save(video);
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Assertions.assertEquals(video, updateResponse.getBody());
    }

    @Test
    public void updateVideoWithInvalidInformation() {
        final long videoId = 1L;
        final Video video = new Video(videoId, " ", "  ", "http://test.com");

        Assertions.assertThrows(RuntimeException.class, () -> videosController.update(videoId, video));

        Mockito.verify(videoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void updateWithVideoNotExisting() {
        final long videoId = 1L;
        final Video video = new Video(videoId, "Title", "description", "http://test.com");
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.never()).save(video);
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, updateResponse.getStatusCode());
    }

    @Test
    public void updateChangingIdField() {
        final long videoId = 1L;

        // use a different id to update video
        final Video video = new Video(2L, "Title", "description", "http://test.com");
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(new Video(1L, "Title", "description", "http://test.com")));

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.never()).save(video);
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode());
    }
}
