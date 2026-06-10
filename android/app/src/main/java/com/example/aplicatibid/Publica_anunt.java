package com.example.aplicatibid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Publica_anunt extends AppCompatActivity {

    private ImageView returnbutton;
    private EditText gettitlu, getdescriere;
    private EditText getmin_price, getimage;
    private EditText getstart_time, getend_time;
    private Button publica;
    private DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_publica_anunt);

        returnbutton = findViewById(R.id.btnBackAdd);
        gettitlu = findViewById(R.id.titlu);
        getdescriere = findViewById(R.id.id_descriere);
        getmin_price = findViewById(R.id.id_pret);
        getstart_time = findViewById(R.id.id_data_i);
        getend_time = findViewById(R.id.id_data_s);
        getimage = findViewById(R.id.image);
        publica = findViewById(R.id.btnPublish);

        returnbutton.setOnClickListener(v -> finish());

        publica.setOnClickListener(v -> trimitereDateServer());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void trimitereDateServer() {
        try {
            String title = gettitlu.getText().toString();
            String description = getdescriere.getText().toString();
            String image = getimage.getText().toString();
            BigDecimal pret = new BigDecimal(getmin_price.getText().toString());

            String rawStart = getstart_time.getText().toString();
            String rawEnd = getend_time.getText().toString();

            LocalDateTime startDT = LocalDateTime.parse(rawStart, inputFormatter);
            LocalDateTime endDT = LocalDateTime.parse(rawEnd, inputFormatter);

            String data_start = startDT.format(isoFormatter);
            String data_end = endDT.format(isoFormatter);
            SharedPreferences prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);
            long idLogat = prefs.getLong("iduser", -1); // Iei ID-ul salvat la Login

            User userLogat = new User();
            userLogat.setIduser(idLogat);

            Anunt anuntnou = new Anunt(null, title, description, pret, pret, data_start, data_end, image, null, userLogat);

            RetrofitService retrofitService = new RetrofitService();
            AnuntApiService apiService = retrofitService.getRetrofit().create(AnuntApiService.class);

            apiService.PublicaAnunt(anuntnou).enqueue(new Callback<Anunt>() {
                @Override
                public void onResponse(Call<Anunt> call, Response<Anunt> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Publica_anunt.this, "Anunț publicat!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Publica_anunt.this, Pagina_Principala.class));
                        finish();
                    } else {
                        Toast.makeText(Publica_anunt.this, "Eroare server: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Anunt> call, Throwable t) {
                    Log.e("API_ERROR", "Eroare: " + t.getMessage());
                    Toast.makeText(Publica_anunt.this, "Eroare de rețea!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Verifică datele introduse (Format dată: yyyy-MM-dd HH:mm:ss)", Toast.LENGTH_LONG).show();
        }
    }
}