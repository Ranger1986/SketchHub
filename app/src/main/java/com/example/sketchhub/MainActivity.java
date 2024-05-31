package com.example.sketchhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    String[] items = {"Dessin 1", "Dessin 2", "Dessin 3"};
    ListView listView;
    private Button buttonDrawing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.drawing_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_list_view,R.id.textView,items);
        listView.setAdapter(arrayAdapter);
        buttonDrawing = findViewById(R.id.buttonDrawing);
        listView.setOnItemClickListener((parent, view, position, id) -> Log.i("LIST_VIEW", "Item is clicked @ position " + position));

        //Méthode appelée lors du clic sur le bouton "Dessiner"
        buttonDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getUserConnected() != null) {
                    // Ouvre l'activité de dessin
                    Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                    startActivity(intent);
                } else {
                    // Ouvre l'activité de connexion
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Méthode appelée lors du clic sur l'image de compte
    public void onAccountImageClicked(View view) {

        if (MyApp.getUserConnected() != null) {
            // Ouvre l'activité du compte
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else {
            // Ouvre l'activité de connexion
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}