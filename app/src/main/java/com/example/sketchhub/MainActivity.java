package com.example.sketchhub;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    String[] items = {"Dessin 1", "Dessin 2", "Dessin 3"};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.drawing_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_list_view,R.id.textView,items);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> Log.i("LIST_VIEW", "Item is clicked @ position " + position));
    }
}