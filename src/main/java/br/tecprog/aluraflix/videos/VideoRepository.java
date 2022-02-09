package br.tecprog.aluraflix.videos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, Long> {
}
