package com.example.mypictures.validator;

import com.example.mypictures.constant.GoogleCloudConstants;
import com.example.mypictures.constant.PhotoConstants;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class PhotoValidator {

    public static boolean photoIsLarge(MultipartFile photo) {
        return (photo.getSize() / 1024) > PhotoConstants.PHOTO_SIZE;
    }

    public static byte[] scalePhoto(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage scaledImage = scaleImageWithQualityControl(originalImage, originalImage.getWidth(), originalImage.getHeight(), PhotoConstants.PHOTO_SIZE);
        return convertImageToByteArray(scaledImage);
    }

    private static BufferedImage scaleImageWithQualityControl(BufferedImage originalImage, int targetWidth, int targetHeight, int maxFileSizeKB) throws IOException {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        scaledImage.getGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1.0f);
        writer.setOutput(new MemoryCacheImageOutputStream(baos));
        IIOImage iioImage = new IIOImage(scaledImage, null, null);

        boolean qualityControlled = false;
        float quality = 1.0f;

        while (!qualityControlled) {
            writer.write(null, iioImage, param);
            byte[] imageBytes = baos.toByteArray();
            if (imageBytes.length / 1024 <= maxFileSizeKB) {
                qualityControlled = true;
            } else {
                quality -= 0.1f;
                param.setCompressionQuality(quality);
                baos.reset();
            }
        }

        writer.dispose();

        return scaledImage;
    }

    private static byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }

    public static String changeToJPNG(String fileName) {
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + ".png";
        return fileName;
    }

    public static String getOriginalFileName(String originalFileName, User user, Album album, AlbumRepository albumRepository, PhotoRepository photoRepository, boolean isAlbum) {
        String save = originalFileName;
        int index = 1;
        if (!isAlbum) {
            while (photoRepository.findByAlbumAndUserAndName(album, user, originalFileName) != null) {
                originalFileName = save;
                originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")) + GoogleCloudConstants.SPLIT + index + originalFileName.substring(originalFileName.lastIndexOf("."));
                index++;
            }
        } else {
            while (albumRepository.findByUserAndSaveName(user, originalFileName) != null) {
                originalFileName = save;
                originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")) + GoogleCloudConstants.SPLIT + index + originalFileName.substring(originalFileName.lastIndexOf("."));
                index++;
            }
        }
        return originalFileName;
    }
}
