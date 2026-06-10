package com.example.aplicatibid;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText getemail,getpass;
    Button Loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        TextView textView9 = findViewById(R.id.textView9);
        getemail = findViewById(R.id.editTextTextEmailAddress);
        getpass = findViewById(R.id.editTextTextPassword);
        Loginbutton = findViewById(R.id.button2);
        textView9.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        Loginbutton.setOnClickListener(v -> {
            Login();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void Login(){
        String email = getemail.getText().toString();
        String pass = getpass.getText().toString();

        User userObiect = new User();
        userObiect.setEmail(email);
        userObiect.setPassw(pass);

        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService anuntApiService = retrofitService.getRetrofit().create(AnuntApiService.class);

        anuntApiService.login(userObiect).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    Long iduser = user.getIduser();
                    Log.d("DEBUG_JSON", "ID primit: " + user.getIduser());
                    Log.d("DEBUG_JSON", "Username primit: " + user.getUsername());
                    if (iduser != null) {
                        SharedPreferences prefs = getSharedPreferences("Sesiune", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putLong("iduser", iduser);
                        editor.putString("username", user.getUsername());
                        editor.putString("email", user.getEmail());
                        editor.putString("roles", user.getRoles());

                        if (user.getBalance() != null) {
                            editor.putString("balance", user.getBalance().toString());
                        } else {
                            editor.putString("balance", "0.00");
                        }

                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, Pagina_Principala.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Eroare: ID invalid!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Datele sunt incorecte!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API_ERROR", "Eroare: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Eroare conexiune!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}