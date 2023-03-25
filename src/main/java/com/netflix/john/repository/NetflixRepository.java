package com.netflix.john.repository;

import com.netflix.john.model.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NetflixRepository extends ReactiveMongoRepository<Movie, String> {


}
