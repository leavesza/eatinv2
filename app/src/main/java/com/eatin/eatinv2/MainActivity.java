package com.eatin.eatinv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eatin.eatinv2.Common.Common;
import com.eatin.eatinv2.Model.UserModel;
import com.eatin.eatinv2.Remote.ICloudFunctions;
import com.eatin.eatinv2.Remote.RetrofitCloudClient;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {


    private String TAG = "TAG";
    private static int APP_REQUEST_CODE = 7171;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private android.app.AlertDialog dialog;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ICloudFunctions cloudFunctions;
    private List<AuthUI.IdpConfig> providers;

    private DatabaseReference userRef;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if(listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();
        providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());
        listener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null)
            {
                //Toast.makeText(MainActivity.this, "already logged in", Toast.LENGTH_SHORT).show();
                checkUserFromFirebase(user);
            }
            else
            {
                phoneLogin();
            }
        };
    }

    private void checkUserFromFirebase(FirebaseUser user) {
        dialog.show();
        Log.d(TAG,"dialog shown");
        userRef.child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(MainActivity.this, "You already registered", Toast.LENGTH_SHORT).show();
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            goToHomeActivity(userModel);
                        } else {
                            showRegisterDialog(user);
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRegisterDialog(FirebaseUser user) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Register");
        builder.setMessage("Please fill information");

        View itemView = LayoutInflater.from(this).inflate(R.layout.layout_register, null);
        EditText edit_name = (EditText) itemView.findViewById(R.id.edt_name);
        EditText edit_address = (EditText) itemView.findViewById(R.id.edt_address);
        EditText edit_phone = (EditText) itemView.findViewById(R.id.edt_phone);

        //set
        edit_phone.setText(user.getPhoneNumber());

        builder.setView(itemView);
        builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("REGISTER", (dialogInterface, i) -> {
            if (TextUtils.isEmpty(edit_name.getText().toString())) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(edit_address.getText().toString())) {
                Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
                return;

            }
            UserModel userModel = new UserModel();
            userModel.setUid(user.getUid());
            userModel.setName(edit_name.getText().toString());
            userModel.setAddress(edit_address.getText().toString());
            userModel.setPhone(edit_phone.getText().toString());

            userRef.child(user.getUid())
                    .setValue(userModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialogInterface.dismiss();
                                Toast.makeText(MainActivity.this, "Congrats! Registration successful!", Toast.LENGTH_SHORT).show();
                                goToHomeActivity(userModel);

                            }
                        }
                    });
        });

        builder.setView(itemView);


        //show dialog
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void goToHomeActivity(UserModel userModel) {
        Common.currentUser = userModel; //IMPORTANT , you always need to assign value for it before use

        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();



    }

    private void phoneLogin() {

        Log.d(TAG,"Inside start phoneLogin()");
        startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                APP_REQUEST_CODE);
        Log.d(TAG,"Passed phonelogin()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"start activity result");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG,"result code was ok");
            }
            else
                {
                Toast.makeText(this, "Failed to sign in !", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"failed to sign in");
            }
        }
        Log.d(TAG,"Passed activity result method");


    }

}

