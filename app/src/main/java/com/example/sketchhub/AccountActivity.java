package com.example.sketchhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Récupére l'utilisateur connecté à partir de MyApp
        User loggedInUser = MyApp.getUserConnected();

        if (loggedInUser != null) {
            // Affiche les informations de l'utilisateur dans les TextView correspondants
            TextView textViewUsername = findViewById(R.id.textViewUsername);
            TextView textViewMail = findViewById(R.id.textViewMail);
            TextView textViewAge = findViewById(R.id.textViewAge);
            TextView textViewPremium = findViewById(R.id.textViewPremium);

            textViewUsername.setText("Username: " + loggedInUser.getUsername());
            textViewMail.setText("Email: " + loggedInUser.getEmail());
            textViewAge.setText("Age: " + loggedInUser.getAge());

            // Affiche si le compte est premium ou non
            if (loggedInUser.isPremium()) {
                textViewPremium.setText("Compte Premium: Oui");
            } else {
                textViewPremium.setText("Compte Premium: Non");
            }
        } else {
            // Gére le cas où l'utilisateur connecté n'est pas trouvé
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            // Redirige l'utilisateur vers l'écran de connexion
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        // Bouton de déconnexion
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnexion de l'utilisateur en supprimant l'utilisateur connecté de MyApp
                MyApp.setUserConnected(null);

                // Redirige l'utilisateur vers la page d'accueil
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
