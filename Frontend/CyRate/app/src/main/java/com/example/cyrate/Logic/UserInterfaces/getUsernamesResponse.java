package com.example.cyrate.Logic.UserInterfaces;

import com.example.cyrate.models.UserModel;

import java.util.HashSet;

public interface getUsernamesResponse {
    public void onSuccess(HashSet<String> usernamesSet);
    public void onError(String s);
}
