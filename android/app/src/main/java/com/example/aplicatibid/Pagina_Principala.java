package com.example.aplicatibid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pagina_Principala extends AppCompatActivity {
    private AnuntApiService anuntApiService;
    private RecyclerView recyclerView_vertical, recyclerView_horizontal;
    private List<Anunt> anunturi;
    private AnuntAdapter adapterVertical;
    private AnuntAdapter adapterHorizontal;
    private SearchView search;
    private Button Publicaanunt;
    private TextView username, username_sold;
    private ImageView username_logo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences prefs;
    private List<Anunt> anunturiVertical;
    private List<Anunt> anunturiHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_principala);

        RetrofitService retrofitService = new RetrofitService();
        anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);
        prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);

        recyclerView_vertical = findViewById(R.id.recyclerView_vertical);
        recyclerView_horizontal = findViewById(R.id.recyclerView_horizontal);
        Publicaanunt = findViewById(R.id.id_publica_anunt_button);
        username = findViewById(R.id.tvUsername_text);
        username_logo = findViewById(R.id.usernamelogo);
        username_sold = findViewById(R.id.tvSold);
        search = findViewById(R.id.search_view);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        if (search != null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query != null && !query.trim().isEmpty()) {
                        Intent intent = new Intent(Pagina_Principala.this, Search.class);
                        intent.putExtra("QUERY_TEXT", query);
                        startActivity(intent);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        swipeRefreshLayout.setOnRefreshListener(this::incarcaDateDeLaServer);

        Publicaanunt.setOnClickListener(v -> {
            Intent intent = new Intent(Pagina_Principala.this, Publica_anunt.class);
            startActivity(intent);
        });

        actualizeazaUiDinPrefs();

        username.setOnClickListener(v -> startActivity(new Intent(Pagina_Principala.this, MyUser.class)));
        username_logo.setOnClickListener(v -> startActivity(new Intent(Pagina_Principala.this, MyUser.class)));

        anunturiVertical = new ArrayList<>();
        anunturiHorizontal = new ArrayList<>();
        adapterVertical = new AnuntAdapter(anunturiVertical);
        adapterHorizontal = new AnuntAdapter(anunturiHorizontal);
        recyclerView_horizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_vertical.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView_horizontal.setAdapter(adapterHorizontal);
        recyclerView_vertical.setAdapter(adapterVertical);
        recyclerView_vertical.setNestedScrollingEnabled(false);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void actualizeazaUiDinPrefs() {
        String username_text = prefs.getString("username", "Utilizator");
        /*String balance_text = prefs.getString("balance", "0.00");*/
        username.setText(username_text);
    }

    private void incarcaDateDeLaServer() {
        apeluriFinalizate = 0;
        swipeRefreshLayout.setRefreshing(true);
        anuntApiService.getAnunturiPrimaPagina().enqueue(new Callback<List<Anunt>>() {
            @Override
            public void onResponse(Call<List<Anunt>> call, Response<List<Anunt>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Anunt> lista_primita = response.body();
                    anunturiVertical.clear();
                    anunturiVertical.addAll(lista_primita);
                    adapterVertical.notifyDataSetChanged();

                    List<Anunt> lista_filtrata = filtrareanunturi(lista_primita);
                    anunturiHorizontal.clear();
                    anunturiHorizontal.addAll(lista_filtrata);
                    adapterHorizontal.notifyDataSetChanged();
                    Log.d("API_SUCCES", "Datele au fost incarcate cu succes");
                }
                verificareFinalizareRefresh();
            }

            @Override
            public void onFailure(Call<List<Anunt>> call, Throwable t) {
                verificareFinalizareRefresh();
                Log.e("API_ERROR", "Eroare: " + t.getMessage());
            }
        });
        anuntApiService.getBalance(String.valueOf(prefs.getString("email", ""))).enqueue(new Callback<BigDecimal>() {
            @Override
            public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                swipeRefreshLayout.setRefreshing(true);
                if(response.isSuccessful() && response.body() != null)
                {
                    BigDecimal balance = response.body();
                    prefs.edit().putString("balance", balance.toString()).apply();
                    username_sold.setText(balance.toString() + " RON");
                }
                verificareFinalizareRefresh();
            }

            @Override
            public void onFailure(Call<BigDecimal> call, Throwable t) {
                verificareFinalizareRefresh();
            }
        });
    }

    private List<Anunt> filtrareanunturi(List<Anunt> lista) {
        List<Anunt> filtrate = new ArrayList<>();
        long treiOreInMillis = 3 * 60 * 60 * 1000;
        long acum = System.currentTimeMillis();

        for (Anunt a : lista) {
            long timpRamas = a.getEndTimeInMillis() - acum;

            if (timpRamas > 0 && timpRamas <= treiOreInMillis) {
                filtrate.add(a);
            }
        }
        return filtrate;

    }

    private int apeluriFinalizate = 0;
    private void verificareFinalizareRefresh() {
        apeluriFinalizate++;
        if (apeluriFinalizate >= 2) {
            swipeRefreshLayout.setRefreshing(false);
            apeluriFinalizate = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizeazaUiDinPrefs();
        incarcaDateDeLaServer();
    }
}