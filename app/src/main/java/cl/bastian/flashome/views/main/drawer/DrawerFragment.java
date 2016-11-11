package cl.bastian.flashome.views.main.drawer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.bastian.flashome.R;
import cl.bastian.flashome.views.login.FullscreenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {

    private MagicalCamera magicalCamera;


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
    }

    @Override
    public void onStart() {
        super.onStart();
        PhotoData photoData = new PhotoData(getContext());
        if (photoData.getUrl() == null) {
            selfie();
        } else {

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

    private void selfie (){
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

        } else {

        }
    }}