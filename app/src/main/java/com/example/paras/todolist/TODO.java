package com.example.paras.todolist;

public class TODO
{
    public int id;
    public String Todo_title;
    public String Todo_status;
    public String date;
    public String description;


    public TODO(int id, String todo_title, String todo_status, String date, String description) {
        this.id = id;
        Todo_title = todo_title;
        Todo_status = todo_status;
        this.date = date;
        this.description = description;
    }

    public TODO(int id, String todo_title, String todo_status, String date) {
        this.id = id;
        Todo_title = todo_title;
        Todo_status = todo_status;
        this.date = date;
    }

    public TODO(){}

    public TODO(String todo_title, String todo_status, String date) {
        Todo_title = todo_title;
        Todo_status = todo_status;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo_title() {
        return Todo_title;
    }

    public void setTodo_title(String todo_title) {
        Todo_title = todo_title;
    }

    public String getTodo_status() {
        return Todo_status;
    }

    public void setTodo_status(String todo_status) {
        Todo_status = todo_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
