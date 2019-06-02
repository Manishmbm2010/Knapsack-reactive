package com.knapsack.service;

import com.knapsack.model.Knapsack;
import com.knapsack.model.Knapsack.Status;
import com.knapsack.model.Problem;
import com.knapsack.model.TimeStamp;
import com.knapsack.repository.KnapsackReactiveRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class KnapsackService {

  private Executor executor = Executors.newFixedThreadPool(10);
  private KnapsackReactiveRepository knapsackReactiveRepository;

  public KnapsackService(KnapsackReactiveRepository knapsackReactiveRepository) {
    this.knapsackReactiveRepository = knapsackReactiveRepository;
  }

  private static void accept(Knapsack x) {}

  public Mono<Knapsack> findById(String knapsackId) {
    return knapsackReactiveRepository.findById(knapsackId);
  }

  public Mono<Knapsack> saveKnapsackProblem(Knapsack knapsack) {
    knapsackReactiveRepository
        .save(knapsack)
        .single()
        .subscribe(
            x -> System.out.println(x.getTaskId() + " task status is " + x.getStatus()),
            Throwable::printStackTrace,
            () -> calculateSolutionForKnapsack(knapsack));
    return Mono.just(new Knapsack());
  }

  public void simpleSave(Knapsack knapsack) {
    knapsackReactiveRepository
        .save(knapsack)
        .single()
        .subscribe(
            x -> System.out.println(x.getTaskId() + " task status is " + x.getStatus()),
            Throwable::printStackTrace,
            () -> calculateSolutionForKnapsack(knapsack));
  }

  private void calculateSolutionForKnapsack(Knapsack knapsack) {
    knapsack.getTimestamps().setStarted(Instant.now().getEpochSecond());
    knapsack.setStatus(Status.STARTED);
    knapsackReactiveRepository
        .save(knapsack)
        .delayElement(Duration.ofSeconds(10))
        .subscribeOn(Schedulers.fromExecutor(executor))
        .subscribe(
            x -> System.out.println(x.getTaskId() + " task status is " + x.getStatus()),
            Throwable::printStackTrace,
            () -> {
              Problem problem = knapsack.getProblem();
              List<Integer> itemsToPutInKnapsack =
                  knapsackAlgo(
                      problem.getCapacity(),
                      problem.getWeights(),
                      problem.getValues(),
                      problem.getValues().length);
              knapsack.getSolution().setItems(itemsToPutInKnapsack);
              knapsack.getTimestamps().setCompleted(Instant.now().getEpochSecond());
              knapsack.setStatus(Status.COMPLETED);
              knapsack
                  .getSolution()
                  .setTime(timeTakenToSolveTheKnapsackProblem(knapsack.getTimestamps()));
              knapsackReactiveRepository
                  .save(knapsack)
                  .delayElement(Duration.ofSeconds(10))
                  .subscribe(
                      x -> System.out.println(x.getTaskId() + " task status is " + x.getStatus()),
                      Throwable::printStackTrace,
                      () -> System.out.println("Processing completed"));
            });
  }

  private Long timeTakenToSolveTheKnapsackProblem(TimeStamp timestamps) {
    return timestamps.getCompleted() - timestamps.getSubmitted();
  }

  private List<Integer> knapsackAlgo(int W, int wt[], int val[], int n) {
    int i, w;
    int K[][] = new int[n + 1][W + 1];
    // Build table K[][] in bottom up manner
    for (i = 0; i <= n; i++) {
      for (w = 0; w <= W; w++) {
        if (i == 0 || w == 0) K[i][w] = 0;
        else if (wt[i - 1] <= w) K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
        else K[i][w] = K[i - 1][w];
      }
    }
    List<Integer> items = new ArrayList<>();
    i = n;
    w = W;
    while (i > 0 && w > 0) {
      if (K[i][w] != K[i - 1][w]) {
        items.add(i - 1);
        w = w - wt[i - 1];
        i = i - 1;
      } else {
        i = i - 1;
      }
    }
    Collections.sort(items);
    return items;
  }

  private int max(int a, int b) {
    return (a > b) ? a : b;
  }
}
