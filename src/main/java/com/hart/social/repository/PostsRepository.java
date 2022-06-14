package com.hart.social.repository;

import com.hart.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    List<Post> findPostsByUserId(Long id);


}