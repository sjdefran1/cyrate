package com.example.cyrate.Logic.BusinessInterfaces;

import com.example.cyrate.models.BusinessListCardModel;

import java.util.List;

public interface getBusinessByIDResponse {
    public void onSuccess(BusinessListCardModel business);
    public void onError(String s);
}
