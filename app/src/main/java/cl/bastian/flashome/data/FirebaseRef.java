package cl.bastian.flashome.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.data;

/**
 * Created by santo_000 on 15-11-2016.
 */

public class FirebaseRef {

    public DatabaseReference messages() {
        return root().child("messages");
    }

    private DatabaseReference root() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference users() {
        return root().child("users");

    }

    public DatabaseReference chatList() {
        return root().child("chat_list");
    }
}
