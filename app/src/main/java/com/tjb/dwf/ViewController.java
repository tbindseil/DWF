package com.tjb.dwf;

public class ViewController {
    private MainActivity mainActivity;

    public ViewController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showPicture() {
        mainActivity.showPicture();
    }

    public void showOptions() {
        mainActivity.showOptions();
    }
}
