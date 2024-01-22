package com.moh.yehia.cloudwatch.controller;

import com.moh.yehia.cloudwatch.model.PostDTO;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PostController {
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    @GetMapping
    @Timed("posts.findPosts.time")
    @Counted("posts.findPosts.count")
    public PostDTO[] findPosts() {
        log.info("PostController :: findPosts :: start");
        ResponseEntity<PostDTO[]> response = restTemplate.exchange(API_URL, HttpMethod.GET, new HttpEntity<>(null), PostDTO[].class);
        log.info("response =>{}", response);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return new PostDTO[]{};
    }

    @GetMapping("/{postId}")
    @Timed("posts.findPostById.time")
    @Counted("posts.findPostById.count")
    public PostDTO findPostById(@PathVariable("postId") int postId) {
        log.info("PostController :: findPostById :: start");
        ResponseEntity<PostDTO> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(API_URL + "/{postId}").buildAndExpand(postId).toUri(), HttpMethod.GET, new HttpEntity<>(null), PostDTO.class);
        log.info("response =>{}", response);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return new PostDTO("000-000-000-000", "000-000-000-000", "No info available!", "No info available!");
    }

    @DeleteMapping("/{postId}")
    @Timed("posts.deletePostById.time")
    @Counted("posts.deletePostById.count")
    public void deletePostById(@PathVariable("postId") int postId) {
        log.info("PostController :: deletePostById :: start");
        ResponseEntity<Void> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(API_URL + "/{postId}").buildAndExpand(postId).toUri(), HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        log.info("response =>{}", response);
    }
}
