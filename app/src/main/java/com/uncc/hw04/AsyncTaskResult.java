package com.uncc.hw04;

/**
 * Homework #04
 * File Name:AsyncTaskResult.java
 * Full Name of the student:
 * 1. Sai Kandimalla
 * 2. Andy Le
 */
public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public AsyncTaskResult(Exception error) {
        super();
        this.error = error;
    }
}