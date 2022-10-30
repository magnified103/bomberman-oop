package com.myproject.bomberman.components;

import com.myproject.bomberman.core.Component;

import java.util.function.BiConsumer;

public class TimerComponent extends Component {

    private double remainingTime;
    private double elapsedTime;
    private boolean stopped;
    private BiConsumer<TimerComponent, Double> onFinishFunc;

    public TimerComponent(double time) {
        remainingTime = time;
        elapsedTime = 0;
        stopped = false;
    }

    public TimerComponent(double time, BiConsumer<TimerComponent, Double> onFinishFunc) {
        this(time);
        this.onFinishFunc = onFinishFunc;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void tick(double tpf) {
        if (!stopped) {
            remainingTime -= tpf;
            elapsedTime += tpf;
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        stopped = true;
    }

    public void resume() {
        stopped = false;
    }

    public void reset(double time) {
        remainingTime = time;
        elapsedTime = 0;
        stopped = false;
    }

    public boolean isFinished() {
        return remainingTime <= 0;
    }

    public void setOnFinishFunc(BiConsumer<TimerComponent, Double> onFinishFunc) {
        this.onFinishFunc = onFinishFunc;
    }

    public void onFinish(double tpf) {
        if (onFinishFunc != null) {
            onFinishFunc.accept(this, tpf);
        }
    }
}
