package com.example.felipe_ochoa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.felipe_ochoa.R;
import com.example.felipe_ochoa.data.models.University;
import com.example.felipe_ochoa.pages.AddApplicationActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UniversitiesAdapter extends RecyclerView.Adapter<UniversitiesAdapter.ViewHolder> {
    private List<University> items;
    private Context context;

    public UniversitiesAdapter(Context context, List<University> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public UniversitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.universities_item_view, parent, false);
        return new UniversitiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversitiesAdapter.ViewHolder holder, int position) {
        University item = items.get(position);
        // get 2 firs caracteres of the name and validate null and empty
        String name = item.getName();
        String twoFirstCaracters = "U";
        if (name != null && !name.isEmpty()) {
            twoFirstCaracters = name.substring(0, 2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate page
                Intent intent = new Intent(context, AddApplicationActivity.class);
                intent.putExtra("university", item.getName());
                context.startActivity(intent);
            }
        });
        holder.nameUniversity.setText(item.getName());
        holder.abreviationName.setText(twoFirstCaracters.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameUniversity;
        TextView abreviationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameUniversity = itemView.findViewById(R.id.nameUniversity);
            abreviationName = itemView.findViewById(R.id.abreviationName);
        }
    }

}
