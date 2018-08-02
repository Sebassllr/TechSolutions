package com.example.usuario.techsolutions;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static boolean calledAlready = false;

    private Transacciones tr = new Transacciones();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        tr.inicializatedFireBase();
    }

    public void initializer(){
        findViewById(R.id.btnIngresar).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        Integer id = v.getId();

        if(validateFields()){
            String email = Util.getTxt((EditText) findViewById(R.id.tvName));
            String pasword = Util.getTxt((EditText) findViewById(R.id.tvPassword));

            switch (id){
                case R.id.btnIngresar: {
                    loginUser(email, pasword);
                    break;
                }
                case R.id.btnRegister: {
                    tr.registrarUser(email,pasword,this);
                    break;
                }
            }
        }
    }

    private void loginUser(String mail, String pass){
        progressDialog.setMessage("Ingresando, por favor espera");
        progressDialog.show();

        tr.firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            tr.account(getApplicationContext());

                        }else{
                            Toast.makeText(getApplicationContext(),"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public Boolean validateFields(){

        if(Util.emptyFieldMSG((EditText) findViewById(R.id.tvName), "Ingrese el campo") ||
                Util.emptyFieldMSG((EditText) findViewById(R.id.tvPassword), "Ingrese el campo")){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
