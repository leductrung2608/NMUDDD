package com.example.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.PurchaseOrder;
import com.example.app.R;
import com.example.app.admin.ConfirmedOrder;
import com.example.app.admin.OrderDetail;
import com.example.app.admin.UnconfirmedOrder;
import com.example.app.login.Login;
import com.example.app.map.Address;
import com.example.app.map.PlacePicker;
import com.example.app.model.OrderModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import at.markushi.ui.CircleButton;
import de.greenrobot.event.EventBus;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    CircleButton btt_logout;
    TextView username, address, phoneNo, email, lb_username, lb_email;



    Button bill;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    ImageView imageProfile, btt_address, btt_phoneNo, btt_location;
    StorageTask uploadTask;

    CardView cv_phoneNo, cv_address;

    public ProfileFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        StorageReference profileRef = storageReference.child("Users/"+ fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageProfile);
            }
        });

        btt_logout = view.findViewById(R.id.btt_logout);
        username = view.findViewById(R.id.tv_username);
        phoneNo = view.findViewById(R.id.tv_phoneNumber);
        address = view.findViewById(R.id.tv_address);
        email = view.findViewById(R.id.tv_email);
        lb_email = view.findViewById(R.id.lb_email);
        lb_username = view.findViewById(R.id.lb_username);
        imageProfile = view.findViewById(R.id.imageProfile);
        cv_phoneNo = view.findViewById(R.id.cv_phoneNo);
        cv_address = view.findViewById(R.id.cv_address);
        btt_address = view.findViewById(R.id.btt_editaddress);
        btt_phoneNo = view.findViewById(R.id.btt_editphoneNo);
        btt_location = view.findViewById(R.id.btt_location);
        bill = view.findViewById ( R.id.bill );

        //Get data user
        DocumentReference df = fStore.collection("Users").document(userId);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("Username"));
                    lb_username.setText(documentSnapshot.getString("Username"));
                    address.setText(documentSnapshot.getString("Address"));
                    email.setText(documentSnapshot.getString("Email"));
                    lb_email.setText(documentSnapshot.getString("Email"));
                    phoneNo.setText(documentSnapshot.getString("PhoneNumber"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });

        //Logout
        btt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
                googleSignInClient.signOut();

                LoginManager.getInstance().logOut();

                fAuth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        //Change image profile
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        //Edit phoneNo and address
        btt_phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPhoneNo();
            }
        });

        btt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddress();
            }
        });

        //Get location by map
        btt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlacePicker.class));
            }
        });

        bill.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity ().getApplicationContext (), PurchaseOrder.class );
                startActivity ( i );
               // startActivity(new Intent(getActivity(), PurchaseOrder.class));

            }
        } );

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Uri imageUri = data.getData();

            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {

        StorageReference fileRef = storageReference.child("Users/"+ fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(imageProfile);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);


        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        uploadTask = fileRef.putFile(imageUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return fileRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", ""+mUri);
                    reference.updateChildren(map);

                    pd.dismiss();
                } else {
                    pd.dismiss();
                }
            }
        });


    }

    public void openDialogPhoneNo(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phoneno);
        Button btt_done = dialog.findViewById(R.id.btt_done);
        EditText edPhoneNo = dialog.findViewById(R.id.ed_address);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams wLayoutParams = window.getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(wLayoutParams);

        btt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPhoneNo.getText().toString().matches(""))
                {

                }else{
                    phoneNo.setText(edPhoneNo.getText().toString());
                    fStore.collection("Users").document(userId).update("PhoneNumber", phoneNo.getText());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openDialogAddress(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_address);
        Button btt_done = dialog.findViewById(R.id.btt_done);
        EditText edAddress= dialog.findViewById(R.id.ed_address);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams wLayoutParams = window.getAttributes();
        wLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(wLayoutParams);

        btt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edAddress.getText().toString().matches(""))
                {

                }else{
                    address.setText(edAddress.getText().toString());
                    fStore.collection("Users").document(userId).update("Address", address.getText());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Auto get address
    public void onAddressClick(View view){
        startActivity(new Intent(getActivity(), PlacePicker.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);

    }

    public void onEventMainThread(Address event) {
        address.setText(event.getAddress());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



}