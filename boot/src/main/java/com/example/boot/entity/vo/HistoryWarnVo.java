package com.example.boot.entity.vo;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/26
 */
public class HistoryWarnVo {


    private List<String> content;
    private Pageable pageable;
    private int totalPages;
    private boolean last;
    private int totalElements;
    private boolean first;
    private Sort sort;
    private int numberOfElements;
    private int size;
    private int number;
    private boolean empty;
    public void setContent(List<String> content) {
        this.content = content;
    }
    public List<String> getContent() {
        return content;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
    public Pageable getPageable() {
        return pageable;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
    public boolean getLast() {
        return last;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
    public int getTotalElements() {
        return totalElements;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
    public boolean getFirst() {
        return first;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
    public Sort getSort() {
        return sort;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
    public boolean getEmpty() {
        return empty;
    }

}
