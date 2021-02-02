package com.example.visitly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.example.visitly.LoginActivity.user;

public class MainActivity extends AppCompatActivity {

    static String flightName;
    TextView greetTextView;
    ImageView imageView_userImage;
    ImageView imageViewMap;
    Button buttonNewFC;
    // recent flight checkins related variables
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flightRef = db.collection("APP/FC/" + user.getEmail());
    private FlightAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNewFC = findViewById(R.id.buttonNewFC);
        greetTextView = findViewById(R.id.greet_textView);
        greetTextView.setText("Hey, " + user.getDisplayName().substring(0, user.getDisplayName().indexOf(" ")));
        imageView_userImage = findViewById(R.id.imageView_user);
        Glide.with(this)
                .asBitmap()
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable d = new BitmapDrawable(getResources(), resource);
                        imageView_userImage.setImageDrawable(d);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        setUpRecyclerView();


//        opening new flight checkin acitivity
        buttonNewFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewFC.class);
                startActivity(intent);
            }
        });
//        opening recentFC activity
        imageView_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecentFCActivity.class);
                startActivity(intent);
            }
        });

        imageViewMap = findViewById(R.id.imageView_map);
        //        opening Map activity
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = flightRef;
        FirestoreRecyclerOptions<Flight> options = new FirestoreRecyclerOptions.Builder<Flight>()
                .setQuery(query, Flight.class)
                .build();
        adapter = new FlightAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.flightCheckins);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(MainActivity.this, FCDetails.class);
                startActivity(intent);
                Flight flight = documentSnapshot.toObject(Flight.class);
                flightName = flight.getFLIGHT_NAME();
                Toast.makeText(MainActivity.this,
                        "Flight Details of: " + flightName, Toast.LENGTH_SHORT).show();
            }
        });
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