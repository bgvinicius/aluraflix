package br.tecprog.aluraflix.unit.videos;

import br.tecprog.aluraflix.categories.Category;
import br.tecprog.aluraflix.videos.Video;
import br.tecprog.aluraflix.videos.VideoDTO;
import br.tecprog.aluraflix.videos.VideoRepository;
import br.tecprog.aluraflix.videos.VideosController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
        var video = new VideoDTO(videoId, "Title", "description", "http://test.com");
        final Video videoFromDb = video.toModel(new Category(1L, "a", "b"));
        Mockito.when(videoRepository.save(Mockito.any())).thenReturn(videoFromDb);
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoFromDb));

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Assertions.assertEquals(video, updateResponse.getBody());
    }

    @Test
    public void updateVideoWithInvalidInformation() {
        final long videoId = 1L;
        final VideoDTO video = new VideoDTO(videoId, " ", "  ", "http://test.com");

        Assertions.assertThrows(RuntimeException.class, () -> videosController.update(videoId, video));

        Mockito.verify(videoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void updateWithVideoNotExisting() {
        final long videoId = 1L;
        var video = new VideoDTO(videoId, "Title", "description", "http://test.com");
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, updateResponse.getStatusCode());
    }

    @Test
    public void updateChangingIdField() {
        final long videoId = 1L;

        // use a different id to update video
        var video = new VideoDTO(2L, "Title", "description", "http://test.com");
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(new Video(1L, "Title", "description", "http://test.com")));

        final ResponseEntity<Video> updateResponse = videosController.update(videoId, video);

        Mockito.verify(videoRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(videoRepository, Mockito.times(1)).findById(videoId);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode());
    }
}
