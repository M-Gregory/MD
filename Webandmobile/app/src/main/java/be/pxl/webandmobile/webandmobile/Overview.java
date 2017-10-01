package be.pxl.webandmobile.webandmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //2. custom
        //2.1 load listview data (threaded)
        String[] list = {"first item filled in by student", "second item filled in by student", "third item filled in by student", "..."};//from local storage (on phone)...
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

}
