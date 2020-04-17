package com.bjshDkl.agriculture.forum.chat.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.forum.chat.login.LoginActivity;
import com.bjshDkl.agriculture.forum.chat.model.Message;
import com.bjshDkl.agriculture.forum.chat.model.User;
import com.bjshDkl.agriculture.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messageDb;
    MessageAdapter messageAdapter;
    List<Message> messages = new ArrayList<>();

    User user;

    RecyclerView messageRecyclerView;
    EditText messageEditText;
    ImageButton sendMessageImageButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Constants.userIdSharedPref,0);
        bindActivity();
    }

    private void bindActivity() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();

        final FirebaseUser currentUser = auth.getCurrentUser();
        user.setEmail(currentUser.getEmail());

        database.getReference("chat/users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
//                user.setUid(currentUser.getUid());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid", dataSnapshot.getKey());
                editor.putString("userName",user.getName());
                editor.putString("userEmail",user.getEmail());
                editor.commit();

//                AllMethod.name = user.getName();
//                AllMethod.uid = dataSnapshot.getKey().toString();
//                user.setUid(dataSnapshot.getKey().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        messageRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        messageEditText = (EditText) findViewById(R.id.inputMessageET);
        sendMessageImageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendMessageImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(messageEditText.getText().toString())) {
//            AllMethod.uid = sharedPreferences.getString("uid","");
            Message message = new Message(messageEditText.getText().toString(),sharedPreferences.getString("uid","   "),user.getName());
            messageEditText.setText("");
            messageDb.push().setValue(message);

        } else {
            Toast.makeText(this, "Enter message to send.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser();
        user.setUid(currentUser.getUid());
        user.setEmail(currentUser.getEmail());

        database.getReference("chat/users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
//                user.setUid(currentUser.getUid());
                AllMethod.name = user.getName();
                AllMethod.uid = user.getUid();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messageDb = database.getReference("chat/message_forum_chat");
        messageDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessage(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                List<Message> newMessages = new ArrayList<>();

                for (Message m : messages) {
                    if (m.getKey().equals(message.getKey())) {
                        newMessages.add(message);
                    } else {
                        newMessages.add(m);
                    }
                }
                messages = newMessages;
                displayMessage(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                List<Message> newMessages = new ArrayList<>();
                for (Message m : messages) {
                    if (!(m.getKey().equals(message.getKey()))) {
                        newMessages.add(m);
                    }
                }
                messages = newMessages;
                displayMessage(messages);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessage(List<Message> messages) {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        messageAdapter = new MessageAdapter(ChatActivity.this, messages, messageDb,sharedPreferences);
        messageRecyclerView.setAdapter(messageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            auth.signOut();
            finish();
            startActivity(new Intent(ChatActivity.this, LoginActivity.class));

        }
        else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
