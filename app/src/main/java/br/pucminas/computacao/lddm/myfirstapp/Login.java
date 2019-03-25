package br.pucminas.computacao.lddm.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {

    LoginButton fb_login;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        initializeControls();
        loginFB();

    }

    public void ActivitySalvarContato (View view){
        finish();
    }

    private void initializeControls() {
        callbackManager = CallbackManager.Factory.create();
        fb_login = (LoginButton)findViewById(R.id.fb_login_button);
    }

    private void loginFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login realizado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login cancelado", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage("Login Cancelado!")
                        .setPositiveButton("OK", null);

                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Erro ao fazer login", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage("Erro ao fazer o login!")
                        .setPositiveButton("OK", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*
    private static RestrictTo.Scope buildScope() {
        return RestrictTo.Scope.build(RestrictTo.Scope.R_BASICPROFILE, RestrictTo.Scope.R_EMAILADDRESS);
    }
    */

}
