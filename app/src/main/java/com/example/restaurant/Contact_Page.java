package com.example.restaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Contact_Page extends AppCompatActivity {



        private EditText etMessage;
        private Button btnSubmit;
        private TextView tvUserInfo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact_page);

            etMessage = findViewById(R.id.etMessage);
            btnSubmit = findViewById(R.id.btnSubmit);
            tvUserInfo = findViewById(R.id.tvUserInfo);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userEmail = currentUser.getEmail();
                tvUserInfo.setText("Email : " + userEmail);
            } else {
                tvUserInfo.setText("Utilisateur non connecté");
            }

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageContent = etMessage.getText().toString().trim();
                    if (!messageContent.isEmpty() && currentUser != null) {
                        String userId = currentUser.getUid();
                        String email = currentUser.getEmail();
                        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

                        // Création du modèle de message
                        ContactMessage contactMessage = new ContactMessage(email, messageContent, timestamp);

                        // Référence à la base de données Firebase
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userContactsRef = database.child("contacts").child(userId);
                        String messageId = userContactsRef.push().getKey(); // Identifiant unique pour le message

                        if (messageId != null) {
                            userContactsRef.child(messageId).setValue(contactMessage)
                                    .addOnSuccessListener(aVoid -> {
                                        // Message enregistré avec succès
                                        etMessage.setText("");
                                        Toast.makeText(Contact_Page.this, "Message envoyé avec succès", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Gestion de l'erreur
                                        Toast.makeText(Contact_Page.this, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Afficher un avertissement si le message est vide ou si l'utilisateur n'est pas connecté
                        Toast.makeText(Contact_Page.this, "Veuillez entrer un message et vérifier votre connexion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }