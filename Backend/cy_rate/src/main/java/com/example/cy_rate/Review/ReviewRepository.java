package com.example.cy_rate.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.cy_rate.Business.Business;
import com.example.cy_rate.User.User;


public interface ReviewRepository extends JpaRepository<Review, Integer>{
    Review findById(int id);
    List<Review> findByUser(User u);
    List<Review> findByBusiness(Business b);
    void deleteById(int id);
}
