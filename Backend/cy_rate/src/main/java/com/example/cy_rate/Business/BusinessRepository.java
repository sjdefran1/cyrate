package com.example.cy_rate.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findById(int id);
    void deleteById(int id);

    List<Business> findAllByIsRestaurant(String trueValue);

    List<Business> findAllByIsCoffee(String trueValue);

    List<Business> findAllByIsBar(String trueValue);
}
