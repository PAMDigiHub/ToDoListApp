package ca.jhoffman.todolistapp.activities;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.model.TodoList;
import ca.jhoffman.todolistapp.model.TodoListsProvider;

/**
 * Created by jhoffman on 2016-10-15.
 */
public class TodoListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<TodoList> lists;

    public TodoListAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        lists = sort();
    }

    private ArrayList<TodoList> sort() {
        ArrayList<TodoList> list = (ArrayList<TodoList>) TodoListsProvider.getInstance().getTodoLists().clone();

        Collections.sort(list, new Comparator<TodoList>() {
            @Override
            public int compare(TodoList o1, TodoList o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        return list;
    }

    @Override
    public void notifyDataSetChanged() {
        lists = sort();

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_todolist, null);
        }

        TodoList todoList = (TodoList)getItem(position);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.row_todolist_list_name_textview);
        nameTextView.setText(todoList.getName());

        View doneImageView = convertView.findViewById(R.id.row_todolist_list_done_imageview);
        TextView progressTextView = (TextView)convertView.findViewById(R.id.row_todolist_list_progress_textview);

        if (todoList.isDone()) {
            doneImageView.setVisibility(View.VISIBLE);
            progressTextView.setVisibility(View.GONE);
        } else {
            doneImageView.setVisibility(View.GONE);
            progressTextView.setVisibility(View.VISIBLE);

            String progressText = String.format("(%d / %d)", todoList.getCompletedItemsCount(), todoList.getItemsCount());
            progressTextView.setText(progressText);
        }

        return convertView;
    }
}
