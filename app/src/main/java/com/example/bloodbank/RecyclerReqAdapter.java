package com.example.bloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerReqAdapter extends RecyclerView.Adapter<RecyclerReqAdapter.ListViewHolder>  {
    ArrayList<Request> requests;
    onBGClick click;


    public RecyclerReqAdapter( ArrayList<Request> requests,onBGClick click)
    {
        this.requests=requests;
        this.click = click;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.reques_item_layout,parent,false);
        ListViewHolder viewHolder=new ListViewHolder(view,click);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.tvMessage.setText(request.getMessage());
        holder.tvGroup.setText(request.getGroup());
        holder.tvNumber.setText(request.getNumber());
        holder.tvCity.setText(request.getCity());
    }


    @Override
    public int getItemCount() {
        return requests.size();
    }


    public static class ListViewHolder extends  RecyclerView.ViewHolder{
        TextView tvMessage,tvNumber,tvGroup,tvCity;
        ImageButton call,share;
        onBGClick click;


        public ListViewHolder(@NonNull View itemView, final onBGClick click) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.messageview);
            tvCity = itemView.findViewById(R.id.cityview);
            tvNumber = itemView.findViewById(R.id.numberview);
            tvGroup = itemView.findViewById(R.id.bgview);
            call = itemView.findViewById(R.id.call_button);
            share = itemView.findViewById(R.id.share_button);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onCall(getAdapterPosition());
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onShare(getAdapterPosition());
                }
            });
        }
    }


    public interface onBGClick{
        void onShare(int pos);
        void onCall(int pos);
    }
}
