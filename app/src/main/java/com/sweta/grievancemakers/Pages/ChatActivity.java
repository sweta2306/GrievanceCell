package com.sweta.grievancemakers.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sweta.grievancemakers.FireChatHelper.ExtraIntent;
import com.sweta.grievancemakers.R;
import com.sweta.grievancemakers.Services.MyService;
import com.sweta.grievancemakers.adapter.ChatAdapter;
import com.sweta.grievancemakers.model.ChatModel;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();


    RecyclerView mChatRecyclerView;
    // @BindView(R.id.edit_text_message) EditText mUserMessageChatText;

    private int Count = 0;
    private String mRecipientId;
    private String mCurrentUserId;
    private DatabaseReference messageChatDatabase;
    private ChildEventListener messageChatListener;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private EditText mUserMessageChatText;
    private ImageButton SEND_MESSAGE;
    private ChatAdapter mUsersChatAdapter;
    private ArrayList<ChatModel> Chats;
    ArrayList<ChatModel> NEWCHATS = new ArrayList<>();
    public String FLAG = "First";
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        startService(new Intent(ChatActivity.this, MyService.class));
        Bundle extras = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        mRecipientId = "5HG1zF2CqIRhapNbupTJdEPu4rk2";
        //mRecipientId=extras.getString(ExtraIntent.EXTRA_RECIPIENT_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SEND_MESSAGE = (ImageButton) findViewById(R.id.btn_send_message);
        mAuth = FirebaseAuth.getInstance();
        mUserMessageChatText = (EditText) findViewById(R.id.edit_text_message);
        messageChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats").child(mRecipientId).child(mAuth.getCurrentUser().getUid());
        //databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Chats").child(mAuth.getCurrentUser().getUid()).child(mRecipientId);
        messageChatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Datasnapshot", dataSnapshot.toString() + "Hello");
                Chats = new ArrayList<ChatModel>();
                Chats.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.v("DATASNANSHOT", ds.toString() + "d");
                    ChatModel CHAT = ds.getValue(ChatModel.class);
                    Chats.add(CHAT);
                    Count++;
                    Log.v("COunt", Count + "");
                }
                Log.v("Chat Length", Chats.size() + "");
                mUsersChatAdapter = new ChatAdapter(ChatActivity.this, Chats, mAuth);
                mChatRecyclerView.setAdapter(mUsersChatAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        SEND_MESSAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertModelElemets(mUserMessageChatText.getText().toString());
            }
        });
    }

        private void insertModelElemets(String text){
            ChatModel chatModel = new ChatModel();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            chatModel.setUid(uid);
            chatModel.setText(text);
            messageChatDatabase.push().setValue(chatModel);
            mUserMessageChatText.setText("");
            mChatRecyclerView.getLayoutManager().scrollToPosition(mUsersChatAdapter.getItemCount() - 1);
        }

    }
