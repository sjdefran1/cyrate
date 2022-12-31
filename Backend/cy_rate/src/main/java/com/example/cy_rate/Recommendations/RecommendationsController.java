package com.example.cy_rate.Recommendations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cy_rate.Business.Business;
import com.example.cy_rate.Business.BusinessRepository;
import com.example.cy_rate.User.User;
import com.example.cy_rate.User.UserRepository;

@RestController
public class RecommendationsController {
@Autowired
RecommendationsRepository recRepo;

@Autowired
UserRepository userRepo;

@Autowired
BusinessRepository businessRepo;

private String success = "{\"message\":\"success\"}";


@GetMapping(path = "/recommendations/all")
List<Recommendations> getAllRecommendations()
{
    return recRepo.findAll();
}

@GetMapping(path = "/recommendations/business/{bid}")
List<Recommendations> getRecommendationsByBusiness(@PathVariable int bid)
{
    Business b = businessRepo.findById(bid);
    return recRepo.findByBusiness(b);
}


    
}
