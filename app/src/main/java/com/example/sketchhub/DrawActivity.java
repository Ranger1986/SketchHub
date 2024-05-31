package com.example.sketchhub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DrawActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private DrawingView drawingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawingView = findViewById(R.id.drawing_view);


        findViewById(R.id.button_pencil).setOnClickListener(v -> {
            drawingView.setColor(Color.BLACK);
        });

        findViewById(R.id.button_undo).setOnClickListener(v -> {
            drawingView.eraser();
        });

        findViewById(R.id.button_redo).setOnClickListener(v -> {
            drawingView.undoEraser();
        });


        findViewById(R.id.button_redColor).setOnClickListener(v -> {
            drawingView.setColor(Color.RED);
        });

        findViewById(R.id.button_yellowColor).setOnClickListener(v -> {
            drawingView.setColor(Color.YELLOW);
        });

        findViewById(R.id.button_greenColor).setOnClickListener(v -> {
            drawingView.setColor(Color.GREEN);
        });

        findViewById(R.id.button_pinkColor).setOnClickListener(v -> {
            drawingView.setColor(Color.MAGENTA);
        });

        findViewById(R.id.button_blueColor).setOnClickListener(v -> {
            drawingView.setColor(Color.BLUE);
        });

        findViewById(R.id.button_save).setOnClickListener(v -> {
            User userConnected = MyApp.getUserConnected();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DrawActivity.this, new String[]{
                                Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.READ_MEDIA_VIDEO,
                                Manifest.permission.READ_MEDIA_AUDIO}, PERMISSION_REQUEST_CODE);
                    } else {
                        drawingView.saveDrawing();
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DrawActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    } else {
                        drawingView.saveDrawing();
                    }
                }
            } else {
                drawingView.saveDrawing();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                drawingView.saveDrawing();
            }
        }
    }

    // Méthode appelée lors du clic sur l'image du menu Home
    public void onHomeImageClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
