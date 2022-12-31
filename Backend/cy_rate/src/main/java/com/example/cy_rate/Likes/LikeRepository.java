package com.example.cy_rate.Likes;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.cy_rate.BusinessPosts.Post;
import com.example.cy_rate.Review.Review;

public interface LikeRepository extends JpaRepository<Like, Integer>{
    Like findByReview(Review review);
    Like findByPost(Post post);
    Like findById(int id);
    void deleteById(int id);


    @Transactional
    @Modifying
    @Query("DELETE Comment c where c.review = ?1")
    void deleteByReview(Review review);

    @Transactional
    @Modifying
    @Query("DELETE Comment c where c.post = ?1")
    void deleteByPost(Post post);
}