package ca.jhoffman.todolistapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.dialogs.ValidateDeleteTodoListDialog;
import ca.jhoffman.todolistapp.model.TodoItem;
import ca.jhoffman.todolistapp.model.TodoListsProvider;

public class EditTodoListActivity extends TodoListActivity implements ValidateDeleteTodoListDialog.ValidateDeleteTodoListDialogListener {

    public static final String TODO_LIST_ID_EXTRA = "ca.jhoffman.todolistapp.TODO_LIST_ID_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_list);

        int id = getIntent().getIntExtra(TODO_LIST_ID_EXTRA, -1);
        currentToDoList = TodoListsProvider.getInstance().getItemById(id);

        initViews();

        nameEditText.setText(currentToDoList.getName());
        updateProgress();

        Button deleteButton = (Button) findViewById(R.id.activity_edit_todo_list_delete_button);
        deleteButton.setOnClickListener(deleteButtonClickListener);
    }

    View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ValidateDeleteTodoListDialog().show(getSupportFragmentManager(), "ValidateDeleteTodoListDialog");
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            updateTodoList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        updateTodoList();
    }

    @Override
    protected void onItemsChanged() {
        super.onItemsChanged();

        updateProgress();
    }

    private void updateTodoList() {
        if (isDataValid()) {
            finish();
        } else {
            showInvalidDataToast();
        }
    }

    private void updateProgress() {
        TextView progressTextview = (TextView)findViewById(R.id.activity_edit_todo_list_items_progress_textview);
        TextView progressPercentTextview = (TextView)findViewById(R.id.activity_edit_todo_list_items_progress_percent_textview);

        float doneItems = currentToDoList.getCompletedItemsCount();
        float itemsCount = currentToDoList.getItemsCount();

        String progressText = String.format("%.0f / %.0f", doneItems, itemsCount);
        progressTextview.setText(progressText);

        String progressPercentText = String.format("%.0f %%", (Math.floor((doneItems / itemsCount) * 100f)));
        progressPercentTextview.setText(progressPercentText);
    }

    // ValidateDeleteTodoListDialogListener

    @Override
    public void onTodoListDeletionConfirmed() {
        TodoListsProvider.getInstance().deleteItemById(currentToDoList.getId());
        finish();
    }
}
