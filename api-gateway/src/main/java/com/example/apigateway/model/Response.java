package com.example.apigateway.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
  private int code;
  private String message;
}
