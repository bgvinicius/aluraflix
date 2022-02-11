package br.tecprog.aluraflix.videos;

import br.tecprog.aluraflix.categories.Category;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
  Iterable<Video> findVideosByCategory(Category category);
}
