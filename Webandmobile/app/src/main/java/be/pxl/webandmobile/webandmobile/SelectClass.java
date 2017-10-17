package be.pxl.webandmobile.webandmobile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class SelectClass extends AppCompatActivity {
    private boolean started;
    private boolean classSelected;
    private boolean specSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        //2. fill spinner (thread it!)
        Spinner yearSpinner = (Spinner) findViewById(R.id.dropdownYear);
        String[] contents = {"1 TIN", "2 TIN", "3 TIN"};//via api of webrip
        yearSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, contents));

        Spinner specSpinner = (Spinner) findViewById(R.id.dropdownSpecialization);
        String[] specContents = {"AON", "SNB", "SWM"};//via api of webrip
        specSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, specContents));

        Spinner classSpinner = (Spinner) findViewById(R.id.dropdownClass);
        String[] classContents = {"3AONA", "3AOND", "3SNBA", "3SWMA"};//via api of webrip
        classSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, classContents));

        //3. click event:
        Button b = (Button) findViewById(R.id.btn);
        b.setOnClickListener(click -> {
            System.out.println("test");
            alert("you clicked me", this);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Spinner yearSpinner = (Spinner) findViewById(R.id.dropdownYear);
        Spinner specSpinner = (Spinner) findViewById(R.id.dropdownSpecialization);
        Spinner classSpinner = (Spinner) findViewById(R.id.dropdownClass);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(started) {
                    String selected = yearSpinner.getSelectedItem().toString();
                    View specializationLayout = findViewById(R.id.relativeSpecialization);
                    View classLayout = findViewById(R.id.relativeClass);

                    if(selected.startsWith("3")) {
                        specializationLayout.setVisibility(View.VISIBLE);
                        classLayout.setVisibility(View.GONE);
                    } else {
                        classLayout.setVisibility(View.VISIBLE);
                        specializationLayout.setVisibility(View.GONE);
                    }
                } else {
                    started = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        specSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(specSelected) {
                    String selected = specSpinner.getSelectedItem().toString();
                    View classLayout = findViewById(R.id.relativeClass);
                    classLayout.setVisibility(View.VISIBLE);
                    alert(selected, SelectClass.this);
                } else {
                    specSelected = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(classSelected) {
                    String selected = classSpinner.getSelectedItem().toString();

                    alert(selected, SelectClass.this);
                } else {
                    classSelected = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static void alert(String message, SelectClass context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
