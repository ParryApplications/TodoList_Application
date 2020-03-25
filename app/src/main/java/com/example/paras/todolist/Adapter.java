package com.example.paras.todolist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    private Context context;
    private List<TODO> todoList;
    private List<TODO> listAll;
    public AlertDialog alertDialog;
    public AlertDialog.Builder dialogbuilder;
    private TextView heading;
    private Button save_id;
    private EditText title,status,description;


    public Adapter(Context context) {
        this.context = context;
    }

    public Adapter(Context context, List<TODO> todoList) {
        this.context = context;
        this.todoList = todoList;
        listAll = new ArrayList<>(todoList);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position)
    {

        TODO todo = todoList.get(position);


        if(todo.getTodo_status().trim().equals("complete"))
        {

            holder.title_id.setText(todo.getTodo_title());
            holder.status_id.setText("Status: "+todo.getTodo_status());
            holder.date_id.setText("Added on: "+todo.getDate());

            holder.title_id.setPaintFlags(holder.title_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.status_id.setPaintFlags(holder.status_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.date_id.setPaintFlags(holder.date_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(!holder.title_id.getPaint().isStrikeThruText())
            {

                holder.title_id.setPaintFlags(holder.title_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.status_id.setPaintFlags(holder.status_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.date_id.setPaintFlags(holder.date_id.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }
            else
                {

            }
        }
        else if(todo.getTodo_status().trim().equals("pending"))
        {

            holder.title_id.setText(todo.getTodo_title());
            holder.status_id.setText("Status: "+todo.getTodo_status());
            holder.date_id.setText("Added on: "+todo.getDate());

            holder.title_id.setPaintFlags(holder.title_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.status_id.setPaintFlags(holder.status_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.date_id.setPaintFlags(holder.date_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));


            if(holder.title_id.getPaint().isStrikeThruText())
            {

                        holder.title_id.setPaintFlags(holder.title_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        holder.status_id.setPaintFlags(holder.status_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        holder.date_id.setPaintFlags(holder.date_id.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            }
            else
                {
                }

        }
            else
            {
                Toast.makeText(context, "ERROR IN YOUR STATUS!", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title_id,status_id,date_id;
        public Button edit_id,delete_id;

        public ViewHolder(@NonNull View itemView , Context ctx)
        {
            super(itemView);
            context = ctx;

            title_id = (TextView)itemView.findViewById(R.id.title_id);
            status_id = (TextView)itemView.findViewById(R.id.status_id);
            date_id = (TextView)itemView.findViewById(R.id.date_id);

            edit_id = (Button)itemView.findViewById(R.id.update_id);
            delete_id = (Button)itemView.findViewById(R.id.delete_id);

            edit_id.setOnClickListener(this);
            delete_id.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemviewcall();
                }
            });

        }

        @Override
        public void onClick(View v)
        {
            int pos = getAdapterPosition();
            final TODO todo = todoList.get(pos);

            switch(v.getId())
            {

// TODO : UPDATE
                case R.id.update_id:
//                    Toast.makeText(context, "Clicked update", Toast.LENGTH_SHORT).show();


                    dialogbuilder = new AlertDialog.Builder(context);
                    final View view = LayoutInflater.from(context).inflate(R.layout.popup,null);

                    title=(EditText) view.findViewById(R.id.title_popup_id);
                    status=(EditText) view.findViewById(R.id.status_popup_id);
                    save_id=(Button) view.findViewById(R.id.save_id);

                    description =(EditText) view.findViewById(R.id.des_popup_id);

                    heading=(TextView)view.findViewById(R.id.heading);
                    heading.setText("Edit Your TODO List");
                    dialogbuilder.setMessage("U Can Update Your Status Also");
                    dialogbuilder.setView(view);
                    alertDialog=dialogbuilder.create();
                    alertDialog.show();

                    title.setText(todo.getTodo_title());
                    status.setText(todo.getTodo_status());
                    description.setText(todo.getDescription());

                    save_id.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBase db = new DataBase(context);

                            todo.setTodo_title(title.getText().toString());
                            todo.setTodo_status(status.getText().toString());
                            todo.setDescription(description.getText().toString());

                            if(!title.getText().toString().isEmpty() && !status.getText().toString().isEmpty()
                                    && status.getText().toString().trim().equals("complete") || status.getText().toString().trim().equals("pending"))
                            {
                                db.updatelist(todo);
                                notifyItemChanged(getAdapterPosition(),todo);
                                Toast.makeText(context," Changed Successfully ",Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                            else
                            {
                                Snackbar.make(view,"Invalid!", Snackbar.LENGTH_SHORT).show();
                                status.setText("");
//                                title.setText("");
                            }
                        }
                    });
                    break;
// TODO : DELETE
                case R.id.delete_id:
//                    Toast.makeText(context, "Clicked delete", Toast.LENGTH_SHORT).show();

//                    int pos1 = getAdapterPosition();
//                    TODO todo1 = todoList.get(pos1);


                    dialogbuilder=new AlertDialog.Builder(context);
                    v = LayoutInflater.from(context).inflate(R.layout.confirmation,null);
                    Button no = (Button)v.findViewById(R.id.no);
                    Button yes = (Button)v.findViewById(R.id.yes);

//                    yes.setBackgroundResource(R.drawable.delete);

                    dialogbuilder.setView(v);
                    alertDialog=dialogbuilder.create();
                    alertDialog.show();



                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            TODO todo2 = new TODO();
                            DataBase dataBase= new DataBase(context);
                            dataBase.deletelist(todo.getId());

                            todoList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            alertDialog.dismiss();
                        }
                    });


                    break;

            }

        }

        public void itemviewcall()
        {

            int pos = getAdapterPosition();
            TODO todo = todoList.get(pos);

            Intent intent = new Intent(context,Description_Activity.class);
            intent.putExtra("Des",todo.getDescription());
            intent.putExtra("title",todo.getTodo_title());
            context.startActivity(intent);
        }
    }
}
