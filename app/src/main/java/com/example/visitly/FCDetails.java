package com.example.visitly;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.example.visitly.LoginActivity.user;
import static com.example.visitly.MainActivity.flightName;

public class FCDetails extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("APP/FC/" + user.getEmail() +"/" + flightName + "/MEMBERS");
    private flightCheckinsAdapter adapter;
    TextView flightNameTv;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_c_details);

        context = getApplicationContext();
        flightNameTv = findViewById(R.id.textViewFlightName);
        flightNameTv.setText(flightName);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = notebookRef;
        FirestoreRecyclerOptions<FC_member> options = new FirestoreRecyclerOptions.Builder<FC_member>()
                .setQuery(query, FC_member.class)
                .build();
        adapter = new flightCheckinsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_viewLV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}