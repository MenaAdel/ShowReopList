package com.example.menaadel.zadtask.data;

/**
 * Created by MenaAdel on 1/16/2018.
 */

public interface RequestsObserver {
    void onSuccess(Object response);
    void onError(String message);
}
