package com.example.sketchhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InscriptionActivity extends AppCompatActivity {
    private EditText editTextRegistreUsername;
    private EditText editTextRegistreMail;
    private EditText editTextRegistreAge;
    private EditText editTextRegistrePassword;
    private CheckBox checkBoxPremium;
    private Button buttonRegistre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        editTextRegistreUsername = findViewById(R.id.editTextRegistreUsername);
        editTextRegistreMail = findViewById(R.id.editTextRegistreMail);
        editTextRegistreAge = findViewById(R.id.editTextRegistreAge);
        editTextRegistrePassword = findViewById(R.id.editTextRegistrePassword);
        checkBoxPremium = findViewById(R.id.checkBoxPremium);
        buttonRegistre = findViewById(R.id.buttonLogin);

        buttonRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifie si tous les champs sont remplis
                if (editTextRegistreUsername.getText().toString().isEmpty() ||
                        editTextRegistreMail.getText().toString().isEmpty() ||
                        editTextRegistreAge.getText().toString().isEmpty() ||
                        editTextRegistrePassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Vérifie si le pseudo existe déjà dans la base de données
                String pseudo = editTextRegistreUsername.getText().toString();
                if (MyApp.getDatabase().isUsernameExists(pseudo)) {
                    // Affiche un message d'erreur si l'e-mail existe déjà
                    Toast.makeText(getApplicationContext(), "Ce surnom est déjà utilisé, veuillez en choisir un autre", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Vérifie si l'e-mail existe déjà dans la base de données
                String email = editTextRegistreMail.getText().toString();
                if (MyApp.getDatabase().isEmailExists(email)) {
                    // Affiche un message d'erreur si l'e-mail existe déjà
                    Toast.makeText(getApplicationContext(), "Cette adresse e-mail est déjà utilisée, veuillez en choisir une autre", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Vérifie si la case premium est cochée
                if (checkBoxPremium.isChecked()) {
                    // Si premium est coché, aller à l'activité de paiement
                    Intent intent = new Intent(InscriptionActivity.this, PaymentActivity.class);

                    // Passe les données à l'activité de récapitulation
                    intent.putExtra("username", editTextRegistreUsername.getText().toString());
                    intent.putExtra("mail", editTextRegistreMail.getText().toString());
                    intent.putExtra("age", editTextRegistreAge.getText().toString());
                    intent.putExtra("password", editTextRegistrePassword.getText().toString());
                    intent.putExtra("premium", checkBoxPremium.isChecked());
                    startActivity(intent);
                } else {
                    // Si premium n'est pas coché, aller à l'activité de récapitulation (AccountActivity)

                    // Crée un nouvel utilisateur avec les données des champs de saisie
                    User newUser = new User(
                            editTextRegistreUsername.getText().toString(),
                            editTextRegistreMail.getText().toString(),
                            Integer.parseInt(editTextRegistreAge.getText().toString()), // Convertir l'âge en entier
                            editTextRegistrePassword.getText().toString(),
                            checkBoxPremium.isChecked()
                    );

                    // Ajoute le nouvel utilisateur à la base de données
                    MyApp.getDatabase().addUser(newUser);

                    // Connecte l'utilisateur s'étant inscrit
                    MyApp.setUserConnected(newUser);

                    Intent intent = new Intent(InscriptionActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
            }
        });

        checkBoxPremium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // La case à cocher est cochée
                    Toast.makeText(getApplicationContext(), "La case est cochée", Toast.LENGTH_SHORT).show();
                } else {
                    // La case à cocher n'est pas cochée
                    Toast.makeText(getApplicationContext(), "La case n'est pas cochée", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
