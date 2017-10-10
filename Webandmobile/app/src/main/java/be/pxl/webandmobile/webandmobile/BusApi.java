package be.pxl.webandmobile.webandmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BusApi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api);

        TextView tv = (TextView) findViewById(R.id.busTextView);
        //tv.setText("edited text");
    }
}
