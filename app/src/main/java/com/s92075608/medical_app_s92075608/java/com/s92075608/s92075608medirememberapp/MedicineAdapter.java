package com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s92075608.medical_app_s92075608.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.VH> {

    public interface OnLongDelete {
        void onDelete(Medicine m);
    }

    private final List<Medicine> data;
    private final OnLongDelete longDelete;

    public MedicineAdapter(List<Medicine> data, OnLongDelete longDelete) {
        this.data = data;
        this.longDelete = longDelete;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Medicine m = data.get(pos);
        h.tvTitle.setText(m.name + " • " + m.type);
        h.tvSubtitle.setText("Dose: " + m.dose + "   Amount: " + m.amount);

        if (m.reminderEnabled && m.reminderAt > 0) {
            String when = new SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault())
                    .format(new Date(m.reminderAt));
            h.tvWhen.setText("Reminder: " + when);
        } else {
            h.tvWhen.setText("Reminder: Off");
        }

        h.itemView.setOnLongClickListener(v -> {
            if (longDelete != null) longDelete.onDelete(m);
            return true;
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle, tvWhen;
        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvWhen = itemView.findViewById(R.id.tvWhen);
        }
    }
}
