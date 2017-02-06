package ca.jhoffman.todolistapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.jhoffman.todolistapp.R;
import ca.jhoffman.todolistapp.model.TodoList;

public class ListsActivity extends AppCompatActivity {

    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_lists_add_list_fab);
        fab.setOnClickListener(fabClickListener);

        ListView todoListsListview = (ListView)findViewById(R.id.activity_lists_list_listview);
        todoListAdapter = new TodoListAdapter(this);
        todoListsListview.setAdapter(todoListAdapter);
        todoListsListview.setOnItemClickListener(onTodoListItemClickListener);
    }

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent newActivityIntent = new Intent(ListsActivity.this, AddTodoListActivity.class);
            startActivity(newActivityIntent);
        }
    };

    AdapterView.OnItemClickListener onTodoListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent editActivityIntent = new Intent(ListsActivity.this, EditTodoListActivity.class);

            TodoList todoList = (TodoList) todoListAdapter.getItem(position);
            editActivityIntent.putExtra(EditTodoListActivity.TODO_LIST_ID_EXTRA, todoList.getId());


            startActivity(editActivityIntent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        todoListAdapter.notifyDataSetChanged();
    }
}
