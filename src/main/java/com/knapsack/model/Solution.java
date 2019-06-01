package com.knapsack.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Solution {

  private List<Integer> items;
  private long time;
}
