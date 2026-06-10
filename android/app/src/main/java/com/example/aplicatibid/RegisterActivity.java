package com.example.aplicatibid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button move;
    private EditText getmail, getpass, getusername;
    private RadioButton butVanzator, butCumparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        getmail = findViewById(R.id.editTextTextEmailAddress);
        getpass = findViewById(R.id.editTextTextPassword);
        getusername = findViewById(R.id.editTextTextEmailAddress3);
        butCumparator = findViewById(R.id.radioButton4);
        butVanzator = findViewById(R.id.radioButton3);
        move = findViewById(R.id.button2);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trimitereDateServer();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void trimitereDateServer() {
        String nameus = getusername.getText().toString();
        String mail = getmail.getText().toString();
        String pass = getpass.getText().toString();
        String roles = "";

        if (butVanzator.isChecked()) {
            roles = "VANZATOR";
        } else if (butCumparator.isChecked()) {
            roles = "CUMPARATOR";
        }

        User usernou = new User(null, nameus, mail, pass, roles, java.math.BigDecimal.ZERO);
        RetrofitService retrofitService = new RetrofitService();
        AnuntApiService userApiService = retrofitService.getRetrofit().create(AnuntApiService.class);

        userApiService.register(usernou).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Datele sunt incorecte!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.e("API_ERROR", "Eroare: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Eroare de rețea!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}