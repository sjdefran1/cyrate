package com.example.cy_rate.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{
    User findById(int id);
    void deleteById(int id);
    User findByEmail(String email);
    User resetPassword(String newPassword);
 
}
