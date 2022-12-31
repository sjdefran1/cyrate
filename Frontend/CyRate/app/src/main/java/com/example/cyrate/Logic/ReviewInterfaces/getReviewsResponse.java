package com.example.cyrate.Logic.ReviewInterfaces;

import com.example.cyrate.models.ReviewListCardModel;

import java.util.List;

public interface getReviewsResponse {
    public void onSuccess(List<ReviewListCardModel> list);
    public void onError(String s);
}
