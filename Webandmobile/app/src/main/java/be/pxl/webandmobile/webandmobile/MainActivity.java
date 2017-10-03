package be.pxl.webandmobile.webandmobile;

import android.content.Intent;
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
        TitleClass.initialise(getTitle().toString());//init only once!

        //2.1 fill in class:
        String selectedClass = "???";
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
    }
}
