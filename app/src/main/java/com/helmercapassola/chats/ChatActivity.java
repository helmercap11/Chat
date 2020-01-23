package com.helmercapassola.chats;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    FirebaseAuth auth;
    EditText editTextMessage;
    String username;
    String userPhoto;
    RecyclerView recyclerViewMessageList;

    FirebaseRecyclerAdapter<Message, MessageViewHolder> firebaseRecyclerAdapter;

    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message = new Message(editTextMessage.getText().toString(), username, userPhoto, null);
                databaseReference.child("messages").push().setValue(message);
                editTextMessage.setText("");
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        editTextMessage = findViewById(R.id.edit_message);
       // username = auth.getCurrentUser().getDisplayName();

        auth = FirebaseAuth.getInstance();

       /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();
        } else {
            // No user is signed in
            Toast.makeText(this, "Nao", Toast.LENGTH_SHORT).show();
        }
*/


        recyclerViewMessageList = findViewById(R.id.messages_list);
        manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.getStackFromEnd();
        recyclerViewMessageList.setLayoutManager(manager);

        initLoad();


    }



    public  void initLoad(){


        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(databaseReference.child("messages"), Message.class).build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
                holder.messageText.setText(model.getText());
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false));
            }
        };


        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int messageCount = firebaseRecyclerAdapter.getItemCount();
                int lastMessage = manager.findLastCompletelyVisibleItemPosition();

                if (lastMessage == -1 || (positionStart >= (messageCount - 1) && lastMessage == (positionStart - 1)) ){
                    recyclerViewMessageList.scrollToPosition(positionStart);
                }
            }
        });

        recyclerViewMessageList.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

}
