package be.pxl.webandmobile.webandmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String FILE = "uurrooster_file";
    private FileOutputStream fileOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. custom:
        TitleClass.initialise(getTitle().toString());//init only once!

        //2.1 fill in class:
        String selectedClass = "";
        if (selectedClass == null) {
            selectedClass = "geen klas geselecteerd";
        }
        setTitle(TitleClass.getCustomisedTitle(selectedClass));

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

        //3.2.4: Google calender
        Button googleButton = (Button) findViewById(R.id.googleCalender);
        googleButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GoogleCalenderAPI.class)));
    }

}