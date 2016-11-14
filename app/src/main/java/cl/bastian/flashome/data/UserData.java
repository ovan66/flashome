package cl.bastian.flashome.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by santo_000 on 14-11-2016.
 */

public class UserData {
    public FirebaseUser user (){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String email() {
        return user().getEmail();
    }


}
