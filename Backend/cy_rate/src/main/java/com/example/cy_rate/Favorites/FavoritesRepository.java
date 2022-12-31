package com.example.cy_rate.Favorites;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cy_rate.Business.Business;
import com.example.cy_rate.User.User;


public interface FavoritesRepository extends JpaRepository<Favorites, Integer>{
    Favorites findById(int id);
    void deleteById(int id);
    List<Favorites> findByUser(User f);
    List<Favorites> findByBusiness(Business b);
    List<Favorites> findByFid(Favorites f);

}
