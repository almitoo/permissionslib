package com.example.checkpermissions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<PermissionsModel>PermissionsModelList;



    public Adapter(Context context, ArrayList<PermissionsModel> PermissionsModelList,RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.PermissionsModelList=PermissionsModelList;
        this.recyclerViewInterface=recyclerViewInterface;

    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new Adapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        PermissionsModel model = PermissionsModelList.get(position);

        holder.name.setText(PermissionsModelList.get(position).getPermissionName());
        holder.imageView.setImageResource(PermissionsModelList.get(position).getImage());

        if (model.isGranted()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return PermissionsModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView imageView;
        AppCompatTextView name;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
