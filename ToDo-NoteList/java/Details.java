package com.example.user.todo_notelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {
    TextView mDetails;
    SimpleDatabase db;
    Note note;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDetails = findViewById(R.id.noteDetails);

        Intent i = getIntent();
        id = i.getLongExtra("ID", 0);

        db = new SimpleDatabase(this);
        note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        mDetails = findViewById(R.id.noteDesc);
        mDetails.setText(note.getContent());
        mDetails.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user click the delete button, it will delete the note in the database
                SimpleDatabase db = new SimpleDatabase(getApplicationContext());
                db.deleteNote(id);
                //Toast the "Note Deleted" message to user
                Toast.makeText(getApplicationContext(),"Note Deleted", Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit){
            //Send user to edit activity
            //And toast the "Edit Note" message to user
            Toast.makeText(this, "Edit Note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Edit.class);
            i.putExtra("ID", note.getId());
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
