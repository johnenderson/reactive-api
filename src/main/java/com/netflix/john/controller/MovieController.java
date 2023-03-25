package com.netflix.john.controller;

import com.netflix.john.model.Movie;
import com.netflix.john.model.MovieEvent;
import com.netflix.john.repository.NetflixRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/netflix")
public class MovieController {

    private NetflixRepository repository;
    public MovieController(NetflixRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public Flux<Movie> getAllMovies() {return repository.findAll();}

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Movie>> getMovie(@PathVariable String id){
        return repository.findById(id)
                .map(movie -> ResponseEntity.ok(movie))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Movie>> updateMovie(@PathVariable(value="id")
                                                     String id,
                                                     @RequestBody Movie movie) {
        return repository.findById(id)
                .flatMap(existingMovie -> {
                    existingMovie.setMovieName(movie.getMovieName());
                    existingMovie.setMovieType(movie.getMovieType());
                    existingMovie.setPrincipalActor(movie.getPrincipalActor());
                    existingMovie.setCreated_at(movie.getCreated_at());
                    return repository.save(existingMovie);
                })
                .map(updateMovie -> ResponseEntity.ok(updateMovie))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Movie>> deleteMovie(@PathVariable(value = "id") String id) {
        return repository.findById(id)
                .flatMap(existingMovie ->
                        repository.delete(existingMovie)
                                .then(Mono.just(ResponseEntity.ok().<Movie>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllMoviesFromDatabase() {
        return repository.deleteAll();
    }

    @GetMapping(value = "/netflix-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> getNetflixEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(val ->
                        new MovieEvent(val, "Netlifx Events")
                );
    }
}
