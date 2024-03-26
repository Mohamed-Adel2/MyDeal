package com.mydeal.domain.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageResizer {

    public static byte[] resizeImage(byte[] originalImage, int newWidth, int newHeight) throws IOException {
        // Convert byte array to BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
        BufferedImage image = ImageIO.read(inputStream);

        // Resize the image
        BufferedImage resizedImage = resize(image, newWidth, newHeight);

        // Convert the resized image to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpeg", outputStream);
        byte[] resizedImageBytes = outputStream.toByteArray();

        // Close streams
        inputStream.close();
        outputStream.close();

        return resizedImageBytes;
    }

    private static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();

        return resizedImage;
    }
}
