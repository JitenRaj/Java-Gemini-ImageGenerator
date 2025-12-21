package com.project.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class ImageManager {
    private final List<ImageEntry> images;

    public ImageManager() {
        this.images = new ArrayList<>();
    }

    public String imageToBase64(byte[] imageBytes) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error processing image: " + e.getMessage());
        }
    }

    public String addImage(String prompt, byte[] imageBytes) {
        String imageBase64 = imageToBase64(imageBytes);
        int id = images.size();
        ImageEntry imageEntry = new ImageEntry(id, prompt, imageBase64);
        images.add(imageEntry);
        return imageBase64;
    }

    public List<ImageEntry> getImages() {
        return images;
    }
}
