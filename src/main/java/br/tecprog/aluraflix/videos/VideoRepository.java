package br.tecprog.aluraflix.videos;

import br.tecprog.aluraflix.categories.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
  Iterable<Video> findVideosByCategory(Category category);

  Iterable<Video> findByTitleIgnoreCaseContaining(String title);
}
