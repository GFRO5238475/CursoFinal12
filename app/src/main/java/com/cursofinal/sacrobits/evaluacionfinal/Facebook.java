package com.cursofinal.sacrobits.evaluacionfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by SacroBits on 23/06/2017.
 */

public class Facebook extends Activity {
    //VARIABLES
    private TextView nombreUsuario;
    private ProfilePictureView fotoUsuario;
    private Button btnCerrarSesion;
    private ProfileTracker mProfileTracker;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook);
        //Intancia entre lo visual y lo logico
        nombreUsuario = (TextView)findViewById(R.id.usuarioNombre);
        fotoUsuario = (ProfilePictureView)findViewById(R.id.usuarioFoto);
        btnCerrarSesion = (Button)findViewById(R.id.btnCerrarSesion);

        //Si hay una cuenta activa que estire los datos del usuario, sino, vuelva al principal
        if(AccessToken.getCurrentAccessToken() == null){
            IrPantallaLogin();
        }else{
            //si el perfil haya cambiado
            /*if(Profile.getCurrentProfile() == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        // profile2 es el nuevo perfil
                        nombreUsuario.setText(profile2.getName());
                        fotoUsuario.setProfileId(profile2.getId());
                        mProfileTracker.stopTracking();
                    }
                };
            }
            else {//si no ha cambiado
                Profile profile = Profile.getCurrentProfile();
                nombreUsuario.setText(profile.getName());
                fotoUsuario.setProfileId(profile.getId());
            }*/
            Profile profile = Profile.getCurrentProfile();
            nombreUsuario.setText(profile.getName());
            fotoUsuario.setProfileId(profile.getId());
        }
        //boton cerrar sesion
        btnCerrarSesion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                LoginManager.getInstance().logOut();
                IrPantallaLogin();
            }
        });

    }
    //ir de vuelta al mainactivity
    private void IrPantallaLogin(){
        Intent intent = new Intent(Facebook.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
