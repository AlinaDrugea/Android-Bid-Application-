package com.example.aplicatibid;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import android.os.CountDownTimer;

public class AnuntMeuAdapter extends RecyclerView.Adapter<AnuntMeuAdapter.AnuntViewHolder> {
    private List<Anunt> anunturi;
    public AnuntMeuAdapter(List<Anunt> anunturi) {
        this.anunturi = anunturi;
    }

    @NonNull
    @Override
    public AnuntViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anunturile_mele, parent, false);
        return new AnuntViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuntViewHolder holder, int position) {
        Anunt anuntcurent = anunturi.get(position);

        holder.tvTitlu.setText(anuntcurent.getTitle());
        holder.tvTimpRamas.setText("Timp ramas" + anuntcurent.getTimpRamas());
        // Adăugăm un spațiu înainte de RON pentru design
        holder.tvPretActual.setText(anuntcurent.getActual_price().toString() + " RON");
        holder.status.setText("Status:" + anuntcurent.getStatus().toString());
        Glide.with(holder.itemView.getContext())
                .load(anuntcurent.getImage())
                .into(holder.imageView);

        if (holder.timer != null) {
            holder.timer.cancel();
        }

        long timpFinal = anuntcurent.getEndTimeInMillis();
        long timpRamasMillis = timpFinal - System.currentTimeMillis();

        if (timpRamasMillis > 0) {
            holder.timer = new CountDownTimer(timpRamasMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.tvTimpRamas.setText(formatTimp(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    holder.tvTimpRamas.setText("EXPIRAT");
                }
            }.start();
        } else {
            holder.tvTimpRamas.setText("EXPIRAT");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Pagina_anunt.class);

            intent.putExtra("id_anunt", anuntcurent.getId_anunt());
            intent.putExtra("title", anuntcurent.getTitle());
            intent.putExtra("description", anuntcurent.getDescription());

            intent.putExtra("actual_price", anuntcurent.getActual_price().toString());
            intent.putExtra("timp_ramas", anuntcurent.getTimpRamas());
            intent.putExtra("image", anuntcurent.getImage());
            intent.putExtra("status",anuntcurent.getStatus());
            intent.putExtra("lider",anuntcurent.getUsernameLider());

            v.getContext().startActivity(intent);
        });
    }
    private String formatTimp(long millis) {
        long secunde = millis / 1000;
        long minute = secunde / 60;
        long ore = minute / 60;
        long zile = ore / 24;

        if (zile > 0) {
            return String.format("%d zile %02d:%02d:%02d", zile, ore % 24, minute % 60, secunde % 60);
        }
        return String.format("%02d:%02d:%02d", ore, minute % 60, secunde % 60);
    }

    @Override
    public int getItemCount() {
        return anunturi != null ? anunturi.size() : 0;
    }

    public static class AnuntViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitlu, tvTimpRamas, tvPretActual, status;
        ImageView imageView;
        CountDownTimer timer;


        public AnuntViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitlu = itemView.findViewById(R.id.tvTitlu);
            tvTimpRamas = itemView.findViewById(R.id.tvTimpRamas);
            tvPretActual = itemView.findViewById(R.id.tvPretActual);
            imageView = itemView.findViewById(R.id.imageView);
            status = itemView.findViewById(R.id.status);
        }
    }
}