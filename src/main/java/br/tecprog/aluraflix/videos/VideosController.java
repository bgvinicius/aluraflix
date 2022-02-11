package br.tecprog.aluraflix.videos;

import br.tecprog.aluraflix.categories.CategoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@RestController
public class VideosController {

    private final VideoRepository videoRepository;
    private final CategoriesRepository categoriesRepository;

    public VideosController(VideoRepository videoRepository, CategoriesRepository categoriesRepository) {
        this.videoRepository = videoRepository;
        this.categoriesRepository = categoriesRepository;
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
    public ResponseEntity<Video> create(@RequestBody @Valid final VideoDTO video) {
        var category = categoriesRepository.findById(video.getCategoryId());

        if (category.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.status(HttpStatus.CREATED).body(videoRepository.save(video.toModel(category.get())));
    }

    @PutMapping("videos/{id}")
    @Transactional
    public ResponseEntity<Video> update(@PathVariable final Long id,
                                         @RequestBody @Valid final VideoDTO video) {
        var maybeVideo = videoRepository.findById(id);
        var maybeCategory = categoriesRepository.findById(video.getCategoryId());

        if (maybeVideo.isEmpty() || maybeCategory.isEmpty()) return ResponseEntity.notFound().build();

        var foundVideo = maybeVideo.get();
        var foundCategory = maybeCategory.get();

        if (foundVideo.getId() != video.getId()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(videoRepository.save(video.toModel(foundCategory)));
    }

    @DeleteMapping("videos/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        videoRepository.findById(id).ifPresent(e -> videoRepository.deleteById(id));
    }
}
