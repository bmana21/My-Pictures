package com.example.mypictures.generator;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ProgressiveJPEGGenerator {
    public static byte[] convertToProgressiveJPEG(MultipartFile file) throws IOException {

        BufferedImage image = ImageIO.read(file.getInputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, "JPEG", baos);

        byte[] progressiveJPEG = baos.toByteArray();

        return progressiveJPEG;
    }

}
