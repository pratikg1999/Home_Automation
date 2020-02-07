package com.example.homeautomation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements SignupFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {
    FirebaseAuth mAuth;
    FirebaseUser curUser;

    public static final String CHANNEL_ID = "NOTIFICATION CHANNEL";
    public static final String SUBSCRIBE_TO = "updates";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();


//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, task.getResult().getId(), Toast.LENGTH_SHORT).show();
//                    Log.d("FCM", "onComplete: token" + task.getResult().getToken());
//                    Log.d("FCM", "onComplete: instanceid" + task.getResult().getId());
//                    String token = task.getResult().getToken();
//                }
//            }
//        });
//        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);


        FragmentManager fragmentManager = getSupportFragmentManager();
        if(curUser==null) {
            LoginFragment loginFragment = LoginFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.llContainer, loginFragment).commit();
//            SignupFragment signupFragment = SignupFragment.newInstance();
//            fragmentManager.beginTransaction().replace(R.id.llContainer, signupFragment).commit();
        }else{
            startActivity(new Intent(MainActivity.this, SmartHome.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void switchFragment() {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.llContainer);
        if(curFragment instanceof LoginFragment){
            Fragment fragment = SignupFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.llContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (curFragment instanceof SignupFragment){
            Fragment fragment = LoginFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.llContainer, fragment).addToBackStack(null).commit();
        }
    }
}
