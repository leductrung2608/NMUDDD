package com.example.app.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.RegisterUserClass;
import com.example.app.admin.Admin;
import com.example.app.util.MyAppContext;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class Login extends AppCompatActivity {
    EditText email, pass;
    CircularProgressButton btt_login;
    SignInButton gg;
    LoginButton fb;
    TextView tv_forgot;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    StorageReference storageReference;
    final static int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        changeStatusBarColor();
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        email = findViewById(R.id.editTextEmail2);
        pass = findViewById(R.id.editTextPassword2);
        btt_login = findViewById(R.id.cirLoginButton);
        gg = findViewById(R.id.gg);
        fb = findViewById(R.id.fb);
        tv_forgot = findViewById(R.id.tv_forgotpass);


        btt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEnteredLogin()){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //Login GG
        createRequest();
        gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Login FB
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fb.setReadPermissions("email", "public_profile");
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TAG", "onError" + error);
            }
        });

        //Forgot Password
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
    }

    private  void handleFacebookToken(AccessToken token){
        Log.d("TAG", "handleFacebookToken" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential((token.getToken()));
        fAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Facebook successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = fAuth.getCurrentUser();
                    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                    if (isNew){
                        createNewUser(user);
                        String userId = user.getUid ();
                        register ( userId );
                    }

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();

                }else{
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Check admin or user
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess:" + documentSnapshot.getData());

                if (documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    finish();
                }

                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//          window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.login_bk_color));
        }
    }

    private boolean checkDataEnteredLogin(){
        String memail = email.getText().toString();
        String mpass = pass.getText().toString();

        if (memail.isEmpty()){
            email.setError("Required");
            return false;
        }else if(mpass.isEmpty()){
            pass.setError("Required");
            return false;
        }else {
            return true;
        }
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, Register.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAdmin") != null){
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                        finish();
                    }

                    if (documentSnapshot.getString("isUser") != null){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            });

        }
    }

    //Login GG
    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Login Google successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();

                            //check login first time
                            if (isNew){
                                createNewUser(user);
                                String userId = user.getUid ();
                                register ( userId );
                            }

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, (CharSequence) task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createNewUser(FirebaseUser user){
        StorageReference fileRef = storageReference.child("Users/"+ fAuth.getCurrentUser().getUid() + "/profile.jpg");

        DocumentReference df = fStore.collection("Users").document(user.getUid());
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Username", user.getDisplayName());
        userInfo.put("PhoneNumber", user.getPhoneNumber());
        userInfo.put("Email",user.getEmail());
        userInfo.put("Address", " ");

        //if the user is admin
        userInfo.put("isUser", "1");

        df.set(userInfo);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getUid());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", fAuth.getUid());
        hashMap.put("username", user.getDisplayName());
        hashMap.put("search", user.getDisplayName().toLowerCase());
        hashMap.put("imageURL", "default");
        hashMap.put("status", "offline");
        hashMap.put("isAdmin", "0");

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }
            }
        });

    }

    public void register(String id) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass ( );


            @Override
            protected void onPreExecute() {
                super.onPreExecute ( );
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute ( s );
                Toast.makeText( Login.this, "Add product successfully!!", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String> ( );


                data.put ( "IdUser", id );


                String result = ruc.sendPostRequest ( "https://ibeautycosmetic.000webhostapp.com/getUser.php", data );

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers ( );
        ru.execute ( id );

    }
}
