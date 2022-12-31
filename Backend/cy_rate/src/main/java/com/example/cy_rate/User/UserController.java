package com.example.cy_rate.User;

import java.util.List;
import com.example.cy_rate.User.*;
import javax.persistence.EntityNotFoundException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Gets all of the users from the remote db
     * 
     * @return all users in user table
     */
    @GetMapping(path = "/user/all")
    List<User> getAllUser()
    {
        return userRepo.findAll();
    }

    /**
     * Returns User from the User table
     * @param id
     * @return The User is found by the corresponding id
     */
    @GetMapping(path = "/user/byId/{id}")
    User getUserById(@PathVariable int id)
    {
        return userRepo.findById(id);
    }

    @GetMapping(path = "/user/byEmail/{email}")
    User getUserByEmail(@PathVariable String email)
    {
        return userRepo.findByEmail(email);
    }


    /**
     * Creates a User by passing json object
     * @param user
     * @return If the creation was succesfull or a failure
     */
    @PostMapping(path = "/user/create")
    String createUser(@RequestBody User user)
    {
        if(user == null)
        {
        return failure;
        }

        userRepo.save(user);
        return success;
    }


    /**
     * Deletes User through the id that its given in the User table
     * @param id
     * @return If the deletion was succesfull or a failure
     */
    @DeleteMapping(path = "/user/delete/{id}")
    String deleteUser(@PathVariable int id)
    {
        userRepo.deleteById(id);
        return success;
    }


    /**
     * Updates a User by its id
     * Have a new User object with updated values
     * @param id
     * @param use
     * @return 
     */
    @PutMapping(path = "/user/updateById/{id}")
    String updateUser(@PathVariable int id, @RequestBody User use)
    {
        try{
            User updateUser = userRepo.findById(id);

            updateUser.setUsername(use.getUsername());
            updateUser.setUserType(use.getUserType());
            updateUser.setRealName(use.getRealName());
            updateUser.setEmail(use.getEmail());
            updateUser.setUserPass(use.getUserPass());
            updateUser.setPhoneNum(use.getPhoneNum());
            updateUser.setDob(use.getDob());
            updateUser.setPhotoUrl(use.getPhotoUrl());
            userRepo.save(updateUser);

        }
        catch(Exception e){
            return "Not able to find User with id: " + id;
        }
        return success;
    }

    @PutMapping(path = "/user/resetPassword/{id}")
    String resetPassword(@PathVariable int id, @RequestBody User use)
    {
        User resetPassword = userRepo.findById(id);
        try{
        resetPassword.setUserPass(use.getUserPass());
        userRepo.save(resetPassword);
        }
        catch(Exception e)
        {
            return "Not able to find User with id: " + id;

        }
        return success;

    }
    
}
