package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView userDetails;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        userDetails = findViewById(R.id.user_details);
        logoutButton = findViewById(R.id.logout_button);

        // Récupérer l'utilisateur actuel
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Si l'utilisateur est connecté, afficher ses détails
        if (currentUser != null) {
            String userInfo = "Email: " + currentUser.getEmail();
            userDetails.setText(userInfo);
        } else {
            userDetails.setText("No user is logged in");
        }

        // Définir l'action de déconnexion
        logoutButton.setOnClickListener(v -> {
            // Déconnecter l'utilisateur
            FirebaseAuth.getInstance().signOut();

            // Afficher un message de succès
            Toast.makeText(MainActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();

            // Rediriger vers l'écran de connexion (LoginActivity)
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // Ferme l'activité actuelle pour éviter de revenir à cette page après la déconnexion
        });
    }
}
