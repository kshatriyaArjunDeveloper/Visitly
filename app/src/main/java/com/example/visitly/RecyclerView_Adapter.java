package com.example.visitly;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_XY;
import static com.example.visitly.R.drawable.ic_baseline_person_24;
import static com.example.visitly.R.drawable.ic_baseline_insert_drive_file_24;
import static com.example.visitly.NewFC.dp_fab_clicked;


public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

    /**
     * Members AL THAT STORES ALL THE INFO TO BE UPLOADED
     */
    static ArrayList<FC_member> fc_members_al_main;
    private Context ctx;
    private LayoutInflater inflater;
    /**
     * LISTENER TO HAVE ONCLICK AND DO SOMETHING FROM ANOTHER ACTIVITY
     */
    private RV_ItemClickListener clickListener;


    RecyclerView_Adapter(Context ctx, ArrayList<FC_member> fc_members_al_main, RV_ItemClickListener clickListener) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        RecyclerView_Adapter.fc_members_al_main = fc_members_al_main;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_fc_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    /**
     * SETS ALL INFO TO LAYOUT VIEW OF RECYCLERVIEW
     * TIP : WHEN THERE IS A PROBLEM ADD HERE WHAT TO DO AND WHAT NOT TO
     */
    @Override
    public void onBindViewHolder(final RecyclerView_Adapter.MyViewHolder holder, final int position) {
        holder.editText_name.setText(fc_members_al_main.get(position).getNAME());
        if (holder.editText_age.getText().toString().equals("null") || holder.editText_age == null) {
            holder.editText_age.setText("");
        }
        holder.editText_age.setText(String.valueOf(fc_members_al_main.get(position).getAGE()));
        holder.editText_gender.setText(String.valueOf(fc_members_al_main.get(position).getGENDER()));
        holder.editText_passport.setText(String.valueOf(fc_members_al_main.get(position).getPASSPORT()));
        holder.editText_pnr.setText(String.valueOf(fc_members_al_main.get(position).getPNR()));
        if (holder.editText_mobile_no.getText().toString().equals("null") || holder.editText_mobile_no == null) {
            holder.editText_mobile_no.setText("");
        }
        holder.editText_mobile_no.setText(String.valueOf(fc_members_al_main.get(position).getM_NUMBER()));
        holder.delete_member_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc_members_al_main.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, fc_members_al_main.size());
                Toast.makeText(ctx, "Member Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        if (fc_members_al_main.get(position).getPerson_image_uri() != null) {
            holder.imageView_dp.setImageURI(fc_members_al_main.get(position).getPerson_image_uri());
            holder.imageView_dp.setScaleType(CENTER_CROP);
        } else {
            holder.imageView_dp.setImageResource(ic_baseline_person_24);
            holder.imageView_dp.setAdjustViewBounds(true);

        }
        if (fc_members_al_main.get(position).getIdentity1_image_uri() != null) {
            holder.imageView_ip1.setImageURI(fc_members_al_main.get(position).getIdentity1_image_uri());
            holder.imageView_ip1.setScaleType(FIT_XY);
        } else {
            holder.imageView_ip1.setImageResource(ic_baseline_insert_drive_file_24);
            holder.imageView_dp.setAdjustViewBounds(true);
        }

        if (fc_members_al_main.get(position).getIdentity2_image_uri() != null) {
            holder.imageView_ip2.setImageURI(fc_members_al_main.get(position).getIdentity2_image_uri());
            holder.imageView_ip2.setScaleType(FIT_XY);
        } else {
            holder.imageView_ip2.setImageResource(ic_baseline_insert_drive_file_24);
            holder.imageView_dp.setAdjustViewBounds(true);
        }

    }

    @Override
    public int getItemCount() {
        return fc_members_al_main.size();
    }


    /**
     * ALL VIEWS ARE REFERENCED AND CREATED
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        EditText editText_name;
        EditText editText_age;
        EditText editText_mobile_no;
        EditText editText_gender;
        EditText editText_passport;
        EditText editText_pnr;
        ImageButton delete_member_card;
        ImageView imageView_dp;
        ImageButton floatingActionButton_set_dp;
        ImageView imageView_ip1;
        ImageView imageView_ip2;
        ImageButton floatingActionButton_set_ip1;
        ImageButton floatingActionButton_set_ip2;


        public MyViewHolder(View itemView) {
            super(itemView);
            editText_name = itemView.findViewById(R.id.editText_name);
            editText_age = itemView.findViewById(R.id.editText_age);
            editText_mobile_no = itemView.findViewById(R.id.editText_mobile_no);
            editText_gender = itemView.findViewById(R.id.editText_gender);
            editText_passport = itemView.findViewById(R.id.editText_passport);
            editText_pnr = itemView.findViewById(R.id.editText_pnr);
            delete_member_card = itemView.findViewById(R.id.imageButton_delete_card);
            imageView_dp = itemView.findViewById(R.id.imageView_dp_LV);
            floatingActionButton_set_dp = itemView.findViewById(R.id.floatingActionButton);
            imageView_ip1 = itemView.findViewById(R.id.imageView_ip1_fcmLV);
            imageView_ip2 = itemView.findViewById(R.id.imageView_ip2_fcmLV);
            floatingActionButton_set_ip1 = itemView.findViewById(R.id.floatingActionButton1);
            floatingActionButton_set_ip2 = itemView.findViewById(R.id.floatingActionButton2);


            editText_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    fc_members_al_main.get(getAdapterPosition()).setNAME(editText_name.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText_age.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_age.getText().toString().equals("null") || editText_age == null) {
                        editText_age.setText("");
                    }
                    fc_members_al_main.get(getAdapterPosition()).setAGE(String.valueOf(editText_age.getText()));
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            editText_mobile_no.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_mobile_no.getText().toString().equals("null") || editText_mobile_no == null) {
                        editText_mobile_no.setText("");
                    }
                    fc_members_al_main.get(getAdapterPosition()).setM_NUMBER(editText_mobile_no.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText_gender.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_gender.getText().toString().equals("null") || editText_gender == null) {
                        editText_gender.setText("");
                    }
                    fc_members_al_main.get(getAdapterPosition()).setGENDER(editText_gender.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText_passport.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_passport.getText().toString().equals("null") || editText_passport == null) {
                        editText_passport.setText("");
                    }
                    fc_members_al_main.get(getAdapterPosition()).setPASSPORT(editText_passport.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText_pnr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_pnr.getText().toString().equals("null") || editText_pnr == null) {
                        editText_pnr.setText("");
                    }
                    fc_members_al_main.get(getAdapterPosition()).setPNR(editText_pnr.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            floatingActionButton_set_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                    dp_fab_clicked = 3;
                }
            });

            floatingActionButton_set_ip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                    dp_fab_clicked = 1;
                }
            });

            floatingActionButton_set_ip2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                    dp_fab_clicked = 2;
                }
            });


        }

    }

}
