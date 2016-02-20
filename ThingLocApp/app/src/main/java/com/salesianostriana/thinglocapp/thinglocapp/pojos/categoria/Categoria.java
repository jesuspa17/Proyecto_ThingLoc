package com.salesianostriana.thinglocapp.thinglocapp.pojos.categoria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class Categoria {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<ResultCategoria> results = new ArrayList<ResultCategoria>();

    /**
     * No args constructor for use in serialization
     */
    public Categoria() {
    }

    /**
     * @param results
     * @param previous
     * @param count
     * @param next
     */
    public Categoria(Integer count, Object next, Object previous, List<ResultCategoria> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The next
     */
    public Object getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Object next) {
        this.next = next;
    }

    /**
     * @return The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     * @return The results
     */
    public List<ResultCategoria> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<ResultCategoria> results) {
        this.results = results;
    }

}
