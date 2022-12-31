package com.example.cy_rate.Comments;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.cy_rate.Review.Review;
import com.example.cy_rate.BusinessPosts.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);
    void deleteById(int id);

    List<Comment> findByReview(Review review);
    List<Comment> findByPost(Post post);

    @Transactional
    @Modifying
    @Query("DELETE Comment c where c.review = ?1")
    void deleteByReview(Review review);

    @Transactional
    @Modifying
    @Query("DELETE Comment c where c.post = ?1")
    void deleteByPost(Post post);
}