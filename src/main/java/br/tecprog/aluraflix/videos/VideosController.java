package br.tecprog.aluraflix.videos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class VideosController {

    private final VideoRepository videoRepository;

    public VideosController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @GetMapping("videos")
    public Iterable<Video> getAll() {
        return videoRepository.findAll();
    }

    @GetMapping("videos/{id}")
    public ResponseEntity<Video> getById(@PathVariable final Long id) {
        var video = videoRepository.findById(id);

        return video.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("videos")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Video create(@RequestBody @Valid final Video video) {
        return videoRepository.save(video);
    }

    @PutMapping("videos/{id}")
    @Transactional
    public ResponseEntity<Video> update(@PathVariable final Long id,
                                         @RequestBody @Valid final Video video) {
        var maybeVideo = videoRepository.findById(id);

        if (maybeVideo.isEmpty()) return ResponseEntity.notFound().build();

        var foundVideo = maybeVideo.get();

        if (foundVideo.getId() != video.getId()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(videoRepository.save(video));
    }

    @DeleteMapping("videos/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        videoRepository.findById(id).ifPresent(e -> videoRepository.deleteById(id));
    }
}
