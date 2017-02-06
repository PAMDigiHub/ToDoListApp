package ca.jhoffman.todolistapp.activities;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.model.TodoItem;
import ca.jhoffman.todolistapp.model.TodoList;
import ca.jhoffman.todolistapp.model.TodoListsProvider;

public class AddTodoListActivity extends TodoListActivity {
    private static final String KEY_ITEMS_STATE = "ca.jhoffman.todolistapp.KEY_ITEMS_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);

        currentToDoList = new TodoList("");

        if (savedInstanceState != null) {
            ArrayList<TodoItem> savedItems = savedInstanceState.getParcelableArrayList(KEY_ITEMS_STATE);
            currentToDoList.addItems(savedItems);
        }

        initViews();

        Button cancelButton = (Button) findViewById(R.id.activity_add_todo_list_cancel_button);
        cancelButton.setOnClickListener(cancelButtonClickListener);

        Button addButton = (Button) findViewById(R.id.activity_add_todo_list_add_button);
        addButton.setOnClickListener(addButtonClickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_ITEMS_STATE, currentToDoList.getAllItems());

        super.onSaveInstanceState(outState);
    }

    View.OnClickListener cancelButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener addButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isDataValid()) {
                TodoListsProvider.getInstance().addItem(currentToDoList);

                finish();
            } else {
                showInvalidDataToast();
            }
        }
    };
}
