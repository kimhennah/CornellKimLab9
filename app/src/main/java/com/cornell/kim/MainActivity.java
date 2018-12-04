package com.cornell.kim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText fname;
    EditText age;
    EditText gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname = findViewById(R.id.etFname);
        age = findViewById(R.id.etAge);
        gender = findViewById(R.id.etGender);

    }
}
