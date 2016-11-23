package cl.bastian.flashome.models;

/**
 * Created by santo_000 on 16-11-2016.
 */

public class Chat {
    public String chat_id,creator,creator_photo,receiver, receiver_photo;
    public boolean notification;
    public long timestamp;

    public Chat() {
    }

    public Chat(boolean notification, String receiver_photo, String receiver, String creator_photo, String creator, String chat_id) {
        this.notification = notification;
        this.receiver_photo = receiver_photo;
        this.receiver = receiver;
        this.creator_photo = creator_photo;
        this.creator = creator;
        this.chat_id = chat_id;
    }
}


