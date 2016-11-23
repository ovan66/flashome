package cl.bastian.flashome.views.chat;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import cl.bastian.flashome.data.FirebaseRef;
import cl.bastian.flashome.data.UserData;
import cl.bastian.flashome.models.Message;
import cl.bastian.flashome.views.main.chatList.CreateMesageCallback;

/**
 * Created by santo_000 on 17-11-2016.
 */

public class CreateMessage {
    private String chatId;
    private Message msg;

    private CreateMesageCallback callback;{
    }

    public CreateMessage(String chatId, String message, CreateMesageCallback callback) {
        this.chatId = chatId;
        this.callback = callback;
        String userEmail = new UserData().email();
        msg = new Message(message, userEmail);

    }

    public void send() {

        if (msg.message.trim().length() > 0) {
            new FirebaseRef().messages().child(chatId).push().setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    callback.clear();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.error("No se pudo enviar, intentelo más tarde");
                }
            });
        } else {
            callback.clear();
        }
    }
}
