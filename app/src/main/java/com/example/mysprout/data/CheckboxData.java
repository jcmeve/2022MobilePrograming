package com.example.mysprout.data;

public class CheckboxData {
    long id;
    boolean checked;

    public CheckboxData(long itemId, boolean t) {
        id = itemId;
        checked = t;
    }

    public long getId() {
        return this.id;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setId(long i){
        this.id = i;
    }

    public void setChecked(boolean c){
        this.checked = c;
    }
}
