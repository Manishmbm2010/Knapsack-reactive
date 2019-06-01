package com.knapsack.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Problem {

  private int capacity;
  private int weights[];
  private int values[];
}
