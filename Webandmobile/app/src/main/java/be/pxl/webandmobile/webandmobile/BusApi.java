package be.pxl.webandmobile.webandmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class BusApi extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api);

        Button b = (Button)findViewById(R.id.selectBusStop);
        Intent intent = new Intent(BusApi.this, TempBusShow.class);
        intent.putStringArrayListExtra("passedStringArray", list);

        b.setOnClickListener(view->{
            list.clear();
            list.add("1");
            list.add("row 2");
            list.add("row 3");

            list.add( (((EditText)findViewById(R.id.editableText)).getText()).toString() );
                    startActivity(intent);
                });//pass list...
    }
}
