package ca.jhoffman.todolistapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.dialogs.ValidateDeleteTodoItemDialog;
import ca.jhoffman.todolistapp.model.TodoItem;
import ca.jhoffman.todolistapp.model.TodoList;

public class TodoListActivity extends AppCompatActivity implements ValidateDeleteTodoItemDialog.ValidateItemDeleteDialogListener {
    protected EditText nameEditText;
    protected EditText newItemNameEditText;
    protected ListView itemsListview;

    protected TodoList currentToDoList;
    protected TodoItemsAdapter itemsAdapter;

    protected void initViews() {
        nameEditText = (EditText) findViewById(R.id.form_todo_list_list_name_edittext);

        newItemNameEditText = (EditText) findViewById(R.id.form_todo_list_items_new_item_name_edittext);

        Button addItemButton = (Button) findViewById(R.id.form_todo_list_items_add_item_button);
        addItemButton.setOnClickListener(addItemButtonClickListener);

        itemsListview = (ListView) findViewById(R.id.form_todo_list_items_todo_items_listview);
        itemsAdapter = new TodoItemsAdapter(this, currentToDoList);
        itemsListview.setAdapter(itemsAdapter);
        itemsListview.setOnItemLongClickListener(onItemLongClickListener);
        itemsListview.setOnItemClickListener(onItemClickListener);
    }

    View.OnClickListener addItemButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newItemName = newItemNameEditText.getText().toString().trim();

            if (newItemName.isEmpty()) {
                Toast.makeText(TodoListActivity.this, "Specify an item name!", Toast.LENGTH_LONG).show();
            } else {
                newItemNameEditText.setText("");
                currentToDoList.addItem(new TodoItem(newItemName));
                itemsAdapter.notifyDataSetChanged();

                onItemsChanged();
            }
        }
    };

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TodoItem item = (TodoItem) itemsAdapter.getItem(position);
            item.toggleDone();
            itemsAdapter.notifyDataSetChanged();

            onItemsChanged();
        }
    };

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (currentToDoList.getItemsCount() == 1) {
                Toast.makeText(TodoListActivity.this, "A todo list must have at least 1 item!", Toast.LENGTH_LONG).show();
            } else {
                TodoItem item = (TodoItem) itemsAdapter.getItem(position);

                ValidateDeleteTodoItemDialog.newInstance(item.getId()).show(getSupportFragmentManager(), "ValidateDeleteDialogFragment");
            }

            return true;
        }
    };

    protected void onItemsChanged() {
        //Default do nothing
        itemsAdapter.notifyDataSetChanged();
    }

    protected String getNameEditTextValue() {
        return nameEditText.getText().toString().trim();
    }

    protected boolean isDataValid() {
        String nameText = getNameEditTextValue();

        if (nameText.isEmpty() == false && currentToDoList.getItemsCount() > 0) {
            currentToDoList.setName(nameText);

            return true;
        }

        return false;
    }

    protected void showInvalidDataToast() {
        Toast.makeText(TodoListActivity.this, "Specify a name and a least 1 item!", Toast.LENGTH_LONG).show();
    }

    //ValidateItemDeleteDialogListener

    @Override
    public void onItemDeletionConfirmed(int id) {
        currentToDoList.deleteItemById(id);

        onItemsChanged();
    }
}
