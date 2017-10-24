package be.pxl.webandmobile.webandmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //link days to arrays:
        TextView[] busMorning = {(TextView) findViewById(R.id.maandagBusToSchool), (TextView) findViewById(R.id.dinsdagBusToSchool), (TextView) findViewById(R.id.woensdagBusToSchool), (TextView) findViewById(R.id.donderdagBusToSchool), (TextView) findViewById(R.id.vrijdagBusToSchool)};

        EditText[] maandag = {(EditText) findViewById(R.id.maandag1), (EditText) findViewById(R.id.maandag2), (EditText) findViewById(R.id.maandag3), (EditText) findViewById(R.id.maandag4), (EditText) findViewById(R.id.maandag5), (EditText) findViewById(R.id.maandag6), (EditText) findViewById(R.id.maandag7), (EditText) findViewById(R.id.maandag8), (EditText) findViewById(R.id.maandag9), (EditText) findViewById(R.id.maandag10)};
        EditText[] dinsdag = {(EditText) findViewById(R.id.dinsdag1), (EditText) findViewById(R.id.dinsdag2), (EditText) findViewById(R.id.dinsdag3), (EditText) findViewById(R.id.dinsdag4), (EditText) findViewById(R.id.dinsdag5), (EditText) findViewById(R.id.dinsdag6), (EditText) findViewById(R.id.dinsdag7), (EditText) findViewById(R.id.dinsdag8), (EditText) findViewById(R.id.dinsdag9), (EditText) findViewById(R.id.dinsdag10)};
        EditText[] woensdag = {(EditText) findViewById(R.id.woensdag1), (EditText) findViewById(R.id.woensdag2), (EditText) findViewById(R.id.woensdag3), (EditText) findViewById(R.id.woensdag4), (EditText) findViewById(R.id.woensdag5), (EditText) findViewById(R.id.woensdag6), (EditText) findViewById(R.id.woensdag7), (EditText) findViewById(R.id.woensdag8), (EditText) findViewById(R.id.woensdag9), (EditText) findViewById(R.id.woensdag10)};
        EditText[] donderdag = {(EditText) findViewById(R.id.donderdag1), (EditText) findViewById(R.id.donderdag2), (EditText) findViewById(R.id.donderdag3), (EditText) findViewById(R.id.donderdag4), (EditText) findViewById(R.id.donderdag5), (EditText) findViewById(R.id.donderdag6), (EditText) findViewById(R.id.donderdag7), (EditText) findViewById(R.id.donderdag8), (EditText) findViewById(R.id.donderdag9), (EditText) findViewById(R.id.donderdag10)};
        EditText[] vrijdag = {(EditText) findViewById(R.id.vrijdag1), (EditText) findViewById(R.id.vrijdag2), (EditText) findViewById(R.id.vrijdag3), (EditText) findViewById(R.id.vrijdag4), (EditText) findViewById(R.id.vrijdag5), (EditText) findViewById(R.id.vrijdag6), (EditText) findViewById(R.id.vrijdag7), (EditText) findViewById(R.id.vrijdag8), (EditText) findViewById(R.id.vrijdag9), (EditText) findViewById(R.id.vrijdag10)};

        TextView[] busEvening = {(TextView) findViewById(R.id.maandagBusHome), (TextView) findViewById(R.id.dinsdagBusHome), (TextView) findViewById(R.id.woensdagBusHome), (TextView) findViewById(R.id.donderdagBusHome), (TextView) findViewById(R.id.vrijdagBusHome)};

        //TODO: load data into roster...
    }

}
