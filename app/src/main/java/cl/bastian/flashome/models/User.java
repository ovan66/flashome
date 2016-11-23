package cl.bastian.flashome.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by santo_000 on 15-11-2016.
 */

@IgnoreExtraProperties
public class User {

    public String name, email, photo, user_id;

    public User() {
    }

    public User(String name, String email, String photo, String user_id) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.user_id = user_id;
    }
}
