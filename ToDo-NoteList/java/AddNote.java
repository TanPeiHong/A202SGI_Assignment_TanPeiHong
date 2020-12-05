package com.example.user.todo_notelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String todayDate, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);
        //Change the text color to white
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        //Change and set the toolbar text to "Add New Note"
        getSupportActionBar().setTitle("Add New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Change the toolbar text, when user is typing anything
                if (s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //*Get the current date and time
        c = Calendar.getInstance();
        todayDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));

        Log.d("Calender", "Date and Time: " + todayDate + " and " + currentTime);
    }

    //Change the format of the time
    /***
     *
     * Example: If the time is 8:30, it will change form 8:3 to 08:30
     *
     ***/
    private String pad(int i) {
        if (i < 10){
            return "0" + i;
        }
        else {
            return String.valueOf(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //If user click save button, it will save the note into the database
        if (item.getItemId() == R.id.save){
            Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), todayDate, currentTime);
            SimpleDatabase db = new SimpleDatabase(this);
            db.addNote(note);
            //Toast "Note have Saved!" message for user click save button
            Toast.makeText(this, "Note have Saved!", Toast.LENGTH_SHORT).show();
            goToMain();
        }
        //If user click delete button, it will not save into the database
        if (item.getItemId() == R.id.delete){
            //Toast "Note Not Saved" message for user click delete button
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    //If user click save button, it will redirect user to main menu
    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    //If user click delete button, it will redirect user back to main menu
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
