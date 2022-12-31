package com.example.cyrate.Logic.BusinessInterfaces;

import com.example.cyrate.models.BusinessListCardModel;
import com.example.cyrate.models.BusinessPostCardModel;

import java.util.List;

public interface getBusinessPostsByID {
    public void onSuccess(List<BusinessPostCardModel> list);
    public void onError(String s);
}
