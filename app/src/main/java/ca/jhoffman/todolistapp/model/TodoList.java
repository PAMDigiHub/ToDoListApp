package ca.jhoffman.todolistapp.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jhoffman on 2016-10-15.
 */
public class TodoList {
    private static int autoId = 0;

    private int id;
    private String name;
    private ArrayList<TodoItem> items;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TodoItem> getAllItems() {
        return items;
    }

    public TodoList(String name) {
        this.id = autoId++;
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void addItem(TodoItem todoItem) {
        items.add(todoItem);
    }

    public boolean isDone() {
        for (TodoItem item: items) {
            if (item.getDone() == false) {
                return false;
            }
        }

        return true;
    }

    public int getCompletedItemsCount() {
        int doneCount = 0;

        for (TodoItem item: items) {
            if (item.getDone() == true) {
                doneCount++;
            }
        }
        return doneCount;
    }

    public int getItemsCount() {
        return items.size();
    }

    public TodoItem getItemAtPosition(int position) {
        return items.get(position);
    }

    public void deleteItem(TodoItem item) {
        items.remove(item);
    }

    public void deleteItemById(int id) {
        TodoItem itemToDelete = null;

        for (TodoItem item: items) {
            if (item.getId() == id) {
                itemToDelete = item;
            }
        }

        if (itemToDelete != null) {
            items.remove(itemToDelete);
        }
    }

    public void addItems(ArrayList<TodoItem> items) {
        this.items.addAll(items);
    }
}
