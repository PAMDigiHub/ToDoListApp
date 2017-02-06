package ca.jhoffman.todolistapp.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.model.TodoItem;
import ca.jhoffman.todolistapp.model.TodoList;
import ca.jhoffman.todolistapp.model.TodoListsProvider;

/**
 * Created by jhoffman on 2016-10-16.
 */
public class TodoItemsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private TodoList todoList;
    private ArrayList<TodoItem> items;

    public TodoItemsAdapter(Context context, TodoList todoList) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.todoList = todoList;

        items = sort();
    }

    private ArrayList<TodoItem> sort() {
        ArrayList<TodoItem> list = (ArrayList<TodoItem>) todoList.getAllItems().clone();

        Collections.sort(list, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        return list;
    }

    @Override
    public void notifyDataSetChanged() {
        items = sort();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_todolist_item, null);
        }

        TodoItem item = (TodoItem) getItem(position);

        TextView nameTextview = (TextView) convertView.findViewById(R.id.row_todolist_item_name_textview);
        nameTextview.setText(item.getName());

        View doneView = convertView.findViewById(R.id.row_todolist_item_done_imageview);

        if (item.getDone() == true) {
            doneView.setVisibility(View.VISIBLE);
        } else {
            doneView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
