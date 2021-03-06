package ca.jhoffman.todolistapp.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jhoffman on 2016-10-16.
 */

public class ValidateDeleteTodoItemDialog extends DialogFragment {

    private static final String ARGS_KEY_ID = "ca.jhoffman.todolistapp.ARGS_KEY_ID";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete item")
                .setMessage("Are you sure you want to delete this todo item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = getArguments().getInt(ARGS_KEY_ID, -1);
                        ((ValidateDeleteTodoItemDialog.ValidateItemDeleteDialogListener)getActivity()).onItemDeletionConfirmed(id);
                    }
                })
                .setNegativeButton("No", null);

        return builder.create();
    }

    public static ValidateDeleteTodoItemDialog newInstance(int id) {
        ValidateDeleteTodoItemDialog dialog = new ValidateDeleteTodoItemDialog();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY_ID, id);
        dialog.setArguments(args);

        return dialog;
    }

    // ValidateDeleteDialogListener

    public interface ValidateItemDeleteDialogListener {
        void onItemDeletionConfirmed(int id);
    }
}
