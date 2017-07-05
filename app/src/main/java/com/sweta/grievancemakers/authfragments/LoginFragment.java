package com.sweta.grievancemakers.authfragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sweta.grievancemakers.Pages.Auth;
import com.sweta.grievancemakers.Pages.ChatActivity;
import com.sweta.grievancemakers.R;


public class LoginFragment extends Fragment {
    ImageView profile_pic;
    TextView welcome_text;
    TextView signup_text;
    EditText email;
    EditText password;
    Button sign_in;
    Button sign_up;
    private ProgressDialog mProg;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        profile_pic = (ImageView) view.findViewById(R.id.profile_picture);
        welcome_text = (TextView) view.findViewById(R.id.greeting_text);
        signup_text = (TextView) view.findViewById(R.id.sign_in_text);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        sign_in = (Button) view.findViewById(R.id.button_sign_in);
        sign_up = (Button) view.findViewById(R.id.button_sign_up);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mProg = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.viewPager.setCurrentItem(1);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("Database Link", mdatabase.getRef().toString());


                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                    if (!(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password))) {
                        mProg.setMessage("Signing you in....");
                        mProg.show();
                        mAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                startActivity(intent);
                                mProg.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                mProg.dismiss();
                                Toast.makeText(getActivity(), "Can't sign you in.." + e.getLocalizedMessage()
                                        , Toast.LENGTH_SHORT).show();


                            }
                        });


                    } else {
                        Toast.makeText(getActivity(), "Can't sign you in", Toast.LENGTH_SHORT).show();
                    }


                }

            });
        return view;

        }
    }

