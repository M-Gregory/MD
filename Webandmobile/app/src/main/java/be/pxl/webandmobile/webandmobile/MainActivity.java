package be.pxl.webandmobile.webandmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. custom:
        //2.1 fill in class:
        TextView textView = (TextView) findViewById(R.id.huidigeKlas);
        textView.setText("Huidige klas: '" + "???" + "'");//?? moet klas worden of 'geen' om aan te geven aan de gebruiker of deze zijn klaas wenst te wijzigen.

        //3.2: click event
        //3.2.1: select class:
        Button setClassButton = (Button) findViewById(R.id.setClass);
        setClassButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SelectClass.class)));

        //3.2.2: overview
        Button overviewButton = (Button)findViewById(R.id.overview);
        overviewButton.setOnClickListener(view->startActivity(new Intent(MainActivity.this, Overview.class)));
    }
}
