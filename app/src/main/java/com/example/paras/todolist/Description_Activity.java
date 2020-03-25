package com.example.paras.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Description_Activity extends AppCompatActivity {

    private TextView des_id,title_id;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_);

        dataBase = new DataBase(this);

        title_id = (TextView)findViewById(R.id.title_des);
        des_id = (TextView)findViewById(R.id.des_id);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            title_id.setText(bundle.getString("title"));
            des_id.setText("Description: "+bundle.getString("Des"));
        }
        else
            {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
    }
}
