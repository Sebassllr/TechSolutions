package com.example.usuario.techsolutions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.usuario.techsolutions.MainActivity.calledAlready;

public class Transacciones {

    /////////////////////
    //Variables
    ////////////////////

    public DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public StorageReference mStorage;
    public FirebaseUser user ;
    ///////////////////
    //Constructor
    //////////////////

    public Transacciones() { }

    /////////////////////
    //Metodos
    ////////////////////

    /**
     * Metodo que inserta cualquier objeto en la base de datos en el nodo que se pasa como
     * parameto
     *  @param childDatabaseR direccion del nodo donde se guardará el objeto
     * @param key            llave primaria del objeto
     * @param object         objeto a guardar
     */
    private Task<Void> insertar(String childDatabaseR, String key, Object object) {
        return databaseReference.child(childDatabaseR).child(key).setValue(object);
    }

    public void registrarArtículo(String title, String autor, String content, String idOwner, final String key){
        Article article = new Article(autor, title, idOwner, content);

        insertar(Article.ARTICLE_NODE_NAME, key, article).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void inicializatedFireBase(Context context){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    public void account(final Context context){
        eventSelect(Usuario.USER_NODE_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = firebaseAuth.getCurrentUser();
                context.startActivity(new Intent(context, MainFragentActivity.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Metodo que selecciona un dato referenciado el cua se pasa por parametro
     * @param usChildString
     */
    private DatabaseReference eventSelect(String usChildString){
        return databaseReference.child(usChildString);
    }

    public void registrarUser(final String email, final String password, final Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando usuario, por favor espera");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    ingresarNuevo(email, password, context);
                }else{
                    progressDialog.dismiss();
                }

            }
        });
    }

    private void ingresarNuevo(String email, String password, final Context context) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Usuario uE = new Usuario( user.getUid(), user.getEmail());
                    insertar(Usuario.USER_NODE_NAME,user.getUid(),uE).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                context.startActivity(new Intent(context, MainFragentActivity.class));
                                progressDialog.dismiss();
                            }else{
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
