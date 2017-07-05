package com.sweta.grievancemakers.authfragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sweta.grievancemakers.FireChatHelper.ChatHelper;
import com.sweta.grievancemakers.MainActivity;
import com.sweta.grievancemakers.Pages.ChatActivity;
import com.sweta.grievancemakers.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterFragment extends Fragment {
    EditText location;
    EditText email;
    EditText mobile_number;
    EditText gender;
    EditText date_of_birth;
    EditText username;
    EditText password;
    Button submit;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private android.app.AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        location = (EditText) view.findViewById(R.id.location);
        email = (EditText) view.findViewById(R.id.email);
        mobile_number = (EditText) view.findViewById(R.id.number);
        gender = (EditText) view.findViewById(R.id.gender);
        date_of_birth = (EditText) view.findViewById(R.id.date_of_birth);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        submit = (Button) view.findViewById(R.id.submit);
        setAuthInstance();
        setDatabaseInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterUser();
                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/

            }
        });
        return view;
    }

    private void setAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void setDatabaseInstance() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void onRegisterUser() {
        if (getUserName().equals("") || getUserEmail().equals("") || getUserPassword().equals("")) {
            showFieldsAreRequired();
        } else if (isIncorrectEmail(getUserEmail()) || isIncorrectPassword(getUserPassword())) {
            showIncorrectEmailPassword();
        } else {
            signUp(getUserEmail(), getUserPassword());
        }
    }

    private boolean isIncorrectEmail(String userEmail) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }

    private boolean isIncorrectPassword(String userPassword) {
        return !(userPassword.length() >= 6);
    }

    private void showIncorrectEmailPassword() {
        showAlertDialog(getString(R.string.error_incorrect_email_pass), true);
    }

    private void showFieldsAreRequired() {
        showAlertDialog(getString(R.string.error_fields_empty), true);
    }

    private void showAlertDialog(String message, boolean isCancelable) {

        dialog = ChatHelper.buildAlertDialog(getString(R.string.login_error_title), message, isCancelable, getActivity());
        dialog.show();
    }

    private String getUserName() {
        return username.getText().toString().trim();
    }

    private String getUserEmail() {
        return email.getText().toString().trim();
    }

    private String getUserPassword() {
        return password.getText().toString().trim();
    }

    private String getUserLocation() {
        return location.getText().toString().trim();
    }


    private void signUp(final String email, String password) {

        // showAlertDialog("Registering...",true);
//        mProgress.setMessage("Registering you....");
        //mProgress.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                DatabaseReference Users_ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());

                String date = new SimpleDateFormat("dd/mm/yyyy hh:mma").format(new Date(System.currentTimeMillis()));

                Log.v("Date", date);

                Users_ref.child("displayName").setValue(username.getText().toString().trim());
                Users_ref.child("email").setValue(email);
                Users_ref.child("createdAt").setValue(date);
                Users_ref.child("uid").setValue(mAuth.getCurrentUser().getUid());
                Intent chat = new Intent(getActivity(), ChatActivity.class);
                getActivity().finish();
                startActivity(chat);
                /*Users_ref.child("connection").setValue("online").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                       //mProgress.dismiss();
                        Intent chat = new Intent(getActivity(), ChatActivity.class);
                        getActivity().finish();
                        startActivity(chat);

                    }
                });*/


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                //mProgress.dismiss();
                Log.v("Error", e.getLocalizedMessage());

            }
        });


    }
}



