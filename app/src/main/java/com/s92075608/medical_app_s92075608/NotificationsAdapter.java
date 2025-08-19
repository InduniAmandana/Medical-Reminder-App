package com.s92075608.medical_app_s92075608;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    public interface OnNotificationClick {
        void onDismiss(String message);
    }

    private final List<String> data;
    private final OnNotificationClick listener;

    public NotificationsAdapter(List<String> data, OnNotificationClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notifications, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg = data.get(position);
        holder.txtMessage.setText(msg);
        holder.btnDismiss.setOnClickListener(v -> listener.onDismiss(msg));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;
        ImageButton btnDismiss;

        ViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtNotification);
            btnDismiss = itemView.findViewById(R.id.btnDismiss);
        }
    }
}

