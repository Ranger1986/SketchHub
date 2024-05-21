package com.example.sketchhub;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    // Déclaration de la requête SQL de création de table
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE User (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "email TEXT," +
                    "age INTEGER," +
                    "password TEXT," +
                    "premium INTEGER" +
                    ")";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table lors de la première exécution de l'application
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données si nécessaire
        // Cela peut inclure la suppression de tables, l'ajout de colonnes, etc.
    }

    //Ajoute un utilisateur
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("age", user.getAge());
        values.put("password", user.getPassword());
        values.put("premium", user.isPremium() ? 1 : 0);

        // Insére une nouvelle ligne dans la table User
        db.insert("User", null, values);
        db.close();
    }

    //Récupère l'utilisateur
    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = null;

        try {
            // Sélectionne l'utilisateur avec le nom d'utilisateur donné
            cursor = db.query("User", null, "username=?", new String[]{username}, null, null, null);

            // Vérifie si le curseur a des données et déplacer le curseur à la première ligne
            if (cursor != null && cursor.moveToFirst()) {
                // Récupère les index des colonnes
                int columnIndexUsername = cursor.getColumnIndex("username");
                int columnIndexEmail = cursor.getColumnIndex("email");
                int columnIndexAge = cursor.getColumnIndex("age");
                int columnIndexPassword = cursor.getColumnIndex("password");
                int columnIndexPremium = cursor.getColumnIndex("premium");

                // Vérifie si les index des colonnes sont valides
                if (columnIndexUsername != -1 && columnIndexEmail != -1 && columnIndexAge != -1 && columnIndexPassword != -1 && columnIndexPremium != -1) {
                    // Récupére les données de l'utilisateur à partir du curseur
                    String userEmail = cursor.getString(columnIndexEmail);
                    int userAge = cursor.getInt(columnIndexAge);
                    String userPassword = cursor.getString(columnIndexPassword);
                    boolean userPremium = cursor.getInt(columnIndexPremium) == 1;

                    // Crée un objet User avec les données récupérées
                    user = new User(username, userEmail, userAge, userPassword, userPremium);
                }
            }
        } catch (Exception e) {
            Log.e("Database", "Error retrieving user: " + e.getMessage());
        } finally {
            // Ferme le curseur et la base de données
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return user;
    }

    //Supprime un utilisateur
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Supprime l'utilisateur avec le nom d'utilisateur donné
            db.delete("User", "username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("Database", "Error deleting user: " + e.getMessage());
        } finally {
            // Ferme la base de données
            db.close();
        }
    }

    // Vérifie si un utilisateur avec le même nom d'utilisateur existe déjà dans la base de données
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            // Exécute une requête pour vérifier si un utilisateur avec le même nom d'utilisateur existe déjà
            cursor = db.query("User", new String[]{"username"}, "username=?", new String[]{username}, null, null, null);

            // Vérifie si le curseur a des données
            exists = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("Database", "Error checking username existence: " + e.getMessage());
        } finally {
            // Ferme le curseur et la base de données
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return exists;
    }

    // Vérifie si un utilisateur avec la même adresse e-mail existe déjà dans la base de données
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            // Exécute une requête pour vérifier si un utilisateur avec la même adresse e-mail existe déjà
            cursor = db.query("User", new String[]{"email"}, "email=?", new String[]{email}, null, null, null);

            // Vérifie si le curseur a des données
            exists = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("Database", "Error checking email existence: " + e.getMessage());
        } finally {
            // Ferme le curseur et la base de données
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return exists;
    }

    //Retourne l'utilisteur correspondant à l'adresse email donnée en parametre
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = null;

        try {
            // Exécute une requête pour récupérer l'utilisateur associé à l'adresse e-mail donnée
            cursor = db.query("User", null, "email=?", new String[]{email}, null, null, null);

            // Vérifie si le curseur a des données et déplacer le curseur à la première ligne
            if (cursor != null && cursor.moveToFirst()) {
                // Vérifie si les colonnes existent dans le curseur
                int columnIndexUsername = cursor.getColumnIndex("username");
                int columnIndexEmail = cursor.getColumnIndex("email");
                int columnIndexAge = cursor.getColumnIndex("age");
                int columnIndexPassword = cursor.getColumnIndex("password");
                int columnIndexPremium = cursor.getColumnIndex("premium");

                if (columnIndexUsername != -1 && columnIndexEmail != -1 && columnIndexAge != -1
                        && columnIndexPassword != -1 && columnIndexPremium != -1) {
                    // Récupére les données de l'utilisateur à partir du curseur
                    String username = cursor.getString(columnIndexUsername);
                    String userEmail = cursor.getString(columnIndexEmail);
                    int userAge = cursor.getInt(columnIndexAge);
                    String userPassword = cursor.getString(columnIndexPassword);
                    boolean userPremium = cursor.getInt(columnIndexPremium) == 1;

                    // Crée un objet User avec les données récupérées
                    user = new User(username, userEmail, userAge, userPassword, userPremium);
                }
            }
        } catch (Exception e) {
            Log.e("Database", "Error retrieving user by email: " + e.getMessage());
        } finally {
            // Ferme le curseur et la base de données
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return user;
    }


}


