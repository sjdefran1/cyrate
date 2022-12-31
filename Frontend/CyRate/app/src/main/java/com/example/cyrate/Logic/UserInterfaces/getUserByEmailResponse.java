package com.example.cyrate.Logic.UserInterfaces;

import com.example.cyrate.models.UserModel;

import java.util.List;

public interface getUserByEmailResponse {
    public void onSuccess(UserModel userModel);
    public void onError(String s);

}
