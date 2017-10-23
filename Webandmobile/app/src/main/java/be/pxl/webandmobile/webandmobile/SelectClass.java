package be.pxl.webandmobile.webandmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class SelectClass extends AppCompatActivity {
    private boolean started;
    private boolean classSelected;
    private boolean specSelected;
    private Spinner yearSpinner;
    private Spinner specSpinner;
    private Spinner classSpinner;
    private View specializationLayout;
    private View classLayout;
    private String[] classContents;
    private LessenroosterAPI api;
    private Button button;

    //TODO: Progressbar tijdens het inladen van klassen (3e spinner) tonen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        api = new LessenroosterAPI();

        //2. fill spinner (thread it!)
        yearSpinner = (Spinner) findViewById(R.id.dropdownYear);
        String[] contents = {"1 TIN", "2 TIN", "3 TIN"};//via api of webrip
        yearSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, contents));

        specSpinner = (Spinner) findViewById(R.id.dropdownSpecialization);
        String[] specContents = {"AON", "SNB", "SWM"};//via api of webrip
        specSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, specContents));

        classSpinner = (Spinner) findViewById(R.id.dropdownClass);
        classContents = new String[]{"", "", "", ""};//via api of webrip
        classSpinner.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, classContents));

        classLayout = findViewById(R.id.relativeClass);
        specializationLayout = findViewById(R.id.relativeSpecialization);

        //3. click event:
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(click -> {
            alert("Selecteer klas " + classSpinner.getSelectedItem().toString() + "?", this);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(started) {
                    String selected = yearSpinner.getSelectedItem().toString();
                    //Zodat je altijd enkel begint met de eerste dropdown,
                    // ook na het selecteren van een klas en daarna een nieuw jaar te kiezen
                    specializationLayout.setVisibility(View.GONE);
                    classLayout.setVisibility(View.GONE);

                    if(selected.startsWith("3")) {
                        specializationLayout.setVisibility(View.VISIBLE);
                    } else {
                        //call http://data.pxl.be/roosters/v1/klassen/xTIN (1 of 2)
                        //vul classSpinner met de klassen vd json
                        classContents = api.getClasses(yearSpinner.getSelectedItem().toString());
                        ((BaseAdapter) classSpinner.getAdapter()).notifyDataSetChanged();
                        classSpinner.invalidate();
                        classSpinner.setSelection(0);
                        classLayout.setVisibility(View.VISIBLE);
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
                    classContents = api.getClasses(specSpinner.getSelectedItem().toString());
                    ((BaseAdapter) classSpinner.getAdapter()).notifyDataSetChanged();
                    classSpinner.invalidate();
                    classSpinner.setSelection(0);

                    classLayout.setVisibility(View.VISIBLE);
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
                button.setEnabled(true);
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
