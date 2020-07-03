package com.tjb.dwf;

// todo, what about other Activities? Do they need ViewControllers? If so, this may be base class
public class ViewController {
    private MainActivity mainActivity;

    // What the fuck lol
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
