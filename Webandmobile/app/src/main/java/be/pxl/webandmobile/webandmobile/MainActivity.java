package be.pxl.webandmobile.webandmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. custom:
        //Set Title:
        setupTitle();//TODO: get class and buss...

        //3.2: click event
        //3.2.1: select class:
        Button setClassButton = (Button) findViewById(R.id.setClass);
        setClassButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SelectClass.class)));

        //3.2.2: overview
        Button overviewButton = (Button) findViewById(R.id.overview);
        overviewButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Overview.class)));

        //3.2.3: schedule
        Button scheduleButton = (Button) findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Schedule.class)));

        //3.3.1: bus main
        Button busApiButton = (Button) findViewById(R.id.busApiButton);
        busApiButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, BusApi.class)));

        //3.3.2 bus test
        Button busApiTestButton = (Button) findViewById(R.id.busApiTestButton);
        busApiTestButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, BusApiTest.class)));
    }

    private void setupTitle() {
        Context context = getApplicationContext();
        SharedPreferences classPreferences = context.getSharedPreferences("class", context.MODE_PRIVATE);
        SharedPreferences busPreferences = context.getSharedPreferences("busApi", context.MODE_PRIVATE);

        String selectedClass = classPreferences.getString("className", "");
        String selectedBus = busPreferences.getString("busName", "");

        String add = "";

        if (!selectedClass.equals("")) {
            add += "Course: " + selectedClass;
        }

        if (!selectedBus.equals("")) {
            add += " Bus: " + selectedBus;
        }

        setTitle(getTitle()+"; " + add.trim());
    }
}
