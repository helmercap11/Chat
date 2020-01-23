package com.helmercapassola.chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {



    private static int RC_SING_IN = 123;

    FirebaseAuth auth;
    DatabaseReference databaseReference;

    EditText email_login, passe_login;
    Button btn_acess;

    ScrollView scrollViewlogin, scrollViewNewCount;

    TextView createCount;


    EditText NewName, NewEmail, NewPass;
    TextView textView;
    Button btn_newcount;


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       // startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),RC_SING_IN);

        scrollViewlogin = findViewById(R.id.login_form);
        scrollViewNewCount = findViewById(R.id.create_form);
        progressDialog = new ProgressDialog(this);


        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        email_login =  findViewById(R.id.email_login);
        passe_login = findViewById(R.id.pass_login);
        btn_acess = findViewById(R.id.btn_acess);
        btn_acess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Authentication();
            }
        });

        createCount = findViewById(R.id.create_count);
        createCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        NewEmail = findViewById(R.id.new_email);
        NewName = findViewById(R.id.FullName);
        NewPass = findViewById(R.id.new_password);
        btn_newcount = findViewById(R.id.btn_create);
        btn_newcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = NewName.getText().toString();
                String newemail = NewEmail.getText().toString();
                String newpass = NewPass.getText().toString();


                RegisterCount(newname,newemail,newpass);
            }
        });

        textView = findViewById(R.id.txt_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollViewlogin.setVisibility(View.VISIBLE);
                scrollViewNewCount.setVisibility(View.GONE);
            }
        });
    }

    private  void Authentication(){


        String email = email_login.getText().toString();
        String password = passe_login.getText().toString();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show();
        }else {


            progressDialog = ProgressDialog.show(this,"Aguarde","Processando...");
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(LoginActivity.this, "Autenticado com sucesso", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                progressDialog.dismiss();
                                String message = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }


    private void RegisterCount(String newname, String newemail, String newpass){


        if (TextUtils.isEmpty(newname)){
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(newemail)){
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(newpass)){
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show();
        }else {

            FirebaseUser user = auth.getCurrentUser();
            DatabaseReference reference = databaseReference.push();
            reference.child("UserName").setValue(newname);
            progressDialog = ProgressDialog.show(this, "Aguarde", "Processando");
            auth.createUserWithEmailAndPassword(newemail,newpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(LoginActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                String message =task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser UserAcess = auth.getCurrentUser();
        if (UserAcess == null){
            Toast.makeText(this, "Iniciar a Sessão", Toast.LENGTH_SHORT).show();
        }
    }


}
