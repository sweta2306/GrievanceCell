package com.sweta.grievancemakers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sweta.grievancemakers.R;
import com.sweta.grievancemakers.model.ChatModel;

import java.util.ArrayList;

/**
 * Created by 1406074 on 02-06-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ChatModel> objects;
    private FirebaseAuth mAuth;

    public ChatAdapter(Context context, ArrayList<ChatModel> objects,FirebaseAuth auth) {
        this.context = context;
        this.objects = objects;
        this.mAuth = auth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_chat,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        ChatModel model = objects.get(position);
        String UID = model.getUid();
        //Log.e("UID","model"+UID);

        if(mAuth.getCurrentUser().getUid().equals(UID)){
            //Log.e("boolean value","->"+mAuth.getCurrentUser().getUid().equals(UID));
            holder.linearLayout_receiver.setVisibility(View.GONE);
            holder.text_receiver.setVisibility(View.GONE);
            holder.text_sender.setText(model.getText());

        }else{
            holder.linearLayout_sender.setVisibility(View.GONE);
            holder.text_sender.setVisibility(View.GONE);
            holder.text_receiver.setText(model.getText());

        }

    }
    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_sender;
        public TextView text_receiver;
        public LinearLayout linearLayout_sender;
        public LinearLayout linearLayout_receiver;
        public ViewHolder(View itemView) {
            super(itemView);
            text_sender = (TextView) itemView.findViewById(R.id.text_view_sender_message);
            text_receiver = (TextView) itemView.findViewById(R.id.text_view_recipient_message);
            linearLayout_sender = (LinearLayout) itemView.findViewById(R.id.linear_layout_sender);
            linearLayout_receiver = (LinearLayout) itemView.findViewById(R.id.linear_layout_receiver);


        }
    }
}
