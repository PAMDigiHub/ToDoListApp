package ca.jhoffman.todolistapp.model;

import java.util.ArrayList;

/**
 * Created by jhoffman on 2016-10-15.
 */

public class TodoListsProvider {
    private static TodoListsProvider instance = new TodoListsProvider();

    private ArrayList<TodoList> todoLists;

    public static TodoListsProvider getInstance() {
        return instance;
    }

    public TodoListsProvider() {
        todoLists = new ArrayList<>();

        TodoList todoListA = new TodoList("Todolist A");
        todoListA.addItem(new TodoItem("item 1a"));
        todoListA.addItem(new TodoItem("item 2a"));
        todoListA.addItem(new TodoItem("item 3a"));

        TodoList todoListB = new TodoList("Todolist B");
        todoListB.addItem(new TodoItem("item 1b"));
        todoListB.addItem(new TodoItem("item 2b"));

        TodoList todoListC = new TodoList("Todolist C");
        TodoItem item1c = new TodoItem("item 1c");
        item1c.setDone(true);
        todoListC.addItem(item1c);
        TodoItem item2c = new TodoItem("item 2c");
        item2c.setDone(true);
        todoListC.addItem(item2c);
        todoListC.addItem(new TodoItem("item 3c"));
        todoListC.addItem(new TodoItem("item 4c"));
        todoListC.addItem(new TodoItem("item 5c"));
        todoListC.addItem(new TodoItem("item 6c"));

        TodoList todoListD = new TodoList("Todolist D");
        TodoItem item1d = new TodoItem("item 1d");
        item1d.setDone(true);
        todoListD.addItem(item1d);

        todoLists.add(todoListA);
        todoLists.add(todoListB);
        todoLists.add(todoListC);
        todoLists.add(todoListD);
    }

    public ArrayList<TodoList> getTodoLists() {
        return todoLists;
    }

    public TodoList getItemById(int id) {
        for (TodoList todoList: todoLists) {
            if (todoList.getId() == id) {
                return todoList;
            }
        }

        return null;
    }

    public void addItem(TodoList todoList) {
        todoLists.add(todoList);
    }

    public void deleteItemById(int id) {
        TodoList toDelete = null;

        for (TodoList todoList: todoLists) {
            if (todoList.getId() == id) {
                toDelete = todoList;
            }
        }

        if (toDelete != null) {
            todoLists.remove(toDelete);
        }
    }
}
