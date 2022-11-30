package com.example.examen_final.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_final.DetalleMovimientos;
import com.example.examen_final.R;
import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.entities.Movimientos;
import com.google.gson.Gson;

import java.util.List;

public class MovimientosAdapter extends RecyclerView.Adapter {
    List<Movimientos> data;

    public MovimientosAdapter(List<Movimientos> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//aquí llamamos al contexto

        View itemView = inflater.inflate(R.layout.item_movimientos, parent, false);//aquí hacemos referencia al item creado

        return new MovimientosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movimientos movimiento = data.get(position);

        TextView tvTipo = holder.itemView.findViewById(R.id.tvTipoMovimiento);
        tvTipo.setText(data.get(position).tipo + "\t\tS/. " +  data.get(position).monto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetalleMovimientos.class);
                intent.putExtra("MOVIMIENTO_DATA", new Gson().toJson(movimiento));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MovimientosViewHolder extends RecyclerView.ViewHolder {
        public MovimientosViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
