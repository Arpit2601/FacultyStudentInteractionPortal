package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseAdd extends AppCompatActivity {

    public static final String TAG = "CourseAdd";
    private DatabaseReference databaseReference;
    public String course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // main work starts here
        // first check the courseid enterred y the prof if it exists prompt him to add new one
        // else open a new intent where all the course information is present

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                EditText courseID = findViewById(R.id.CourseID);
                course_id = courseID.getText().toString();
                if (course_id.equals("")){
                    Toast.makeText(CourseAdd.this, "Enter a valid course key", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference.child("Courses").orderByKey().equalTo(course_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Log.d(TAG, course_id);
                            Toast.makeText(CourseAdd.this, "This course ID already exists. Please enter new ID.", Toast.LENGTH_SHORT).show();
                            button.setEnabled(true);

                        }
                        else
                        {

                            Log.d(TAG,course_id);
                            Intent intent = new Intent(CourseAdd.this, AddCourseContent.class);
                            intent.putExtra("CourseID",course_id);
                            finish();
                            startActivity(intent);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }


}
