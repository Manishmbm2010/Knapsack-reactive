package com.knapsack.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class TimeStamp {

  private Long submitted;
  private Long started;
  private Long completed;

  TimeStamp() {
    this.submitted = System.currentTimeMillis() / 1000L;
    this.started = null;
    this.completed = null;
  }
}
