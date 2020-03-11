package com.bitspilani.apogeear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitspilani.apogeear.Models.Event_Details;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail,userPassword;
    private Button loginBtn,button;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    String email,password;

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        button = findViewById(R.id.googlelogin);
        userEmail=findViewById(R.id.user_email);
        userPassword=findViewById(R.id.password);
        loginBtn=findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db1=FirebaseFirestore.getInstance();
        if (user != null) {

            SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", user.getUid());
            editor.apply();

            if(sharedPref.getString("char","").equals("")) {
                db1.collection("Users").document(user.getUid()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot DocumentSnapshot) {
                                if(DocumentSnapshot.get("char")==null) {
                                    SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("char", DocumentSnapshot.get("char").toString());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, CharSelect.class));
                                    finish();
                                }
                                else if (DocumentSnapshot.get("char").toString().equals("none")){
                                    SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("char", DocumentSnapshot.get("char").toString());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, CharSelect.class));
                                    finish();
                                }
                                else{
                                    SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("char", DocumentSnapshot.get("char").toString());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }

                            }
                        });

            }
            else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                        Log.d("here", "here");
                    }
                });
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=userEmail.getText().toString();
                password=userPassword.getText().toString();
                firebaseAuthwithemail();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("here", "here");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email1=account.getEmail();
                if(email1.substring(email1.indexOf('@')+1).equals("pilani.bits-pilani.ac.in"))
                    firebaseAuthWithGoogle(account);
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    mGoogleSignInClient.signOut();
                    mAuth.signOut();
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void firebaseAuthwithemail()
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIOut(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            updateUIOut(null);
                        }

                    }
                });
    }

    private void updateUIOut(FirebaseUser user){
        if (user != null) {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection("Users").whereEqualTo("username", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.getResult().getDocuments().isEmpty()) {

                        SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", user.getUid());
                        editor.putString("char","none");
                        editor.apply();

                        ArrayList<String> types=new ArrayList<>();
                        ArrayList<Event_Details> events=new ArrayList<>();
                        Map<String, Object> data = new HashMap<>();
                        email = email.trim();
                        int index = email.indexOf("@");
                        String name = email.substring(0,index);
                        data.put("email", email);
                        data.put("name", name);
                        data.put("char","none");
                        data.put("username", user.getUid());
                        data.put("score", 0.0);
                        data.put("slot_time", FieldValue.serverTimestamp());
                        StringTokenizer stringTokenizer = new StringTokenizer(user.getEmail(), "@");
                        String qrcode = stringTokenizer.nextToken();
                        data.put("qr_code", qrcode);
                        data.put("Types",types);
                        data.put("Attended",events);

                        db.collection("Users").document(user.getUid()).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this,  CharSelect.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Connection error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        email = email.trim();
                        int index = email.indexOf("@");
                        String name = email.substring(0,index);
                        SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", name);
                        editor.putString("char","none");
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, CharSelect.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection("Users").whereEqualTo("username", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.getResult().getDocuments().isEmpty()) {

                        SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", user.getUid());
                        editor.apply();

                        ArrayList<String> types=new ArrayList<>();
                        ArrayList<Event_Details> events=new ArrayList<>();
                        Map<String, Object> data = new HashMap<>();
                        data.put("email", user.getEmail());
                        data.put("name", user.getDisplayName());
                        data.put("char","none");
                        data.put("username", user.getUid());
                        data.put("score", 0.0);
                        data.put("slot_time", FieldValue.serverTimestamp());
                        StringTokenizer stringTokenizer = new StringTokenizer(user.getEmail(), "@");
                        String qrcode = stringTokenizer.nextToken();
                        data.put("qr_code", qrcode);
                        data.put("Types",types);
                        data.put("Attended",events);

                        db.collection("Users").document(user.getUid()).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this,  CharSelect.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.putExtra("user_id",user.getUid());
                                    startActivity(i);

                                    finish();
                                } else {

                                    Toast.makeText(LoginActivity.this, "Connection error!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                    else{

                        SharedPreferences sharedPref=getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", user.getUid());
                        editor.putString("char","none");
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, CharSelect.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
    }
}


