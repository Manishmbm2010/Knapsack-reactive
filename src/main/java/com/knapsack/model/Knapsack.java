package com.knapsack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
// @JsonView("knapsack")
public class Knapsack {

  @Id private String taskId;;
  private Problem problem;
  private Solution solution;
  private TimeStamp timestamps;
  private Status status;

  public Knapsack() {
    this.timestamps = new TimeStamp();
    solution = new Solution();
    this.status = Status.SUBMITTED;
  }

  public enum Status {
    SUBMITTED,
    STARTED,
    COMPLETED
  }
}
