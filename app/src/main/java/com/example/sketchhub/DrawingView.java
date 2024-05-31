package com.example.sketchhub;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Stack;

public class DrawingView extends View {

    private Paint currentPaint;
    private Path currentPath;
    private Stack<DrawnPath> paths;
    private Stack<DrawnPath> erasedPaths; // Liste des chemins effacés

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        currentPaint = new Paint();
        currentPaint.setColor(Color.BLACK);
        currentPaint.setAntiAlias(true);
        currentPaint.setStrokeWidth(10);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);

        currentPath = new Path();
        paths = new Stack<>();
        erasedPaths = new Stack<>();

        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Dessine les chemins normaux
        for (DrawnPath drawnPath : paths) {
            canvas.drawPath(drawnPath.path, drawnPath.paint);
        }
        // Dessine le chemin actuel
        canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                paths.push(new DrawnPath(new Path(currentPath), new Paint(currentPaint)));
                currentPath.reset();
                break;
        }
        invalidate();
        return true;
    }

    public void setColor(int color) {
        currentPaint.setColor(color);
    }

    public void eraser() {
        if (!paths.isEmpty()) {
            DrawnPath lastPath = paths.pop(); // Retire le dernier chemin ajouté
            erasedPaths.push(lastPath); // Ajoute le chemin à la liste des chemins effacés
            invalidate(); // Redessine la vue
        }
    }

    public void undoEraser() {
        if (!erasedPaths.isEmpty()) {
            DrawnPath lastErasedPath = erasedPaths.pop(); // Retire le dernier chemin effacé
            paths.push(lastErasedPath); // Ajoute le chemin à la liste des chemins normaux
            invalidate(); // Redessine la vue
        }
    }

    private static class DrawnPath {
        Path path;
        Paint paint;

        DrawnPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    public void saveDrawing() {
        // Créer un bitmap de la taille de la vue
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas); // Dessine le contenu de la vue sur le canvas

        // Enregistrer le bitmap dans un fichier
        try {
            // Chemin du fichier de sauvegarde
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File file = new File(path, "drawing_" + System.currentTimeMillis() + ".png");
            OutputStream out = new FileOutputStream(file);

            // Sauvegarde le bitmap en format PNG
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            // Ajouter l'image à la galerie
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());

            getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Sauvegarde le chemin de l'image dans la base de données
            Database db = new Database(getContext());
            db.addDrawing(0, file.getAbsolutePath());

            // Afficher un toast indiquant que l'image a été enregistrée avec succès
            Toast.makeText(getContext(), "Image sauvegardée!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            // Afficher un toast indiquant qu'il y a eu une erreur lors de la sauvegarde
            Toast.makeText(getContext(), "Erreur lors de la sauvegarde de l'image.", Toast.LENGTH_SHORT).show();
        }
    }

}