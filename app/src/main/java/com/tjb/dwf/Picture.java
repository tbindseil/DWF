package com.tjb.dwf;

public class Picture {
    private String title;

    private Picture(String pictureTitle) {
        title = pictureTitle;
    }

    static class Builder {
        private Picture picture;

        public Builder(String pictureTitle) {
            picture = new Picture(pictureTitle);
        }

        public Picture build() {
            return picture;
        }
    }
}
