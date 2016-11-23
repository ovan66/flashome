package cl.bastian.flashome.views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cl.bastian.flashome.R;
import cl.bastian.flashome.views.main.chatList.ChatListFragment;
import cl.bastian.flashome.views.main.chatList.CreateMesageCallback;

public class ChatActivity extends AppCompatActivity implements CreateMesageCallback{

    private EditText userImput;
    private ImageButton sendBtn;
    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatId = getIntent().getStringExtra(ChatListFragment.CHAT_ID);
        String otherName = getIntent().getStringExtra(ChatListFragment.OTER_USER);
        getSupportActionBar().setTitle(otherName);

        userImput = (EditText) findViewById(R.id.mesageTv);
        sendBtn = (ImageButton) findViewById(R.id.sendMsgBtn);
        setSendBtn();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new UpdateChat(chatId).send();
    }

    private void setSendBtn (){
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesage = userImput.getText().toString();
                new CreateMessage(chatId,mesage,ChatActivity.this).send();

            }
        });
    }

    @Override
    public void clear() {
        userImput.setText("");
        if (userImput.getError() != null) {
            userImput.setError(null);
        }
    }

    @Override
    public void error(String error) {
        userImput.setError(error);

    }


}
