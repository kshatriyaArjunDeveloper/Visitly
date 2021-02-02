package com.example.visitly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.visitly.LoginActivity.user;
import static com.example.visitly.RecyclerView_Adapter.fc_members_al_main;

public class NewFC extends AppCompatActivity {


    /**
     * FC RELATED VARIABLES
     */
    public ArrayList<FC_member> memberlist_rv;

    /**
     * RECYCLERVIEW RELATED VARIABLES
     */
    private RecyclerView recyclerView;
    private RecyclerView_Adapter fc_rv_adapter;
    public final int PICK_IMAGE_REQUEST = 1;
    static Uri imageUri;
    Integer image_position_al;
    static Integer dp_fab_clicked;

    /**
     * FIRESTORE AND FIREBASE STORAGE RELATED VARIABLES
     */
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference spaceRef;
    DocumentReference doc_ref;

    /**
     * SOME OTHER VARIABLES
     */
    Button addFC;

    /* FC NUMBER HOLDER */
    EditText flightName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_f_c);

        /* REFERENCING FC UPLOAD BUTTON AND FC NO. EDIT TEXT AND TOTAL MEMBERS TEXT VIEW */
        addFC = findViewById(R.id.buttonUploadFC);
        flightName = findViewById(R.id.editTextFlightName);

        /* RECYCLERVIEW INITIALIZING ...*/
        memberlist_rv = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        fc_rv_adapter = new RecyclerView_Adapter(this, memberlist_rv, new RV_ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                /* ADDS IMAGE IN CORRECT ARRAY ELEMENT */
                image_position_al = position;
                /* SELECTS IMAGE FROM GALLERY*/
                select_image();
                imageUri = null;
            }
        });
        recyclerView.setAdapter(fc_rv_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    /**
     * UPLOAD ALL DATA TO FIRESTORE AND FIREBASE
     */
    public void addToFirestoreDb(View v) {

        /* ADDING MEMBERS */
        Map<String, Object> fcMember = new HashMap<>();

//        Making a flight
        Map<String, Object> flightNameDb = new HashMap<>();
        flightNameDb.put("FLIGHT_NAME", flightName.getText().toString());
        doc_ref = FirebaseFirestore.getInstance().document
                ("APP/FC/" + user.getEmail() + "/" + flightName.getText().toString());
        doc_ref.set(flightNameDb).addOnFailureListener(NewFC.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewFC.this,
                        "PROBLEM OCCURRED ADDING Flight:" + flightName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0; i < fc_members_al_main.size(); i++) {
            fcMember.put("NAME", fc_members_al_main.get(i).getNAME());
            fcMember.put("AGE", fc_members_al_main.get(i).getAGE());
            fcMember.put("GENDER", fc_members_al_main.get(i).getGENDER());
            fcMember.put("PASSPORT", fc_members_al_main.get(i).getPASSPORT());
            fcMember.put("PNR", fc_members_al_main.get(i).getPNR());
            fcMember.put("M_NUMBER", fc_members_al_main.get(i).getM_NUMBER());
            fcMember.put("VERIFICATION", "NO");
            doc_ref = FirebaseFirestore.getInstance().document
                    ("APP/FC/" + user.getEmail() + "/" + flightName.getText().toString() + "/MEMBERS/" + fcMember.get("NAME"));
            final int finalI = i;
            doc_ref.set(fcMember).addOnFailureListener(NewFC.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewFC.this,
                            "PROBLEM OCCURRED ADDING MEMBER:" + finalI, Toast.LENGTH_SHORT).show();
                }
            });


            /* UPLOADING PROFILE IMAGES OF MEMBERS */
            spaceRef = storageRef.child("APP/FC/" + user.getEmail() + "/" + flightName.getText().toString() + "/" + fcMember.get("NAME") +
                    "/PROFILE_IMAGE.jpg");
            if (fc_members_al_main.get(i).getPerson_image_uri() != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                spaceRef.putFile(fc_members_al_main.get(i).getPerson_image_uri())
                        .addOnSuccessListener(NewFC.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(NewFC.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(NewFC.this,
                                        "FAILED UPLOADING MEMBER IMAGE" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(NewFC.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }

            /* UPLOADING IDENTITY IMAGES OF MEMBERS */
            spaceRef = storageRef.child("APP/FC/" + user.getEmail() + "/" + flightName.getText().toString() + "/" +  fcMember.get("NAME") +
                    "/IDENTITY_IMAGE1.jpg");
            if (fc_members_al_main.get(i).getIdentity1_image_uri() != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                spaceRef.putFile(fc_members_al_main.get(i).getIdentity1_image_uri())
                        .addOnSuccessListener(NewFC.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(NewFC.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(NewFC.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(NewFC.this,
                                        "FAILED UPLOADING IDENTITY PROOF" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(NewFC.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }

            spaceRef = storageRef.child("APP/FC/" + user.getEmail() + "/" + flightName.getText().toString() + "/" + fcMember.get("NAME") +
                    "/IDENTITY_IMAGE2.jpg");
            if (fc_members_al_main.get(i).getIdentity2_image_uri() != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                spaceRef.putFile(fc_members_al_main.get(i).getIdentity2_image_uri())
                        .addOnSuccessListener(NewFC.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(NewFC.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(NewFC.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(NewFC.this,
                                        "FAILED UPLOADING IDENTITY PROOF" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(NewFC.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
        }

    }

    /**
     * ADD ONE MEMBER TO ARRAYLIST OF FC MEMBERS FOR RECYCLERVIEW ADAPTER
     */
    public void onAddMember(View v) {
        FC_member healthcardmember = new FC_member();
        memberlist_rv.add(healthcardmember);
        fc_rv_adapter.notifyItemInserted(memberlist_rv.size());
        recyclerView.scrollToPosition(memberlist_rv.size() - 1);
        Toast.makeText(this, "Member Added", Toast.LENGTH_SHORT).show();
    }


    /**
     * SELECTS IMAGE FROM GALLERY
     */
    public void select_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                if (dp_fab_clicked == 3) {
                    fc_members_al_main.get(image_position_al).setPerson_image_uri(imageUri);
                } else if (dp_fab_clicked == 1) {
                    fc_members_al_main.get(image_position_al).setIdentity1_image_uri(imageUri);
                } else {
                    fc_members_al_main.get(image_position_al).setIdentity2_image_uri(imageUri);
                }
                fc_rv_adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}