package com.example.boot.entity.vo;

import com.example.boot.entity.dto.Record;
import com.example.boot.entity.response.CommonResponse;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class DeviceResult extends CommonResponse {
    private int current;
    private int[] orders;
    private int pages;
    private int size;
    private int total;
    private boolean searchCount;
    private Record[] records;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int[] getOrders() {
        return orders;
    }

    public void setOrders(int[] orders) {
        this.orders = orders;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }


    public Record[] getRecords() {
        return records;
    }

    public void setRecords(Record[] records) {
        this.records = records;
    }
}
