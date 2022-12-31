package com.example.cy_rate.Recommendations;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cy_rate.Business.Business;
import com.example.cy_rate.User.User;

public interface RecommendationsRepository extends JpaRepository<Recommendations, Integer>{
    Recommendations findById(int id);
    void deleteById(int id);
    List<Recommendations> findByUser(User r);
    List<Recommendations> findByBusiness(Business b);
    List<Recommendations> findByRecid(Recommendations r);
    
}
