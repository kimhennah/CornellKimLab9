package com.cornell.kim;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText eFname, eAge, eGender;
    Button saveButton;
    TextView resName,resAge,resGender;

    String name, gender;
    int age;
    Users user;

    DatabaseReference dbUsers;
    ArrayList<Users> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eFname = findViewById(R.id.etFname);
        eAge = findViewById(R.id.etAge);
        eGender = findViewById(R.id.etGender);
        saveButton = findViewById(R.id.btnSave);

        resName = findViewById(R.id.tvFname);
        resAge = findViewById(R.id.tvAge);
        resGender = findViewById(R.id.tvGender);

        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        userList = new ArrayList<>();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUsers();
            }
        });

        Clear();

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    Users user = userSnapshot.getValue(Users.class);
                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addUsers(){
        name = eFname.getText().toString().trim();
        age = Integer.parseInt(eAge.getText().toString());
        gender = eGender.getText().toString().trim();

        if (name.equals("") || gender.equals("")) {
            Toast.makeText(this, "Please input values on all fields.", Toast.LENGTH_LONG).show();
        } else {
            if (userList.isEmpty()) {
                user = new Users(name, age, gender);
                String id = dbUsers.push().getKey();
                dbUsers.child(id).setValue(user);
                Toast.makeText(this, "Record Stored.", Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < userList.size(); i++) {
                    String storedFname = userList.get(i).getFname();
                    if (storedFname.equals(name)) {
                        Toast.makeText(this, "Record Exists.", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        user = new Users(name, age, gender);
                        String id = dbUsers.push().getKey();
                        dbUsers.child(id).setValue(user);
                        Toast.makeText(this, "Record Stored.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

    }

    public void searchRecord(View v) {
        name = eFname.getText().toString().trim();

        for(int i = 0; i < userList.size(); i++) {
            String storedFname = userList.get(i).getFname();
            if(storedFname.equalsIgnoreCase(name)) {
                resName.setText(storedFname);
                String storedAge = userList.get(i).getAge()+"";
                resAge.setText(storedAge);
                resGender.setText(userList.get(i).getGender());
                Toast.makeText(this, "Record Retrieved.", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        eFname.onEditorAction(EditorInfo.IME_ACTION_NEXT);
    }

    void Clear(){
        eFname.setText(null);
        eAge.setText(null);
        eGender.setText(null);
    }
}
