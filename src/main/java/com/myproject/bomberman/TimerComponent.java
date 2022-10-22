package com.myproject.bomberman;

public class TimerComponent extends Component {

    private double remainingTime;
    private double elapsedTime;
    private boolean stopped;

    public TimerComponent(double time) {
        remainingTime = time;
        elapsedTime = 0;
        stopped = false;
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
}
