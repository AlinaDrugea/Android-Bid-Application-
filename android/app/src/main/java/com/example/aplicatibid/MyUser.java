package com.example.aplicatibid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyUser extends AppCompatActivity {
    private SharedPreferences prefs;
    TextView sold, roles ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);
        setContentView(R.layout.activity_my_user);
        ImageView returnButton = findViewById(R.id.id_return_button);
        TextView LogOut = findViewById(R.id.Logout);
        TextView user = findViewById(R.id.tvUsernametext);
        sold = findViewById(R.id.tvSold);
        roles = findViewById(R.id.userrole);
        TextView mybidsbutton = findViewById(R.id.mybidsbutton);
        TextView depunere = findViewById(R.id.depuneresuma);
        TextView retragere = findViewById(R.id.retrageresuma);
        Button depunereButton = findViewById(R.id.depunerebutton);
        Button retragereButton = findViewById(R.id.retragerebutton);
        TextView deleteAccount = findViewById(R.id.deleteaccount);

        returnButton.setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mybidsbutton.setOnClickListener(v-> {
            Intent intent = new Intent(MyUser.this, MyBids.class);
            startActivity(intent);
        });

        deleteAccount.setOnClickListener(v->{
            long currentId = prefs.getLong("iduser", -1);
            if (currentId != -1) {
                DeleteAccount(currentId);
            } else {
                Toast.makeText(this, "Eroare: ID utilizator invalid!", Toast.LENGTH_SHORT).show();
            }
        });


        String username_text = prefs.getString("username","Utilizator");
        String user_balance = prefs.getString("balance","0.00");
        String role = prefs.getString("roles","").toLowerCase();
        sold.setText(user_balance + " RON");
        user.setText("Welcome,\n" + username_text);
        roles.setText("Rol: "+ role);

        depunereButton.setOnClickListener(v->{
            String text_suma = depunere.getText().toString().trim();
            if(!text_suma.isEmpty()) {
                BigDecimal suma = new BigDecimal(text_suma);
                if(suma.compareTo(BigDecimal.ZERO) > 0) {
                    Depunere(text_suma);
                    depunere.setText("");
                }
                else{
                    Toast.makeText(MyUser.this, "Suma depunere trebuie sa fie mai mare decat 0!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        retragereButton.setOnClickListener(v->{
            String text_suma = retragere.getText().toString();
            if(!text_suma.isEmpty()) {
                BigDecimal valoare = new BigDecimal(text_suma);
                if(valoare.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal sold_utilizator = new BigDecimal(prefs.getString("balance", "0.00"));
                    if(valoare.compareTo(sold_utilizator) > 0)
                    {
                        Toast.makeText(MyUser.this, "Sold insuficient!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Retragere(text_suma);
                        retragere.setText("");
                    }
                }
                else{
                    Toast.makeText(MyUser.this, "Suma retragere trebuie sa fie mai mare decat 0!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LogOut.setOnClickListener(v->{
            Logout();
        });
    }

    private void Depunere(String text_suma) {
      
        User user = new User();
        String email_utilizat = prefs.getString("email","");
        user.setEmail(email_utilizat);

        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);

        BigDecimal suma = new BigDecimal(text_suma);
        anuntApiService.addBalance(user,suma).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User useractualizat = response.body();
                    String noubalanta  = useractualizat.getBalance().toString();
                    prefs.edit().putString("balance", useractualizat.getBalance().toString()).apply();
                    sold.setText("Sold: " + useractualizat.getBalance().toString() + " RON");
                } else {
                    Toast.makeText(MyUser.this, "Eroare!", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onFailure(Call<User> call, Throwable t){
                Toast.makeText(MyUser.this, "Eroare!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Retragere(String text_suma){
      
        User user = new User();
        String email_utilizat = prefs.getString("email","");
        user.setEmail(email_utilizat);
        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);
        BigDecimal suma = new BigDecimal(text_suma);
        anuntApiService.withdraw(user,suma).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User useractualizat = response.body();
                    String noubalanta  = useractualizat.getBalance().toString();
                    prefs.edit().putString("balance", useractualizat.getBalance().toString()).apply();
                    sold.setText("Sold: " + useractualizat.getBalance().toString() + " RON");
                } else {
                    Toast.makeText(MyUser.this, "Eroare la retragere!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t){
                Toast.makeText(MyUser.this, "Eroare la retragere!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Logout(){
      
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(MyUser.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void DeleteAccount(Long iduser){
        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);
        anuntApiService.deleteUser(iduser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MyUser.this, "Contul a fost sters cu succes!", Toast.LENGTH_SHORT).show();
                    Logout();
                }
                else{
                    Toast.makeText(MyUser.this, "Eroare la stergere!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyUser.this, "Eroare la stergere!", Toast.LENGTH_SHORT).show();
            }

        });



    }
}