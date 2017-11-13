package be.pxl.webandmobile.webandmobile.lessenrooster;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.bus.ApiRoutesAsync;
import be.pxl.webandmobile.webandmobile.beans.bus.BusRoute;


/**
 * Created by René on 07/11/2017.
 */

public class ApiGetClassData extends ApiBaseClassAsync {
    private EditText[][] courses;
    private TextView[][] busRegeling;
    private boolean isLastClass;

    public ApiGetClassData(Context context, ProgressBar progressBar, EditText[][] courses, TextView[][] busRegeling, boolean isLastClass) {
        super(context, progressBar);

        this.courses = courses;
        this.busRegeling = busRegeling;
        this.isLastClass = isLastClass;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String passedString) {
        //super.onPostExecute(passedString);
        List<Course> courseList;
        LocalDate currentDate = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int thisWeek = currentDate.get(woy);

        try {
            JSONArray jsonArray = new JSONArray(passedString);
            courseList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject classInfo = jsonArray.getJSONObject(i);
                String dateString = classInfo.getString("datum").replace("\\", "");

                LocalDate date = LocalDate.parse(dateString, formatter);
                int weekOfDate = date.get(woy);

                //add boolean to see if course is in the current week
                if (thisWeek == weekOfDate) {
                    Course course = new Course(date, classInfo.getInt("van_uur"), classInfo.getInt("tot_uur"),
                            classInfo.getString("lokaal"), classInfo.getString("olod"), classInfo.getString("code_docent"));

                    courseList.add(course);
                }
            }

            int dayOfWeek;
            int startHour;
            int endHour;
            int hourValue;
            int amount;

            for (Course c : courseList) {
                dayOfWeek = c.getDate().getDayOfWeek().getValue() - 1;
                startHour = c.getStartHour();
                endHour = c.getEndHour();

                //-8 To get the correct index for courses
                hourValue = Integer.parseInt(Integer.toString(startHour).substring(0, 2)) - 8;

                //amount is the amount of hours the course takes
                amount = Integer.parseInt(Integer.toString(endHour).substring(0, 2)) - Integer.parseInt(Integer.toString(startHour).substring(0, 2));


                for (int i = 0; i < amount; i++) {
                    courses[dayOfWeek][hourValue + i].setText(c.toString());
                    courses[dayOfWeek][hourValue + i].setEnabled(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //load busdata:
        if(isLastClass) {
            try {
                setupBusses(busRegeling, courses);
            } catch (Exception e) {
                e.printStackTrace();
                //no internet (probably).
            }
        }
        //3.3 save editable fields on change
        changeableEdit(courses);
    }

    public static boolean isCourseDataAvailable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);

        return preferences.contains("courseApi");
    }

    //Fills the editTexts with saved courseData
    public static void getCourseData(Context context, EditText[][] courses) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        String courseString = preferences.getString("courses", null);

        String[] temp = courseString.split(";");
        String[][] courseMatrix = new String[5][10];

        for (int i = 0; i < temp.length; i++) {
            courseMatrix[i] = courseString.split(",");
        }

        for (int i = 0; i < courseMatrix.length; i++) {
            for (int j = 0; j < courseMatrix[i].length; j++) {
                courses[i][j].setText(courseMatrix[i][j]);
                courses[i][j].setEnabled(false);
            }
        }
    }

    //Saves courseData in SharedPreferences
    //A comma seperates columns
    //Semicolon seperates rows
    public static void saveCourseData(EditText[][] courses, Context context) {
        String stringToSave = "";
        StringBuilder builder = new StringBuilder(stringToSave);

        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //clear out old data:
        preferencesEditor.clear();//clear out previous data!

        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[0].length; j++) {
                builder.append(courses[i][j].getText());
                builder.append(',');
            }
            builder.append(';');
        }

        stringToSave = builder.toString();

        //add class to keyset
        preferencesEditor.putString("courses", stringToSave);
        preferencesEditor.apply();
    }

    public static void deleteCoursedata(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //clear out old data:
        preferencesEditor.clear();
    }

    //busses:
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
            Context context = super.getContext();
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

                    ApiBaseClassAsync api = new ApiRoutesAsync(context, null, busRegeling[0][dayofweek]);//ignore the progressbar by setting it to null.
                    api.execute(myBusRoute.generateUrl());

                    //return trip:
                    cl.set(Calendar.HOUR_OF_DAY, endHour);
                    cl.set(Calendar.MINUTE, 40);

                    myBusRoute.setDateAndTime(cl.getTime());
                    myBusRoute.setToSchool(false);
                    myBusRoute.isArrivaltime(false);

                    ApiBaseClassAsync apiReturn = new ApiRoutesAsync(context, null, busRegeling[1][dayofweek]);
                    apiReturn.execute(myBusRoute.generateUrl());
                }
            }//busdata not set, so just quit
        }//else weekend, not at current roster so skip it.
    }

    private void changeableEdit(EditText[][] editableFields) {
        final Context context = super.getContext();
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
        EditText[] tmp = classes;
        for (int i = 0; i < classes.length; i++) {
            if (!classes[i].getText().toString().trim().equals("")) {
                return i + 8;
            }
        }

        return -1;
    }
}
