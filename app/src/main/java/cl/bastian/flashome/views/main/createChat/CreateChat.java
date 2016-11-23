package cl.bastian.flashome.views.main.createChat;

import android.content.Context;
import android.transition.ChangeTransform;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;

import cl.bastian.flashome.data.FirebaseRef;
import cl.bastian.flashome.data.UserData;
import cl.bastian.flashome.models.Chat;
import cl.bastian.flashome.models.User;
import cl.bastian.flashome.views.main.drawer.PhotoData;

/**
 * Created by santo_000 on 16-11-2016.
 */

public class CreateChat {

    private VerificationCallback callback;
    private String email;
    private Context context;
    private UserData userData = new UserData();
    private User oterUser = new User();

    public CreateChat(VerificationCallback callback, String email, Context context) {
        this.callback = callback;
        this.email = email;
        this.context = context;
    }

    public void init() {
        if (email.trim().length() == 0 || !email.contains(".") || !email.contains("@") || email.contains(" ")) {
            callback.invalid("Email no valido");
        } else {
            if (email.equals(userData.email())) {
                callback.selft("tu mismo");
            } else {
                search();
            }

        }
    }


    private void search() {
        new FirebaseRef().users().orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() !=null){
                    oterUser =dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    creation();
                }else{
                    callback.notFound("Usuario no encontrado");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.notFound("Error del servidos");

            }
        });

    }

    private void creation(){
        String key = userData.email().replace(".","DOT" + " ; " +oterUser.email.replace(".","DOT"));
        Chat chat =new Chat(
                true,
                oterUser.photo,
                oterUser.email,
                new PhotoData(context).getUrl(),
                userData.email(),
                key
        );
        DatabaseReference reference = new FirebaseRef().chatList();
        reference.child(userData.user().getUid()).child(key).setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.success();
            }
        });

        reference.child(oterUser.user_id).child(key).setValue(chat);

    }
}
