package com.example.aplicatibid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Search extends AppCompatActivity {
    private RecyclerView recyclerView_vertical;
    private List<Anunt> anunturi;
    private AnuntMeuAdapter adapter;
    private ImageView back_image;
    private String keyword;
    private TextView tvNuSuntRezultate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        View mainLayout = findViewById(R.id.main);
        recyclerView_vertical = findViewById(R.id.recyclerView_vertical);
        back_image = findViewById(R.id.id_return_button);
        keyword = getIntent().getStringExtra("QUERY_TEXT");
        tvNuSuntRezultate = findViewById(R.id.tvNuSuntRezultate);

        back_image.setOnClickListener(v -> {
            Intent intent = new Intent(Search.this, Pagina_Principala.class);
            startActivity(intent);
        });
        GridLayoutManager verticalLayoutManager = new GridLayoutManager(this, 1);
        recyclerView_vertical.setLayoutManager(verticalLayoutManager);

        recyclerView_vertical.setNestedScrollingEnabled(false);
        anunturi = new ArrayList<>();
        adapter = new AnuntMeuAdapter(anunturi);

        recyclerView_vertical.setAdapter(adapter);
        if (keyword != null && !keyword.isEmpty()) {
            cautaDateDeLaServer(keyword);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void cautaDateDeLaServer(String keyword){
        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);

        // Verifică dacă în interfață metoda se numește searchAnunt sau SearchAnunt
        anuntApiService.SearchAnunt(keyword).enqueue(new Callback<List<Anunt>>() {
            @Override
            public void onResponse(Call<List<Anunt>> call, Response<List<Anunt>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Anunt> listaPrimita = response.body();
                    anunturi.clear();
                    if (listaPrimita.isEmpty()) {

                        tvNuSuntRezultate.setVisibility(View.VISIBLE);
                        recyclerView_vertical.setVisibility(View.GONE);
                    } else {
                        tvNuSuntRezultate.setVisibility(View.GONE);
                        recyclerView_vertical.setVisibility(View.VISIBLE);
                        anunturi.addAll(listaPrimita);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Anunt>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }



}