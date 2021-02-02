package com.example.visitly;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.visitly.FCDetails.context;
import static com.example.visitly.LoginActivity.user;
import static com.example.visitly.MainActivity.flightName;
import static com.example.visitly.QRCodeActivity.QRStorageReference;

public class flightCheckinsAdapter extends FirestoreRecyclerAdapter<FC_member, flightCheckinsAdapter.flightCheckinHolder> {


    private StorageReference storageReference;

    public flightCheckinsAdapter(@NonNull FirestoreRecyclerOptions<FC_member> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull flightCheckinHolder holder, int position, @NonNull FC_member model) {
        holder.textView_name.setText("Name : " + model.getNAME());
        holder.textView_age.setText("Age: " + model.getAGE());
        holder.textView_gender.setText("Gender: " + model.getGENDER());
        holder.textView_pnr.setText("PNR NO. : " + model.getPNR());
        holder.textView_passport.setText("PASSPORT NO. : " + model.getPASSPORT());
        holder.textView_mobile_no.setText("Mobile NO.: " + model.getM_NUMBER());
        holder.textView_verification.setText("VERIFICATION STATUS: " + model.getVERIFICATION());

        // Reference to an image file in Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference
                ("/APP/FC/" + user.getEmail() + "/" + flightName + "/" + model.getNAME() + "/PROFILE_IMAGE.jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context /* context */)
                .load(storageReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageView_dp);

        // Reference to an image file in Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference
                ("/APP/FC/" + user.getEmail() + "/" + flightName + "/" + model.getNAME() + "/IDENTITY_IMAGE1.jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context /* context */)
                .load(storageReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageView_ip1);

        // Reference to an image file in Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference
                ("/APP/FC/" + user.getEmail() + "/" + flightName + "/" + model.getNAME() + "/IDENTITY_IMAGE2.jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context /* context */)
                .load(storageReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageView_ip2);

        // Reference to an image file in Cloud Storage
        QRStorageReference = storageReference = FirebaseStorage.getInstance().getReference
                ("/APP/FC/" + user.getEmail() + "/" + flightName + "/" + model.getNAME() + "/QR_IMAGE.png");

        holder.buttonOpenQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, QRCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.imageView_dp.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView_ip1.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView_ip2.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    @NonNull
    @Override
    public flightCheckinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_fc_item,
                parent, false);
        return new flightCheckinHolder(v);
    }

    class flightCheckinHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_verification;
        TextView textView_age;
        TextView textView_mobile_no;
        TextView textView_gender;
        TextView textView_passport;
        TextView textView_pnr;
        ImageView imageView_dp;
        ImageView imageView_ip1;
        ImageView imageView_ip2;
        Button buttonOpenQR;

        public flightCheckinHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.nameLV);
            textView_verification = itemView.findViewById(R.id.verificationLV);
            textView_age = itemView.findViewById(R.id.ageLV);
            textView_mobile_no = itemView.findViewById(R.id.numberLV);
            textView_gender = itemView.findViewById(R.id.genderLV);
            textView_passport = itemView.findViewById(R.id.passportLV);
            textView_pnr = itemView.findViewById(R.id.pnrLV);
            imageView_dp = itemView.findViewById(R.id.imageView_dp_LV);
            imageView_ip1 = itemView.findViewById(R.id.imageView_ip1_fcmLV);
            imageView_ip2 = itemView.findViewById(R.id.imageView_ip2_fcmLV);
            buttonOpenQR = itemView.findViewById(R.id.buttonOpenQR);

        }
    }
}
