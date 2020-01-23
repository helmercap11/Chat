package com.helmercapassola.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView messageText;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        messageText = itemView.findViewById(R.id.textview_message);
    }
}
