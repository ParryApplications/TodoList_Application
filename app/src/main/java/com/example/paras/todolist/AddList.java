package com.example.paras.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AddList extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogbuilder;
    private EditText title,status,des;
    private Button save_id;
    private TextView heading;
    private DataBase db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list2);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_id);
//        db = new DataBase(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AddList.this, "Clicked", Toast.LENGTH_SHORT).show();


                dialogbuilder = new AlertDialog.Builder(AddList.this);
                View view = getLayoutInflater().inflate(R.layout.popup, null);

                heading = (TextView)view.findViewById(R.id.heading);
                heading.setText(" Add Your TODO List here! ");

                title = (EditText)view.findViewById(R.id.title_popup_id);
                status = (EditText)view.findViewById(R.id.status_popup_id);
                des = (EditText)view.findViewById(R.id.des_popup_id);


                dialogbuilder.setView(view);
                alertDialog = dialogbuilder.create();
                alertDialog.show();

                save_id = (Button) view.findViewById(R.id.save_id);

                save_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!title.getText().toString().isEmpty() && !status.getText().toString().isEmpty()
                        && status.getText().toString().trim().equals("complete") || status.getText().toString().trim().equals("pending"))
                        {

                                String title_text = title.getText().toString().trim();
                                String status_text = status.getText().toString().trim();
                                String description = des.getText().toString().trim();

                                TODO todo = new TODO();
                                todo.setTodo_title(title_text);
                                todo.setTodo_status(status_text);
                                todo.setDescription(description);
                                db = new DataBase(AddList.this);
                                db.addtitle(todo);

                                Snackbar.make(v, "Successfully Added to the List", BaseTransientBottomBar.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.dismiss();
                                        startActivity(new Intent(AddList.this, MainActivity.class));
                                        finish();
                                    }
                                }, 1000);    // 1 sec

                        }
                        else

                            Snackbar.make(v,"Invalid! Fill Blocks Properly!", BaseTransientBottomBar.LENGTH_LONG).show();
//                        title.setText("");
//                        status.setText("");
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        db = new DataBase(this);
        if(db.countlist() >0 )
        {
            startActivity(new Intent(AddList.this,MainActivity.class));
            finish();
        }
        else
            Toast.makeText(this, "Click Button and Add Your List!", Toast.LENGTH_LONG).show();
    }
}
