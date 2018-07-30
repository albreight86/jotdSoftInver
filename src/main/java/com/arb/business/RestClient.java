/*
 * COPYRIGHT 2017.  Al Breight
 * File:     RestClient.java
 */
package com.arb.business;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestClient {

   private RestTemplate rest;
   private HttpHeaders headers;
   private HttpStatus status;

   public RestClient() { }

   @PostConstruct
   public void init() {
      this.rest = new RestTemplate();
      this.headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
   }

   public ResponseEntity get(String uri, Class entity) {
      HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
      ResponseEntity response = rest.getForEntity(uri, entity);
      setStatus(response.getStatusCode());
      return response;
   }

   public void setStatus(HttpStatus status) {
      this.status = status;
   }
}
