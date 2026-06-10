package com.example.aplicatibid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pagina_anunt extends AppCompatActivity {
    private SharedPreferences prefs;
    private final Handler refreshHandler = new Handler();
    private AnuntApiService anuntApiService;
    private Runnable refreshRunnable;
    private ImageView image, returnButton;
    private TextView title, descriere, LastBID, timp_ramas, pret_initial, Lider, sold;
    private EditText suma;
    private Button BidButton;
    private BigDecimal currentPrice;
    private CountDownTimer countDownTimer;
    private KonfettiView konfettiView;
    private TextView txtWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_anunt);
        konfettiView = findViewById(R.id.konfettiView);
        txtWinner = findViewById(R.id.txtWinner);
        RetrofitService retrofitService = new RetrofitService();
        anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);
        image = findViewById(R.id.headerImage);
        title = findViewById(R.id.id_titlu);
        descriere = findViewById(R.id.id_descriere_text);
        LastBID = findViewById(R.id.id_lastbid);
        timp_ramas = findViewById(R.id.id_timp_ramas);
        pret_initial = findViewById(R.id.id_ultima_licitatie);
        suma = findViewById(R.id.id_suma);
        BidButton = findViewById(R.id.id_bid_button);
        returnButton = findViewById(R.id.id_return_button);
        Lider = findViewById(R.id.Lider);
        sold = findViewById(R.id.id_sold);
        prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String titlu = extras.getString("title");
            String descriere_anunt = extras.getString("description");
            String LastBID_Price = extras.getString("actual_price");
            String imageURL = extras.getString("image");
            String pret_init = extras.getString("min_price");
            String nume_lider = extras.getString("lider");
            long endTimeMillis = extras.getLong("end_time_millis", 0);
            String sold_user = prefs.getString("balance", "0");

            currentPrice = new BigDecimal(LastBID_Price);

            title.setText(titlu);
            descriere.setText(descriere_anunt);
            LastBID.setText("Last BID: " + LastBID_Price + " RON");
            pret_initial.setText("Pretul initial: " + pret_init + " RON");
            Lider.setText("Lider: " + nume_lider);
            sold.setText("Sold:\n" + sold_user + "\nRON");
            Glide.with(this).load(imageURL).into(image);

            refreshRunnable = new Runnable() {
                @Override
                public void run() {
                    actualizeazDate();
                    refreshHandler.postDelayed(this, 1000);
                }
            };

            long diff = endTimeMillis - System.currentTimeMillis();
            if (diff > 0) {
                countDownTimer = new CountDownTimer(diff, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timp_ramas.setText("Timp Ramas: " + formatTimp(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        timp_ramas.setText("Timp Ramas: EXPIRAT");
                        BidButton.setEnabled(false);
                        String currentUser = prefs.getString("username", "");


                        String textLider = Lider.getText().toString();

                        if (textLider.contains(currentUser) && !currentUser.isEmpty()) {
                            startWinningCelebration();
                            Toast.makeText(Pagina_anunt.this, "Felicitări! Ai câștigat licitația!", Toast.LENGTH_LONG).show();
                        }
                    }
                }.start();
            } else {
                timp_ramas.setText("Timp Ramas: EXPIRAT");
                BidButton.setEnabled(false);
            }
        }

        returnButton.setOnClickListener(v -> finish());

        BidButton.setOnClickListener(v -> {
            String textSuma = suma.getText().toString().trim();
            if (textSuma.isEmpty()) {
                Toast.makeText(this, "Te rugăm să introduci o sumă!", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal suma_corecta = new BigDecimal(textSuma);
            BigDecimal balanta_actuala = new BigDecimal(prefs.getString("balance", "0"));

            if (suma_corecta.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(this, "Suma trebuie să fie mai mare decât 0!", Toast.LENGTH_SHORT).show();
            } else if (suma_corecta.compareTo(balanta_actuala) > 0) {
                Toast.makeText(this, "Sold insuficient!", Toast.LENGTH_SHORT).show();
            } else if (suma_corecta.compareTo(currentPrice) <= 0) {
                Toast.makeText(this, "Trebuie să licitezi mai mult decât prețul actual!", Toast.LENGTH_SHORT).show();
            } else {
                Bid(suma_corecta);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void animatieLiderApropiere() {

        TextView txtLider = findViewById(R.id.txtLiderAnim);
        if (txtLider == null) return;

        txtLider.animate().cancel();
        txtLider.setVisibility(View.VISIBLE);
        txtLider.setText("LIDER!");
        txtLider.setTextColor(0xFF4CAF50); // Verde


        txtLider.setAlpha(0f);
        txtLider.setScaleX(0.2f);
        txtLider.setScaleY(0.2f);


        txtLider.animate()
                .alpha(1f)
                .scaleX(1.6f)
                .scaleY(1.6f)
                .setDuration(500)
                .withEndAction(() -> {
                    txtLider.animate()
                            .alpha(0f)
                            .scaleX(2.0f)
                            .scaleY(2.0f)
                            .setStartDelay(400)
                            .setDuration(300)
                            .withEndAction(() -> txtLider.setVisibility(View.GONE))
                            .start();
                })
                .start();
    }
    protected void startWinningCelebration() {
        if (txtWinner == null || konfettiView == null) return;

        txtWinner.setText("FELICITĂRI!\nAI CÂȘTIGAT LICITAȚIA");
        txtWinner.setVisibility(View.VISIBLE);
        txtWinner.setAlpha(0f);
        txtWinner.animate().alpha(1f).setDuration(1000).start();

        // Configurație explozie scurtă și intensă
        EmitterConfig emitterConfig = new nl.dionsegijn.konfetti.core.emitter.Emitter(2000L, java.util.concurrent.TimeUnit.MILLISECONDS).max(100);

        // Poți lansa 3 explozii pentru un efect mai spectaculos
        konfettiView.start(new nl.dionsegijn.konfetti.core.PartyFactory(emitterConfig)
                .spread(360)
                .colors(java.util.Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb911f3))
                .position(0.2, 0.3).build());

        konfettiView.start(new nl.dionsegijn.konfetti.core.PartyFactory(emitterConfig)
                .spread(360)
                .colors(java.util.Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb911f3))
                .position(0.5, 0.3).build());

        konfettiView.start(new nl.dionsegijn.konfetti.core.PartyFactory(emitterConfig)
                .spread(360)
                .colors(java.util.Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb911f3))
                .position(0.8, 0.3).build());
        new android.os.Handler().postDelayed(() -> {
            if (txtWinner != null) {
                txtWinner.setVisibility(View.GONE);
            }
        }, 5000);
    }

    public void Bid(BigDecimal suma_corecta) {
        long iduser = prefs.getLong("iduser", -1);
        long id_anunt = getIntent().getLongExtra("id_anunt", -1);

        if (id_anunt == -1) {
            Toast.makeText(this, "Eroare: ID anunt invalid!", Toast.LENGTH_SHORT).show();
            return;
        }



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dataString = LocalDateTime.now().format(formatter);

        Bids actualbid = new Bids(suma_corecta, dataString, "ACTIV", id_anunt, iduser);

        anuntApiService.Bid(actualbid).enqueue(new Callback<Bids>() {
            @Override
            public void onResponse(Call<Bids> call, Response<Bids> response) {
                if (response.isSuccessful()) {
                    currentPrice = suma_corecta;
                    LastBID.setText("Last BID: " + currentPrice.toString() + " RON");


                    String liderusername = prefs.getString("username", "Anonim");
                    Lider.setText("Lider actual:\n" + liderusername);
                    animatieLiderApropiere();
                    suma.setText("");

                    actualizeazDate();
                    Toast.makeText(Pagina_anunt.this, "Succes!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Pagina_anunt.this, "Eroare server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bids> call, Throwable t) {
                Toast.makeText(Pagina_anunt.this, "Eroare retea!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizeazDate(){
        long id_anunt = getIntent().getLongExtra("id_anunt", -1);
        if(id_anunt == -1) return;

        anuntApiService.getAnuntbyID(id_anunt).enqueue(new Callback<Anunt>() {
            @Override
            public void onResponse(Call<Anunt> call, Response<Anunt> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Anunt anunt = response.body();
                    currentPrice = anunt.getActual_price();
                    LastBID.setText("Last BID: " + currentPrice.toString() + " RON");

                    if (anunt.getUsernameLider() != null) {
                        String liderusername = anunt.getUsernameLider();
                        Lider.setText("Lider actual:\n " + liderusername);
                    }
                }
            }

            @Override
            public void onFailure(Call<Anunt> call, Throwable t) {
            }
        });
        String myEmail = prefs.getString("email", "");
        anuntApiService.getBalance(myEmail).enqueue(new Callback<BigDecimal>() {
            @Override
            public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    BigDecimal balance = response.body();
                    prefs.edit().putString("balance", balance.toString()).apply();
                    sold.setText("Sold:\n" + balance.toString() + "RON");
                }
            }

            @Override
            public void onFailure(Call<BigDecimal> call, Throwable t) {
                Log.e("API_ERROR", "Eroare refresh sold");
            }


        });

    }

    private String formatTimp(long millis) {
        long secunde = millis / 1000;
        long minute = secunde / 60;
        long ore = minute / 60;
        long zile = ore / 24;
        if (zile > 0) {
            return String.format("%d zile %02d:%02d:%02d", zile, ore % 24, minute % 60, secunde % 60);
        }
        return String.format("%02d:%02d:%02d", ore, minute % 60, secunde % 60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshHandler.post(refreshRunnable);
    }

    @Override
    protected void onPause(){
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }
}