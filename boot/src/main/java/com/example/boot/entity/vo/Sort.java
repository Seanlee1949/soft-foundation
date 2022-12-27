package com.example.boot.entity.vo;

/**
 * @author lishuai
 * @since 2022/11/26
 */
public class Sort {

    private boolean sorted;
    private boolean unsorted;
    private boolean empty;

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean getSorted() {
        return sorted;
    }

    public void setUnsorted(boolean unsorted) {
        this.unsorted = unsorted;
    }

    public boolean getUnsorted() {
        return unsorted;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean getEmpty() {
        return empty;
    }

}