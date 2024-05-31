package com.example.sketchhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextCardNumber;
    private EditText editTextExpiryMonth;
    private EditText editTextExpiryYear;
    private EditText editTextSecurityCode;
    private Button buttonPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editTextName = findViewById(R.id.editTextName);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextExpiryMonth = findViewById(R.id.editTextExpiryMonth);
        editTextExpiryYear = findViewById(R.id.editTextExpiryYear);
        editTextSecurityCode = findViewById(R.id.editTextSecurityCode);
        buttonPayment = findViewById(R.id.buttonPayment);

        // Récupére les données passées depuis l'activité d'inscription
        String username = getIntent().getStringExtra("username");
        String mail = getIntent().getStringExtra("mail");
        String age = getIntent().getStringExtra("age");
        String password = getIntent().getStringExtra("password");
        boolean premium = getIntent().getBooleanExtra("premium", false);

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifie que tous les champs sont remplis
                if (areFieldsFilled()) {
                    // Si tous les champs sont remplis, passe à l'activité AccountActivity

                    // Crée un nouvel utilisateur avec les données des champs de saisie
                    User newUser = new User(
                            username,
                            mail,
                            Integer.parseInt(age),
                            password,
                            premium
                    );

                    // Ajoute le nouvel utilisateur à la base de données
                    MyApp.getDatabase().addUser(newUser);

                    Intent intent = new Intent(PaymentActivity.this, AccountActivity.class);
                    startActivity(intent);
                } else {
                    // Si un champ est manquant, affiche un message d'erreur
                    Toast.makeText(PaymentActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean areFieldsFilled() {
        // Vérifie que tous les champs sont remplis
        return !editTextName.getText().toString().isEmpty() &&
                !editTextCardNumber.getText().toString().isEmpty() &&
                !editTextExpiryMonth.getText().toString().isEmpty() &&
                !editTextExpiryYear.getText().toString().isEmpty() &&
                !editTextSecurityCode.getText().toString().isEmpty();
    }

    // Méthode appelée lors du clic sur l'image du menu Home
    public void onHomeImageClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
