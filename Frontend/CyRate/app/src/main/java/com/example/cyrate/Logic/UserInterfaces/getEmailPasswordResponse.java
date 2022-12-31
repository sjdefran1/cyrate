package com.example.cyrate.Logic.UserInterfaces;

import com.example.cyrate.models.UserModel;

import java.util.HashMap;

public interface getEmailPasswordResponse {
    public void onSuccess(HashMap<String, String> usernamePassword);
    public void onError(String s);
}
