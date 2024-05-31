package com.example.sketchhub;
// LoginActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des vues
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonInscription = findViewById(R.id.buttonInscription);

        // Ajout du listener de clic pour le bouton de connexion
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération du nom d'utilisateur et du mot de passe saisis
                String usernameOrEmail = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Vérifie si le pseudo existe dans la base de données
                if (MyApp.getDatabase().isUsernameExists(usernameOrEmail)) {
                    // Récupération de l'utilisateur correspondant au pseudo
                    User user = MyApp.getDatabase().getUser(usernameOrEmail);
                    if (user != null) {
                        // Vérifie si le mot de passe est correct
                        if (user.getPassword().equals(password)) {
                            // Mot de passe correct, connecter l'utilisateur
                            MyApp.setUserConnected(user);

                            // Démarre AccountActivity
                            Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                            startActivity(intent);

                            // Termine LoginActivity après le démarrage de AccountActivity
                            finish();
                        } else {
                            // Mot de passe incorrect, affiche un message d'erreur
                            Toast.makeText(LoginActivity.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Utilisateur non trouvé dans la base de données, affiche un message d'erreur
                        Toast.makeText(LoginActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                    }
                }
                // Vérifie si l'adresse e-mail existe dans la base de données
                else if(MyApp.getDatabase().isEmailExists(usernameOrEmail)){
                    // Récupération de l'utilisateur correspondant à l'adresse e-mail
                    User user = MyApp.getDatabase().getUserByEmail(usernameOrEmail);
                    if (user != null) {
                        // Vérifie si le mot de passe est correct
                        if (user.getPassword().equals(password)) {
                            // Mot de passe correct, connecte l'utilisateur
                            MyApp.setUserConnected(user);

                            // Démarre AccountActivity
                            Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                            startActivity(intent);

                            // Termine LoginActivity après le démarrage de AccountActivity
                            finish();
                        } else {
                            // Mot de passe incorrect, affiche un message d'erreur
                            Toast.makeText(LoginActivity.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Utilisateur non trouvé dans la base de données, affiche un message d'erreur
                        Toast.makeText(LoginActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // Utilisateur ou adresse e-mail non trouvé dans la base de données, affiche un message d'erreur
                    Toast.makeText(LoginActivity.this, "Utilisateur ou adresse e-mail non trouvé", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ajout du listener de clic pour le bouton d'inscription
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité d'inscription
                Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    // Méthode appelée lors du clic sur l'image du menu Home
    public void onHomeImageClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

