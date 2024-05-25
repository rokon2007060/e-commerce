package com.example.e_commerce_app;
import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResource implements IdlingResource {

    private ResourceCallback callback;
    private boolean isIdle = false;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean isIdle) {
        this.isIdle = isIdle;
        if (isIdle && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
