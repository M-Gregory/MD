package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by René on 07/11/2017.
 */

public class ApiGetClassData extends ApiBaseClassAsync {
    private EditText[][] courses;

    public ApiGetClassData(Context context, ProgressBar progressBar, EditText[][] courses) {
        super(context, progressBar);

        this.courses = courses;
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

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject classInfo = jsonArray.getJSONObject(i);
                String dateString = classInfo.getString("datum").replace("\\", "");

                LocalDate date = LocalDate.parse(dateString, formatter);
                int weekOfDate = date.get(woy);

                //add boolean to see if course is in the current week
                if(thisWeek == weekOfDate) {
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

            for(Course c: courseList) {
                dayOfWeek = c.getDate().getDayOfWeek().getValue() - 1;
                startHour = c.getStartHour();
                endHour = c.getEndHour();

                //-8 To get the correct index for courses
                hourValue = Integer.parseInt(Integer.toString(startHour).substring(0,2)) - 8;

                //amount is the amount of hours the course takes
                amount = Integer.parseInt(Integer.toString(endHour).substring(0,2)) - Integer.parseInt(Integer.toString(startHour).substring(0,2));


                for(int i = hourValue; i < hourValue + amount; i++) {
                    courses[dayOfWeek][hourValue].setText(c.toString());
                }
            }
        } catch (JSONException e) {
            //TODO: add alert
        }
    }
}
