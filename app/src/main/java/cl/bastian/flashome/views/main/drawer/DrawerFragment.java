package cl.bastian.flashome.views.main.drawer;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.util.FirebaseAuthWrapper;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

import cl.bastian.flashome.R;
import cl.bastian.flashome.views.login.FullscreenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {

    private MagicalCamera magicalCamera;
    private CircularImageView avatarView;


    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.logoutTv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

        });

        avatarView = (CircularImageView) view.findViewById(R.id.userAvatar);
        PhotoData photoData = new PhotoData(getContext());
        if (photoData.getUrl() == null) {
            selfie();
        } else {
            Picasso.with(getContext()).load(photoData.getUrl()).into(avatarView);
            TextView nickTv = (TextView) view.findViewById(R.id.userNick);
            nickTv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        }

    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void selfie() {
        magicalCamera = new MagicalCamera(getActivity(), 500);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("tomate una selfie");
        dialog.setMessage("Para completar tu perfil debes tomarte una foto");
        dialog.setPositiveButton("selfie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (magicalCamera.takeFragmentPhoto()) {
                    startActivityForResult(magicalCamera.getIntentFragment(), MagicalCamera.TAKE_PHOTO);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            magicalCamera.resultPhoto(requestCode, resultCode, data);
            avatarView.setImageBitmap(magicalCamera.getMyPhoto());
            String name = "flash_avatar_" + System.currentTimeMillis();
            magicalCamera.savePhotoInMemoryDevice(magicalCamera.getMyPhoto(),name,"flashMain", MagicalCamera.JPEG, false);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            final String photoServer = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@", "_at_").replace(".", "_dot_") + ".jpeg";
            String refUrl = "gs://flashome-6a9ab.appspot.com/avatars/"+photoServer;
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(refUrl);
            File file = new File ("/storage/emulated/0/Pictures/flashMain/"+name+".jpeg");

            storageReference.putFile(Uri.fromFile(file)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String url = "https://firebasestorage.googleapis.com/v0/b/flashome-6a9ab.appspot.com/o/avatars%2F"+ photoServer +"?alt=media";
                    new PhotoData(getContext()).saveUrl(url);


                }
            });


        } else {
            selfie();

        }
    }
}