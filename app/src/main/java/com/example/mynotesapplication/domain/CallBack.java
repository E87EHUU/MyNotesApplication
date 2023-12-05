package com.example.mynotesapplication.domain;

public interface CallBack<T> {

    void onSuccess(T data); //передает данные, на которые подписались

    void onError(Throwable exception); //передает ошибки
}
