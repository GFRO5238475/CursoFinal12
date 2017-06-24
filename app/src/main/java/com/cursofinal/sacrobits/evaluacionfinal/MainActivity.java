package com.cursofinal.sacrobits.evaluacionfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {

    //VARIABLES
    private LoginButton loginButton; //Boton reservado de Facebook
    private CallbackManager callbackManager;//Accion de llamada
    private AdView adView;//Visor de Anuncios


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sdk Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //Intancia entre lo visual y lo logico
        loginButton = (LoginButton)findViewById(R.id.login_facebook);
        adView = (AdView)findViewById(R.id.ad_view);

        //consulta si esta o no iniciada la sesion
        if(AccessToken.getCurrentAccessToken() != null){//si no está vacia que abra los datos del usuario
            IrAFacebook();
        }else{//sino que ingrese el correo y la contraseña
            //Acciones en la hora del ingreso de datos: Exitoso, Cancelado, Fallido
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {//si los datos son correctos
                    Toast.makeText(MainActivity.this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show();
                    IrAFacebook();
                }

                @Override
                public void onCancel() {//si se cancela el inicio
                    Toast.makeText(MainActivity.this, "¡Inicio de sesión cancelado!", Toast.LENGTH_SHORT).show();Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {//si da un error al ingresar datos
                    Toast.makeText(MainActivity.this, "¡Inicio de sesión NO exitoso!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Mostrar Anuncio Ads
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

    }
    //Lanzamiento de la Aplicacion Facebook
    private void IrAFacebook(){
        Intent facebook = new Intent(MainActivity.this, Facebook.class);
        finish();
        startActivity(facebook);
    }
    //Actividad
    protected void onActivityResult(int reqCode, int resCode, Intent i){
        super.onActivityResult(reqCode,resCode,i);
        callbackManager.onActivityResult(reqCode, resCode, i);
    }



    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

}
