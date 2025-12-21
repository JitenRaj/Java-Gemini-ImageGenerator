package com.project.model;

public class ImageEntry {

    private final int id;
    private final String prompt;
    private final String imageBase64;

    public ImageEntry(int id, String prompt, String imageBase64) {
        this.id = id;
        this.prompt = prompt;
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    @Override
    public String toString() {
        return "ImageEntry{id=" + id + ", prompt='" + prompt + "', imageBase64='" + imageBase64.substring(0, Math.min(30, imageBase64.length())) + "...'}";
    }
}
