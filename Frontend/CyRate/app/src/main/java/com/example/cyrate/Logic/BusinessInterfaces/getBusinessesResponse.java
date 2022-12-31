package com.example.cyrate.Logic.BusinessInterfaces;

import com.example.cyrate.models.BusinessListCardModel;


import java.util.List;

public interface getBusinessesResponse {
    public void onSuccess(List<BusinessListCardModel> list);
    public void onError(String s);

}
