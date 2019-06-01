package com.knapsack.repository;

import com.knapsack.model.Knapsack;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface KnapsackReactiveRepository extends ReactiveMongoRepository<Knapsack, String> {}
