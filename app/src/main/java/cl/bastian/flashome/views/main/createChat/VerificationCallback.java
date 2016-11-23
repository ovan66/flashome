package cl.bastian.flashome.views.main.createChat;

/**
 * Created by santo_000 on 15-11-2016.
 */

public interface VerificationCallback {

    void invalid (String error);
    void selft (String error);
    void notFound(String error);
    void success ();

}
