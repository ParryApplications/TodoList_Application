package com.example.paras.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private DataBase db;
    private List<TODO> list;
    private List<TODO> todolist;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogbuilder;
    private EditText title,status,des;
    private Button save_id;
    private TextView heading;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBase(this);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            list = new ArrayList<>();
            todolist = new ArrayList<>();

            db = new DataBase(this);
            list = db.getalllist();

            for(TODO obj : list)
            {
                TODO todo = new TODO();
                todo.setId(obj.getId());
                todo.setTodo_title(obj.getTodo_title());
                todo.setTodo_status(obj.getTodo_status());
                todo.setDescription(obj.getDescription());
                todo.setDate(obj.getDate());

                todolist.add(todo);
            }


            adapter = new Adapter(this,todolist);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem item = menu.findItem(R.id.search_id);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search List By Title's...");

        // TODO : Insert Filter in This search view

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.add_id:
                popup_foradd();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void popup_foradd()
    {

//        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();


        dialogbuilder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        heading = (TextView)view.findViewById(R.id.heading);
        heading.setText(" Add Your TODO List here! ");

        des = (EditText)view.findViewById(R.id.des_popup_id);
        title = (EditText)view.findViewById(R.id.title_popup_id);
        status = (EditText)view.findViewById(R.id.status_popup_id);


        dialogbuilder.setView(view);
        alertDialog = dialogbuilder.create();
        alertDialog.show();

        save_id = (Button) view.findViewById(R.id.save_id);
        save_id.setOnClickListener(this);



    }

    @Override
    public void onClick(View v)
    {
       switch (v.getId())
       {
           case R.id.save_id:

               if(!title.getText().toString().isEmpty() && !status.getText().toString().isEmpty()
                       && status.getText().toString().trim().equals("complete") || status.getText().toString().trim().equals("pending"))
               {
                   String title_text = title.getText().toString().trim();
                   String status_text = status.getText().toString().trim();
                   String Description = des.getText().toString().trim();

                   TODO todo = new TODO();
                   todo.setTodo_title(title_text);
                   todo.setTodo_status(status_text);
                   todo.setDescription(Description);

                   db = new DataBase(MainActivity.this);
                   db.addtitle(todo);

                   Toast.makeText(this, "Successfully Added to the List", Toast.LENGTH_SHORT).show();

                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run()
                       {
                           alertDialog.dismiss();
                           startActivity(new Intent(MainActivity.this,MainActivity.class));
                           finish();

                       }
                   }, 1000);    // 1 sec

               }
               else
                   Toast.makeText(this, "Invalid! Fill Blocks Properly!", Toast.LENGTH_SHORT).show();
//                    title.setText("");
//                    status.setText("");
               break;
       }

    }
}
