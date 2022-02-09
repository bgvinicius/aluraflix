package br.tecprog.aluraflix.videos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CreateVideosTests {
    @MockBean
    VideoRepository videoRepository;

    @Autowired
    VideosController videosController;

    final Video expectedVideo = new Video(1L, "Title", "description", "http://test.com");

    @Test
    @DisplayName("creating new video with correct information")
    public void createWithCorrectInformationTest() {

        Mockito.when(videoRepository.save(Mockito.any())).thenReturn(expectedVideo);

        final Video video = new Video(null, "Title", "description", "http://test.com");
        final Video createdVideo = videosController.create(video);

        Mockito.verify(videoRepository).save(video);
        Assertions.assertEquals(expectedVideo, createdVideo);
    }

}
