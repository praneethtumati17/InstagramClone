package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    EditText name,bio,hobbies,profession,sport;
    Button update;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        name=view.findViewById(R.id.name);
        bio=view.findViewById(R.id.bio);
        hobbies=view.findViewById(R.id.hobbies);
        profession=view.findViewById(R.id.profession);
        sport=view.findViewById(R.id.sport);
        update=view.findViewById(R.id.update);
        final ParseUser parseUser=ParseUser.getCurrentUser();
        if (parseUser.get("ProfileName") == null || parseUser.get("ProfileBio")==null || parseUser.get("Profileprofession") == null || parseUser.get("Profilesport") == null || parseUser.get("Profilehobbies") == null ) {
            name.setText("");
            bio.setText(parseUser.get("");
            hobbies.setText(parseUser.get("");
            profession.setText(parseUser.get("");
            sport.setText(parseUser.get("");

        }
        else{
            name.setText(parseUser.get("ProfileName").toString());
            bio.setText(parseUser.get("ProfileBio").toString());
            hobbies.setText(parseUser.get("Profileprofession").toString());
            profession.setText(parseUser.get("Profilehobbies").toString());
            sport.setText(parseUser.get("Profilesport").toString());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("ProfileName",name.getText().toString());
                parseUser.put("ProfileBio",bio.getText().toString());
                parseUser.put("Profileprofession",profession.getText().toString());
                parseUser.put("Profilehobbies",hobbies.getText().toString());
                parseUser.put("Profilesport",sport.getText().toString());
                final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),"info Updated",Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                        }
                        else{
                            FancyToast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;

    }
}
