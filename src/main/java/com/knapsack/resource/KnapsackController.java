package com.knapsack.resource;

import com.knapsack.model.Knapsack;
import com.knapsack.service.KnapsackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class KnapsackController {

  private KnapsackService knapsackService;

  public KnapsackController(KnapsackService knapsackService) {
    this.knapsackService = knapsackService;
  }

  @RequestMapping(path = "/knapsack/{knapsackId}", method = RequestMethod.GET)
  public Mono<Knapsack> getKnapsack(@PathVariable String knapsackId) {
    return knapsackService.findById(knapsackId);
  }

  @RequestMapping(path = "/knapsack", method = RequestMethod.POST)
  public Mono<Knapsack> submitKnapsackProblem(@RequestBody Knapsack knapsack) {
    return knapsackService.saveKnapsackProblem(knapsack);
  }

  @RequestMapping(path = "/knapsacks", method = RequestMethod.POST)
  public void submitKnapsackProblemSimple(@RequestBody Knapsack knapsack) {
    knapsackService.simpleSave(knapsack);
  }

  @RequestMapping(path = "/", method = RequestMethod.GET)
  public Mono<String> sayHello() {
    return Mono.just("Hello Reactive world");
  }
}
