package com.example.visitly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FlightAdapter extends FirestoreRecyclerAdapter<Flight, FlightAdapter.FlightHolder> {
    private OnItemClickListener listener;

    public FlightAdapter(@NonNull FirestoreRecyclerOptions<Flight> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FlightAdapter.FlightHolder holder, int position, @NonNull Flight flight) {
        holder.FLIGHT_NAME.setText(flight.getFLIGHT_NAME());
    }

    @NonNull
    @Override
    public FlightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item,
                parent, false);
        return new FlightHolder(v);
    }

    class FlightHolder extends RecyclerView.ViewHolder {
        TextView FLIGHT_NAME;

        public FlightHolder(View itemView) {
            super(itemView);
            FLIGHT_NAME = itemView.findViewById(R.id.FLIGHT_NAME);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}