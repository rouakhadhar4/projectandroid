package com.example.restaurant;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private List<Categorie> categorieList;
    private DatabaseReference categoriesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categorieList = new ArrayList<>();
        adapter = new CategoriesAdapter(this, categorieList);
        recyclerView.setAdapter(adapter);

        categoriesRef = FirebaseDatabase.getInstance().getReference("catégories");

        // Récupérer les données depuis Firebase
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categorieList.clear(); // Réinitialiser la liste

                // Parcours de toutes les catégories
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Categorie categorie = snapshot.getValue(Categorie.class);
                    categorieList.add(categorie);
                }

                // Notifier l'adaptateur que les données ont été mises à jour
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CategoriePage", "Erreur de récupération des catégories", databaseError.toException());
            }
        });
    }
}
