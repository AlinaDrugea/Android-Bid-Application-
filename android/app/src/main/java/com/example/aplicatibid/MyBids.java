package com.example.aplicatibid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBids extends AppCompatActivity {
    private RecyclerView recyclerView_vertical;
    private List<Anunt> anunturi;
    private AnuntMeuAdapter adapter;
    private ImageView back_image;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bids);
        recyclerView_vertical = findViewById(R.id.recyclerView_vertical);
        back_image = findViewById(R.id.id_return_button2);

        anunturi = new ArrayList<>();
        GridLayoutManager verticalLayoutManager = new GridLayoutManager(this, 1);
        recyclerView_vertical.setLayoutManager(verticalLayoutManager);

        recyclerView_vertical.setNestedScrollingEnabled(false);

        back_image.setOnClickListener(v->{
            finish();
        });

        adapter = new AnuntMeuAdapter(anunturi);
        recyclerView_vertical.setAdapter(adapter);
        incarcaDateDeLaServer();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void incarcaDateDeLaServer(){
        // Schimbă "UserPrefs" cu "Sesiune"
        SharedPreferences prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);
        long idLogat = prefs.getLong("iduser", -1L);
        String rolLogat = prefs.getString("roles", "");



        if (idLogat == -1L) {
            Log.e("API_ERROR", "User ID invalid în SharedPreferences");
            return;
        }


        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);
        if(rolLogat.equals("VANZATOR"))
        {
            anuntApiService.getAnunturibyUserID(idLogat).enqueue(new Callback<List<Anunt>>() {
                @Override
                public void onResponse(Call<List<Anunt>> call, Response<List<Anunt>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Anunt> anunturi_primite = response.body();

                        anunturi.clear();
                        anunturi.addAll(anunturi_primite);
                        adapter.notifyDataSetChanged();
                        Log.d("API_SUCCES", "Datele au fost incarcate cu succes. Nr: " + anunturi_primite.size());
                    } else {
                        Log.e("API_ERROR", "Cod de eroare: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<Anunt>> call, Throwable t) {
                    Log.e("API_ERROR", "Eroare retea: " + t.getMessage());
                }
            });


        }
        else if(rolLogat.equals("CUMPARATOR")){
            anuntApiService.findAnunturileUndeAiLicitat(idLogat).enqueue(new Callback<List<Anunt>>() {
                @Override
                public void onResponse(Call<List<Anunt>> call, Response<List<Anunt>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Anunt> anunturi_primite = response.body();
                        anunturi.clear();
                        anunturi.addAll(anunturi_primite);
                        adapter.notifyDataSetChanged();
                        Log.d("API_SUCCES", "Datele au fost incarcate cu succes. Nr: " + anunturi_primite.size());
                    } else {
                        Log.e("API_ERROR", "Cod de eroare: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<Anunt>> call, Throwable t) {
                    Log.e("API_ERROR", "Eroare retea: " + t.getMessage());
                }
            });


        }





    }
}