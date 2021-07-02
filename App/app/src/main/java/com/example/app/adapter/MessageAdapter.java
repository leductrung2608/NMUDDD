package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;
    TextView txt_seen, show_message;
    CircleImageView profile_image;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            txt_seen = view.findViewById(R.id.txt_seen);
            show_message = view.findViewById(R.id.show_message);
            profile_image = view.findViewById(R.id.profile_image);

            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            txt_seen = view.findViewById(R.id.txt_seen);
            show_message = view.findViewById(R.id.show_message);
            profile_image = view.findViewById(R.id.profile_image);

            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        show_message.setText(chat.getMessage());

        if (imageurl.equals("default")){
            profile_image.setImageResource(R.drawable.profile_avatar);
        } else {
            Glide.with(mContext).load(imageurl).into(profile_image);
        }

        if (position == mChat.size()-1){
            if (chat.isIsseen()){
                txt_seen.setText("Seen");
            } else {
                txt_seen.setText("Delivered");
            }
        } else {
            txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}