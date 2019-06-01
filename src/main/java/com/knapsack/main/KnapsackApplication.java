package com.knapsack.main;

import com.knapsack.repository.KnapsackReactiveRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(
    scanBasePackages = {
      "com.knapsack.resource",
      "com.knapsack.service",
      "com.knapsack.config",
      "com.knapsack.model",
      "com.knapsack.repository",
      "com.knapsack.main"
    })
@EnableReactiveMongoRepositories(basePackageClasses = KnapsackReactiveRepository.class)
public class KnapsackApplication {

  public static void main(String[] args) {
    SpringApplication.run(KnapsackApplication.class, args);
  }
}
