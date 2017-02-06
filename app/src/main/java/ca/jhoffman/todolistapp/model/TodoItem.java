package ca.jhoffman.todolistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jhoffman on 2016-10-16.
 */

public class TodoItem implements Parcelable {
    private static int autoId = 0;

    private int id;
    private String name;
    private boolean done;

    private TodoItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        done = in.readByte() != 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public TodoItem(String name) {
        this.id = autoId++;
        this.name = name;
        this.done = false;
    }

    public void toggleDone() {
        done = !done;
    }

    // Parcelable

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (done ? 1 : 0));
    }
}
