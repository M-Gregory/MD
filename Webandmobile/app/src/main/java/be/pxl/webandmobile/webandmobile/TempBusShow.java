package be.pxl.webandmobile.webandmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class TempBusShow extends AppCompatActivity {

    private TextView busApiContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_bus_show);


        busApiContent = (TextView) findViewById(R.id.showContentApi);

        //temporary pushing of data:

        String resultString = "";
        List<String> dataList = getIntent().getStringArrayListExtra("passedStringArray");//get data.

        if (dataList != null)
            for (String item : dataList) {
                resultString += item + System.getProperty("line.separator");
            }

        busApiContent.setText(resultString);
    }

}
