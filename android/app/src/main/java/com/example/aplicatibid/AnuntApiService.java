package com.example.aplicatibid;

import androidx.core.view.RoundedCornerCompat;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.processing.Generated;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnuntApiService {
    @GET("api/anunturi/pagina_principala")
    Call<List<Anunt>> getAnunturiPrimaPagina();

    @POST("api/users/login")
    Call<User> login(@Body User user);

    @POST("api/users/register")
    Call<User> register(@Body User user);

    @POST("api/users/add_balance/{balance}")
    Call<User> addBalance(@Body User user,@Path("balance") BigDecimal balance);

    @POST("api/users/withdraw/{balance}")
    Call<User> withdraw(@Body User user, @Path("balance") BigDecimal balance );

    @GET("api/anunturi/user/{id}")
    Call<List<Anunt>> getAnunturibyUserID(@Path("id") Long id);

    @POST("api/bids/bid")
    Call<Bids> Bid(@Body Bids bid);

    @GET("api/anunturi/search/{keyword}")
    Call<List<Anunt>> SearchAnunt(@Path("keyword") String keyword);

    @POST("api/anunturi/publica")
    Call<Anunt> PublicaAnunt(@Body Anunt anunt);

    @GET("api/anunturi/anunt/{id}")
    Call<Anunt> getAnuntbyID(@Path("id") Long id);

    @GET("api/bids/istoric_user/{iduser}")
    Call<List<Anunt>>  findAnunturileUndeAiLicitat(@Path("iduser") Long iduser);

    @GET("api/users/get_balance/{email}")
    Call<BigDecimal> getBalance(@Path("email") String email);

    @DELETE("api/users/delete/{id}")
    Call<Void> deleteUser(@Path("id") Long id);
}
