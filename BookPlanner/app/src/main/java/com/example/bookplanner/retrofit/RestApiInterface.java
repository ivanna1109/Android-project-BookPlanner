package com.example.bookplanner.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiInterface {

    String BASE_URL = "https://got-quotes.herokuapp.com/";

    @GET("quotes")
    Call<GoTQuote> getGoTQuote();
}
