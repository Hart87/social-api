package com.hart.social.controller.v1;

import com.hart.social.model.Post;
import com.hart.social.repository.PostsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PostsController {

    private static final Logger log = LoggerFactory.getLogger(PostsController.class);

    @Autowired
    PostsRepository postsRepository;

    @GetMapping(value = "/posts/{id}", produces = {"application/json"})
    public ResponseEntity getAPost(@PathVariable String id) {

        Post post = postsRepository.findPostById(Long.valueOf(id));
        return new ResponseEntity(post, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/posts/{user_id}/all", produces = {"application/json"})
    public ResponseEntity getAllPosts(String user_id) {

        List<Post> posts = postsRepository.findPostsByUserId(Long.valueOf(4));
        return new ResponseEntity(posts, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/posts/all", produces = {"application/json"})
    public ResponseEntity getAllPosts() {

        List<Post> posts = postsRepository.findAll();
        return new ResponseEntity(posts, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/posts", produces = {"application/json"})
    public ResponseEntity createAUser(@RequestBody Post post) {

        post.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        post.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        postsRepository.save(post);
        return new ResponseEntity(post, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/posts/{id}", produces = {"application/json"})
    public ResponseEntity editAPost(@RequestBody Post post, @PathVariable String id) {

        post.setId(Long.valueOf(id));
        post.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        postsRepository.save(post);
        return new ResponseEntity(post, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/posts/{id}/like", produces = {"application/json"})
    public ResponseEntity incrementPostLikes(@PathVariable String id) {

        Post post = postsRepository.findPostById(Long.valueOf(id));
        post.setLikes(post.getLikes() + 1);
        post.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        postsRepository.save(post);
        return new ResponseEntity(post, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/posts/{id}/unlike", produces = {"application/json"})
    public ResponseEntity decrementPostLikes(@PathVariable String id) {

        Post post = postsRepository.findPostById(Long.valueOf(id));
        post.setLikes(post.getLikes() - 1);
        post.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        postsRepository.save(post);
        return new ResponseEntity(post, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/posts/{id}", produces = {"application/json"})
    public ResponseEntity deleteAPost(@PathVariable String id) {

        postsRepository.deleteById(Long.valueOf(id));
        return new ResponseEntity("Deleted",HttpStatus.ACCEPTED);
    }


}
