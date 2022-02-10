package br.tecprog.aluraflix.unit.videos;

import br.tecprog.aluraflix.videos.Video;
import br.tecprog.aluraflix.videos.VideoRepository;
import br.tecprog.aluraflix.videos.VideosController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SpringBootTest
public class ListVideoTests {
    @MockBean
    VideoRepository videoRepository;

    @Autowired
    VideosController videosController;

    String generateUrl(long index) {
        return "http://website" + index + ".com";
    }

    Iterable<Video> generateVideos() {
        return LongStream
                .range(1, 10)
                .mapToObj(value -> new Video
                        (value, "Title" + value, "Description" + value, generateUrl(value)))
                .collect(Collectors.toList());
    }

    @Test
    public void listVideosTest() {
        Iterable<Video> generatedVideos = generateVideos();

        Mockito.when(videoRepository.findAll()).thenReturn(generatedVideos);

        final Iterable<Video> result = videosController.getAll();

        Mockito.verify(videoRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(generatedVideos, result);
    }
}
