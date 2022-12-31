package com.example.cy_rate.Business;

import com.example.cy_rate.Review.Review;
import com.example.cy_rate.User.User;
import com.example.cy_rate.Review.ReviewRepository;
import com.example.cy_rate.User.UserRepository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BusinessContoller {
    @Autowired
    BusinessRepository businessRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ReviewRepository reviewRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public static final String TRUE_VALUE = "t";

//-------------- GET MAPPING ----------------//
    
    /**
     * Get all business from remote db
     * 
     * @return all businesses in business table
     */
    @GetMapping(path = "/business/all")
    List<Business> getAllBusiness()
    {
        return businessRepo.findAll();
    }

    /**
     * Returns specific business from Business table
     * @param id
     * @return business found by id given
     */
    @GetMapping(path = "/business/byId/{id}")
    Business getBusinessById(@PathVariable int id)
    {
        return businessRepo.findById(id);
    }

    @GetMapping(path = "/business/restaurants")
    List<Business> getAllRestaurants()
    {
        return businessRepo.findAllByIsRestaurant(TRUE_VALUE);
    }

    @GetMapping(path = "/business/coffee")
    List<Business> getAllCoffee()
    {
        return businessRepo.findAllByIsCoffee(TRUE_VALUE);
    }

    @GetMapping(path = "/business/bars")
    List<Business> getAllBars()
    {
        return businessRepo.findAllByIsBar(TRUE_VALUE);
    }

    
    /**
     * Gather all reviews for a specific business
     * 
     * @param bid business id to get reviews for
     * @return List<Review> 
     */
    // @GetMapping(path="/business/getReviews/byId/{bid}")
    // List<Review> getReviews(@PathVariable int bid)
    // {
    //     Business b = businessRepo.findById(bid);
    //     return b.getReviews();
    // }


//-------------- Post MAPPING ----------------//

    /**
     * Create a business by passing json obj
     * @param bus
     * @return success/failure str
     */
    @PostMapping(path = "/business/create")
    String createBusiness(@RequestBody Business bus)
    {
        if(bus == null)
        {
            return failure;
        }
        businessRepo.save(bus);
        
        return success;
    }
    
//-------------- Delete MAPPING ----------------//
    /**
     * Deletes business identified by id from business table in db
     * 
     * @param id
     * @return success/failure str
     */
    @DeleteMapping(path = "/business/delete/{id}")
    String deleteBusiness(@PathVariable int id)
    {
        businessRepo.deleteById(id);
        return success;
    }

//-------------- Put MAPPING ----------------//
    /**
     * Update specific business by id
     * Pass new Business obj as body w updated values
     * 
     * @param id
     * @param bus
     * @return Success Str / "Couldnt find business w id: id"
     */
    @PutMapping(path= "/business/updateById/{id}")
    String updateBusiness(@PathVariable int id, @RequestBody Business bus)
    {
        try{
        Business updateBusiness = businessRepo.findById(id);
        
        // copy @requestbody bus to specific business found by id
        // probably more efficient way of doing this, all i could find for now
        updateBusiness.setBusName(bus.getBusName());
        updateBusiness.setBusType(bus.getBusType());
        updateBusiness.setHours(bus.getHours());
        updateBusiness.setLocation(bus.getLocation());
        updateBusiness.setMenuLink(bus.getMenuLink());
        updateBusiness.setOwnerId(bus.getOwnerId());
        updateBusiness.setPhotoUrl(bus.getPhotoUrl());
        updateBusiness.setPriceGauge(bus.getPriceGauge());
        updateBusiness.setReviewCount(bus.getReviewCount());
        updateBusiness.setReviewSum(bus.getReviewSum());
        businessRepo.save(updateBusiness);
        }
        catch(Exception e){ 
            return "Could not find business with id: " + id;
        }
        return success; 
    }


}
