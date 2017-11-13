package be.pxl.webandmobile.webandmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.bus.ApiRoutesAsync;
import be.pxl.webandmobile.webandmobile.lessenrooster.ApiScheduleAsync;
import be.pxl.webandmobile.webandmobile.beans.bus.BusRoute;

public class Schedule extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        loadDataIntoRoster();
    }

    private void loadDataIntoRoster() {
        //all fields into multidimensional array
        //1.1 busfields:
        TextView[][] busRegeling = {
                {(TextView) findViewById(R.id.maandagBusToSchool), (TextView) findViewById(R.id.dinsdagBusToSchool), (TextView) findViewById(R.id.woensdagBusToSchool), (TextView) findViewById(R.id.donderdagBusToSchool), (TextView) findViewById(R.id.vrijdagBusToSchool)},
                {(TextView) findViewById(R.id.maandagBusHome), (TextView) findViewById(R.id.dinsdagBusHome), (TextView) findViewById(R.id.woensdagBusHome), (TextView) findViewById(R.id.donderdagBusHome), (TextView) findViewById(R.id.vrijdagBusHome)}
        };

        //1.2 class/editable fields...
        EditText[][] editableFields = {
                {(EditText) findViewById(R.id.maandag1), (EditText) findViewById(R.id.maandag2), (EditText) findViewById(R.id.maandag3), (EditText) findViewById(R.id.maandag4), (EditText) findViewById(R.id.maandag5), (EditText) findViewById(R.id.maandag6), (EditText) findViewById(R.id.maandag7), (EditText) findViewById(R.id.maandag8), (EditText) findViewById(R.id.maandag9), (EditText) findViewById(R.id.maandag10)},
                {(EditText) findViewById(R.id.dinsdag1), (EditText) findViewById(R.id.dinsdag2), (EditText) findViewById(R.id.dinsdag3), (EditText) findViewById(R.id.dinsdag4), (EditText) findViewById(R.id.dinsdag5), (EditText) findViewById(R.id.dinsdag6), (EditText) findViewById(R.id.dinsdag7), (EditText) findViewById(R.id.dinsdag8), (EditText) findViewById(R.id.dinsdag9), (EditText) findViewById(R.id.dinsdag10)},
                {(EditText) findViewById(R.id.woensdag1), (EditText) findViewById(R.id.woensdag2), (EditText) findViewById(R.id.woensdag3), (EditText) findViewById(R.id.woensdag4), (EditText) findViewById(R.id.woensdag5), (EditText) findViewById(R.id.woensdag6), (EditText) findViewById(R.id.woensdag7), (EditText) findViewById(R.id.woensdag8), (EditText) findViewById(R.id.woensdag9), (EditText) findViewById(R.id.woensdag10)},
                {(EditText) findViewById(R.id.donderdag1), (EditText) findViewById(R.id.donderdag2), (EditText) findViewById(R.id.donderdag3), (EditText) findViewById(R.id.donderdag4), (EditText) findViewById(R.id.donderdag5), (EditText) findViewById(R.id.donderdag6), (EditText) findViewById(R.id.donderdag7), (EditText) findViewById(R.id.donderdag8), (EditText) findViewById(R.id.donderdag9), (EditText) findViewById(R.id.donderdag10)},
                {(EditText) findViewById(R.id.vrijdag1), (EditText) findViewById(R.id.vrijdag2), (EditText) findViewById(R.id.vrijdag3), (EditText) findViewById(R.id.vrijdag4), (EditText) findViewById(R.id.vrijdag5), (EditText) findViewById(R.id.vrijdag6), (EditText) findViewById(R.id.vrijdag7), (EditText) findViewById(R.id.vrijdag8), (EditText) findViewById(R.id.vrijdag9), (EditText) findViewById(R.id.vrijdag10)}
        };

        //2.1 setup courses:
        setupCourses(editableFields, busRegeling);
/*
        //2.2 setup busses (note, this HAS to come after setupCourses!!!) -> (eh no, it now happens in the setupcourses asynctask!
        try {
            setupBusses(busRegeling, editableFields);
        } catch (Exception e) {
            e.printStackTrace();
            //no internet (probably).
        }

        //3.3 save editable fields on change
        changeableEdit(editableFields);
        */
    }

    private void setupCourses(EditText[][] courses, TextView[][] busRegeling) {
        // TODO: 31/10/2017: note empty courses should be a "String.empty" for now (you can edit the checks if you want something else!
        //String dummy = "m1;m2;m3;m4;m5;m6;m7;m8;m9;m10; ;d2;d3;d4;d5;d6;d7;d8; ;d10;w1;w2;w3;w4;w5;w6;w7;w8;w9;w10;d1;d2;d3;d4;d5;d6;d7;d8;d9;d10;v1;v2;v3;v4;v5;v6;v7;v8;v9;v10";
        //String[] dummy2 = dummy.split(";");

        //courses:
        /* for (int i = 0, j = 0; i < dummy2.length; i++, j = i / 10) {
            if (!dummy2[i].equals(" ")) {//space means no data...
                courses[j][i % 10].setText(dummy2[i]);
                courses[j][i % 10].setEnabled(false);
            }
        } */

        Context context = getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("classApi", context.MODE_PRIVATE);
        String className = preferences.getString("class", null);

        try {
            if (ApiScheduleAsync.isCourseDataAvailable(context)) {
                ApiScheduleAsync.getCourseData(context, courses);
            } else {
                ApiBaseClassAsync api = new ApiScheduleAsync(Schedule.this, null, courses, className, busRegeling);
                api.execute("http://data.pxl.be/roosters/v1/klassen/" + className + "/vakken");
            }
        } catch (Exception ex) {
            //TODO: add alert saying something is wrong with the selected class
        }
    }
    /*
    private void setupBusses(TextView[][] busRegeling, EditText[][] courses) {
        int dayofweek;
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        dayofweek = cl.get(Calendar.DAY_OF_WEEK) - 2;//2== monday, set it to 0==monday

        if (dayofweek >= 0 && dayofweek < 5) {//mon-fri
            //set calendar's time to time to start the trip.
            //test:
            busRegeling[0][dayofweek].setText("laden...");
            busRegeling[1][dayofweek].setText("laden...");

            //Prepare stored variables:
            Context context = getApplicationContext();
            SharedPreferences preferences = context.getSharedPreferences("busApi", context.MODE_PRIVATE);

            String stopName = preferences.getString("busName", "startPoint");
            int xCoordinate = preferences.getInt("busXCoord", 0);
            int yCoordinate = preferences.getInt("busYCoord", 0);

            //test stored variables:
            if (xCoordinate != 0 && yCoordinate != 0) {
                int startHour = getCoursesStartHour(courses[dayofweek]);
                int endHour = getCoursesEndHour(courses[dayofweek]);

                if (startHour >= 0 && endHour >= 0) {//test if you have courses today
                    //set calendar to leave date.
                    cl.set(Calendar.HOUR_OF_DAY, startHour);// 6h
                    cl.set(Calendar.MINUTE, 20);// 20 m
                    cl.set(Calendar.SECOND, 0);// 0s

                    BusRoute myBusRoute = new BusRoute();

                    myBusRoute.setBusStop(stopName);
                    myBusRoute.setDateAndTime(cl.getTime());
                    myBusRoute.setDestX(xCoordinate);
                    myBusRoute.setDestY(yCoordinate);

                    myBusRoute.setToSchool(true);//to school, false when going home!!!
                    myBusRoute.isArrivaltime(true);//you wish to be at school at: 20-11-2017 12:45u -> false would be you start you're trip at 20-11-2017 12:45u

                    ApiBaseClassAsync api = new ApiRoutesAsync(Schedule.this, null, busRegeling[0][dayofweek]);//ignore the progressbar by setting it to null.
                    api.execute(myBusRoute.generateUrl());

                    //return trip:
                    cl.set(Calendar.HOUR_OF_DAY, endHour);
                    cl.set(Calendar.MINUTE, 40);

                    myBusRoute.setDateAndTime(cl.getTime());
                    myBusRoute.setToSchool(false);
                    myBusRoute.isArrivaltime(false);

                    ApiBaseClassAsync apiReturn = new ApiRoutesAsync(Schedule.this, null, busRegeling[1][dayofweek]);
                    apiReturn.execute(myBusRoute.generateUrl());
                }
            }//busdata not set, so just quit
        }//else weekend, not at current roster so skip it.
    }

    private void changeableEdit(EditText[][] editableFields) {
        final Context context = getApplicationContext();
        final SharedPreferences preferences = context.getSharedPreferences("editedFields", context.MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = preferences.edit();

        //preferencesEditor.clear();preferencesEditor.commit();//clear, usefull for resetting.

        if (preferences.getString("ma1", null) == null) {//i'll set them to strings
            //generate default data:
            for (int i = 1; i < editableFields.length; i++) {
                String s = determineDay(i);

                //generate key-value (value empty):
                for (int j = 1; j <= editableFields[i].length; j++) {
                    preferencesEditor.putString(s + j, "");
                }
            }
            preferencesEditor.commit();//save
        } else {
            //load data
            for (int i = 0; i < editableFields.length; i++) {
                String s = determineDay(i + 1);

                //generate key-value (value empty):
                for (int j = 0; j < editableFields[i].length; j++) {
                    if (editableFields[i][j].isEnabled())
                        editableFields[i][j].setText(preferences.getString(s + (j + 1), ""));
                }
            }
        }

        //set listeners to all editTexts:
        for (int i = 0; i < editableFields.length; i++) {
            for (int j = 0; j < editableFields[i].length; j++) {
                if (editableFields[i][j].isEnabled()) {//meaning no class or bus
                    //finals because AS nagged me.
                    final EditText field = editableFields[i][j];
                    final int day = j;

                    editableFields[i][j].addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //don't worry about me.
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //don't worry abuot me.
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            //i'm interesting xD
                            for (int k = 0; k < editableFields.length; k++) {
                                for (int l = 0; l < editableFields[k].length; l++) {
                                    if (editableFields[k][l].getText().hashCode() == editable.hashCode()) {
                                        preferencesEditor.putString(determineDay(k + 1) + (l + 1), editable.toString());
                                        preferencesEditor.commit();

                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private String determineDay(int i) {
        String s;

        switch (i) {
            //define key:
            case (1):
                s = "ma";
                break;
            case (2):
                s = "di";
                break;
            case (3):
                s = "wo";
                break;
            case (4):
                s = "do";
                break;
            case (5):
                s = "vr";
                break;
            default:
                s = null;
        }

        return s;
    }

    private int getCoursesEndHour(EditText[] classes) {
        for (int i = classes.length - 1; i >= 0; i--) {
            if (!classes[i].getText().toString().trim().equals("")) {
                return i + 9;//+7 to get the hour, array[0] -> 0 + 8 == 8:30
            }
        }

        return -1;
    }

    private int getCoursesStartHour(EditText[] classes) {
        for (int i = 0; i < classes.length; i++) {
            if (!classes[i].getText().toString().trim().equals("")) {
                return i + 8;
            }
        }

        return -1;
    }
    */
}
