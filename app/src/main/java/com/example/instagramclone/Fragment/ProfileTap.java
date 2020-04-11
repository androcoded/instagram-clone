package com.example.instagramclone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import static com.parse.Parse.getApplicationContext;


public class ProfileTap extends Fragment implements View.OnClickListener {

    private EditText edtProfileName,edtProfileBio,edtProfileProfession,edtProfileHobbies,edtProfileFavSport;
    private Button btnProfileUpdate;
    private ParseUser mUser;
    public ProfileTap() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_tap, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavSports);
        btnProfileUpdate = view.findViewById(R.id.btnProfileUpdate);
        btnProfileUpdate.setOnClickListener(this);
        mUser = ParseUser.getCurrentUser();
        updatingProfileTab();
        return view;
    }

    @Override
    public void onClick(View v) {

        mUser.put("profileName",edtProfileName.getText().toString());
        mUser.put("profileBio",edtProfileBio.getText().toString());
        mUser.put("profileProfession",edtProfileProfession.getText().toString());
        mUser.put("profileHubbies",edtProfileHobbies.getText().toString());
        mUser.put("profileFavouriteSport",edtProfileFavSport.getText().toString());
        mUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(getApplicationContext(), mUser.getUsername() + " is successfully updated!",
                            Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                } else {
                    FancyToast.makeText(getApplicationContext(), e.getMessage() + "",
                            Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                }

            }
        });
    }
    private void updatingProfileTab(){
        if (mUser.get("profileName")!=null){
            edtProfileName.setText(mUser.get("profileName").toString());
        }else{
            edtProfileName.getText().clear();
        }
        if (mUser.get("profileBio")!=null){
            edtProfileBio.setText(mUser.get("profileBio").toString());
        }else{
            edtProfileBio.getText().clear();
        }
        if (mUser.get("profileProfession")!=null){
            edtProfileProfession.setText(mUser.get("profileProfession").toString());
        }else{
            edtProfileProfession.getText().clear();
        }
        if (mUser.get("profileHubbies")!=null){
            edtProfileHobbies.setText(mUser.get("profileHubbies").toString());
        }else{
            edtProfileHobbies.getText().clear();
        }
        if (mUser.get("profileFavouriteSport")!=null){
            edtProfileFavSport.setText(mUser.get("profileFavouriteSport").toString());
        }else{
            edtProfileFavSport.getText().clear();
        }

    }
}
