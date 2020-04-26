package com.example.babybottle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.text.*;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Chronometer;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private FirebaseDatabase Datenbank = FirebaseDatabase.getInstance();
    private DatabaseReference Geber_Ref = Datenbank.getReference().child("Geber");
    private DatabaseReference Uhrzeit_Ref = Datenbank.getReference().child("Uhrzeit");
    private DatabaseReference Datum_Ref = Datenbank.getReference().child("Datum").child("x15xIY3s8u4xoZR5OZhQ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.Chronos);
        final TextView Geber_Text = (TextView) findViewById(R.id.Geber_Text);
        final TextView Uhrzeit_Text = (TextView) findViewById(R.id.Uhrzeit_Text);
        //Wenn etwas in der Datenbank geändert wird dann führe folgende Methode aus
        Geber_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x = dataSnapshot.getValue(String.class);
                Geber_Text.setText(x);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Uhrzeit_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long diff;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String x = dataSnapshot.getValue(String.class); //Zeit als Flasche gegeben wurde von der Datenbank ausgelesen
                String y = sdf.format(new Date()); //current time
                Uhrzeit_Text.setText(x);
                try{
                    Date date = sdf.parse(x); //mach aus String eine Zeit
                    Date currentTime=sdf.parse(y);
                    diff= currentTime.getTime() - date.getTime(); //ms
                    chronometer.setBase(SystemClock.elapsedRealtime()-diff);
                    chronometer.start();
                }catch(ParseException ex){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void Mama(View v) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String uhrzeit = sdf.format(new Date());
        chronometer = findViewById(R.id.Chronos);
        Geber_Ref.setValue("Letztes Fläschchen gegeben von Mama um:");
        Uhrzeit_Ref.setValue(uhrzeit);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void Papa(View v) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String uhrzeit = sdf.format(new Date());
        chronometer = findViewById(R.id.Chronos);
        Geber_Ref.setValue("Letztes Fläschchen gegeben von Papa um:");
        Uhrzeit_Ref.setValue(uhrzeit);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
//branch
        //branch2
    }

}
