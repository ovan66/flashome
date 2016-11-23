package cl.bastian.flashome.views.chat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import cl.bastian.flashome.data.FirebaseRef;
import cl.bastian.flashome.data.UserData;

/**
 * Created by santo_000 on 17-11-2016.
 */

public class UpdateChat {

    private String chatId;

    public UpdateChat(String chatId) {
        this.chatId = chatId;
    }

    public void send (){
        String uid = new UserData().user().getUid();
        DatabaseReference reference = new FirebaseRef().chatList().child(uid).child(chatId);
        reference.child("notification").setValue(false);
        reference.child("timestamp").setValue(ServerValue.TIMESTAMP);
    }
}
