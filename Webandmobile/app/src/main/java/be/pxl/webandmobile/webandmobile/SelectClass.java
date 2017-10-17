package be.pxl.webandmobile.webandmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectClass extends AppCompatActivity {

    //public static String klasNaam = "";
    public static PXLClass aClass = new PXLClass();
    //private static Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1. default:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        //2. fill spinner (thread it!)
        Spinner s = (Spinner) findViewById(R.id.dropdown);
        String[] contents = {"klas 1", "klas 2", "klas 3"};//via api of webrip
        s.setAdapter(new ArrayAdapter<String>(SelectClass.this, R.layout.support_simple_spinner_dropdown_item, contents));

        //3. click event:
        Button classSelectButton = (Button) findViewById(R.id.classSelectButton);
        classSelectButton.setOnClickListener(click -> {
            //System.out.println("test");
            aClass.setClassName(s.getSelectedItem().toString());
            alert("you clicked " + aClass.getClassName(), this);
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> startActivity(new Intent(SelectClass.this, MainActivity.class)));
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
