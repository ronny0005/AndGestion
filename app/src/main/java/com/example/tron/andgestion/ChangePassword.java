package com.example.tron.andgestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;

import java.io.IOException;

public class ChangePassword extends AppCompatActivity {

    private Button valider;
    private TextView old,new1,new2;
    outils ou;
    Parametre param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ou = (outils) getIntent().getSerializableExtra("outils");

        ou.app=ChangePassword.this;
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        valider = (Button) findViewById(R.id.validerPass);
        old =(TextView) findViewById(R.id.old);
        new1 =(TextView) findViewById(R.id.new1);
        new2 =(TextView) findViewById(R.id.new2);

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!old.getText().toString().isEmpty() && !new1.getText().toString().isEmpty() && !new2.getText().toString().isEmpty()) {

                    if(param!=null){

                        if(param.getMdp().equals(old.getText().toString())){

                            Parametre parametre = null;
                            try {
                                 parametre = ou.changePassword(param.getUser(),old.getText().toString(),new1.getText().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(parametre!=null){
                                Toast.makeText(ChangePassword.this, " Mot de passe modifié avec succes",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePassword.this, MenuActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ChangePassword.this, "Modification echouée",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(ChangePassword.this, "Ancien Mot de passe incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(ChangePassword.this, "Veuillez saisir tous champs",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
