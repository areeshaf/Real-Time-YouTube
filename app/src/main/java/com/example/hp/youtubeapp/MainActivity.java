package com.example.hp.youtubeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends YouTubeBaseActivity {
Button b,b2;
    YouTubePlayerView y;
    YouTubePlayer.OnInitializedListener yo;
    ApiFetcher apiFetcher=new ApiFetcher();
EditText et;TextView tv;

    FirebaseDatabase obj=FirebaseDatabase.getInstance();
    DatabaseReference reference=obj.getReference();//rootnode ref
    DatabaseReference rr=reference.child("Video");//take ref of child

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        y=(YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        et=(EditText) findViewById(R.id.editText);
        tv=(TextView) findViewById(R.id.textView);


    }
    public void click(View view)
    {

        String string=et.getText().toString();
        rr.setValue(string);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    String s=dataSnapshot.getValue(String.class);
                    tv.setText(s);
                    yo= new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(tv.getText().toString());
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    };
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            y.initialize(apiFetcher.apik,yo);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
