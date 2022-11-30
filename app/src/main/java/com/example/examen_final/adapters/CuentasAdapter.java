package com.example.examen_final.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_final.R;
import com.example.examen_final.entities.Cuentas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuentasAdapter  extends RecyclerView.Adapter{

    List<Cuentas> data;

    public CuentasAdapter(List<Cuentas> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//aquí llamamos al contexto

        View itemView = inflater.inflate(R.layout.item_cuentas, parent, false);//aquí hacemos referencia al item creado

        return new CuentasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cuentas cuentas = data.get(position);

        TextView tvNombre = holder.itemView.findViewById(R.id.tvNombre);
        tvNombre.setText(data.get(position).nombre);

        TextView tvSaldo = holder.itemView.findViewById(R.id.tvSaldo);
        tvSaldo.setText(data.get(position).saldo);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    static class CuentasViewHolder extends RecyclerView.ViewHolder {
        public CuentasViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

