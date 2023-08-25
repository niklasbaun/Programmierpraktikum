package de.umr.ds.task1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

public class ImageProcessing {

    /**
     * Loads an image from the given file path
     *
     * @param path The path to load the image from
     * @return BufferedImage
     */
    private static BufferedImage loadImage(String path) throws IOException {
        //get image file
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return image;
    }


    /**
     * Converts the input RGB image to a single-channel gray scale array.
     *
     * @param img The input RGB image
     * @return A 2-D array with intensities
     */
    private static int[][] convertToGrayScaleArray(BufferedImage img) {
        //create array
        int[][] grayScaleArray = new int[img.getWidth()][img.getHeight()];
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to gray scale
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //get rgb values
                int rgb = img.getRGB(i, j);
                //get single values as described here: https://stackoverflow.com/questions/2615522/java-bufferedimage-getting-red-green-and-blue-individually
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                //calculate gray scale value by formula from task
                int grayScale = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                //set gray scale value
                grayScaleArray[i][j] = grayScale;
            }
        }
        return grayScaleArray;
    }

    /**
     * Converts a single-channel (gray scale) array to an RGB image.
     *
     * @param img The input image array
     * @return BufferedImage
     */
    private static BufferedImage convertToBufferedImage(int[][] img) {
        //create new buffered image
        BufferedImage bufferedImage = new BufferedImage(img.length, img[0].length, BufferedImage.TYPE_INT_RGB);
        //convert to buffered image
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                //set rgb values (same bit shift as in convertToGrayScaleArray)
                int grayScale = Math.max(0, Math.min(255, img[i][j]));
                int rgb = (0xFF << 24) | (grayScale << 16) | (grayScale << 8) | grayScale;
                //set rgb value
                bufferedImage.setRGB(i, j, rgb);
            }
        }
        return bufferedImage;
    }

    /**
     * Saves an image to the given file path
     *
     * @param img  The RGB image
     * @param path The path to save the image to
     *             (e.g. "C:\\Users\\User\\Desktop\\image.png")
     */
    private static void saveImage(BufferedImage img, String path) throws IOException {
        //save image
        try {
            ImageIO.write(img, "jpg", new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Converts input image to grayscale and applies the kernel.
     *
     * @param img    The RGB input image
     * @param kernel The kernel to apply
     * @return The convolved gray-scale image
     */
    private static BufferedImage filter(BufferedImage img, Kernel kernel) {
        //convert to gray scale
        int[][] grayScaleArray = convertToGrayScaleArray(img);
        //use the kernel
        int[][] convolved = kernel.convolve(grayScaleArray);

        //convert to buffered image
        BufferedImage convolvedImage = convertToBufferedImage(convolved);

        //resize the convolved image to the original size
        BufferedImage resizedImage = resize(img, convolvedImage);
        System.out.println("Old Size");
        System.out.println(img.getWidth());
        System.out.println(img.getHeight());
        System.out.println("New Size");
        System.out.println(resizedImage.getWidth());
        System.out.println(resizedImage.getHeight());
        return convolvedImage;
    }


    /**
     * helper method to resize the convolved image to the original size
     * @param img the original image
     * @param convolved the convolved image
     */
    private static BufferedImage resize(BufferedImage img, BufferedImage convolved){
        //get size of original image
        int width = img.getWidth();
        int height = img.getHeight();

        //resize the convolved image to the original size
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //set the Color
        resized.getGraphics().setColor(Color.red);
        resized.getGraphics().drawImage(convolved, 0, 0, null);

        return resized;
    }


    public static void main(String[] args) {
        // open image
        BufferedImage img = null;
        try {
            img = loadImage("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\example.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // convert to gray scale
        int[][] grayScaleArray = convertToGrayScaleArray(img);
        //convert gray scale array to buffered image
        BufferedImage grayScaleImage = convertToBufferedImage(grayScaleArray);
        //save gray scale image
        try {
            saveImage(grayScaleImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\exampleGrayScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //gaussenblur 5x5
        Kernel gauss = Kernels.GaussianBlur5x5();
        BufferedImage gaussedImage = filter(img, gauss);
        try {
            saveImage(gaussedImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\gaussedScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //try boxblur
        Kernel boxBlur = Kernels.BoxBlur3x3();
        BufferedImage boxBlurImg = filter(img, boxBlur);
        try {
            saveImage(boxBlurImg, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\boxBlurScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //EdgeDetection
        Kernel edgeDetection = Kernels.EdgeDetection();
        BufferedImage edgeImage = filter(img, edgeDetection);
        try {
            saveImage(edgeImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\edgeDetectionScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get sharpen kernel
        Kernel sharp = Kernels.Sharpen();
        BufferedImage sharpenedImage = filter(img, sharp);
        try {
            saveImage(sharpenedImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\sharpenedScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Identity
        Kernel identity = Kernels.Identity();
        BufferedImage identityImage = filter(img, identity);
        try {
            saveImage(identityImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\identityScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Relief
        Kernel relief = Kernels.Relief();
        BufferedImage reliefImage = filter(img, relief);
        try {
            saveImage(reliefImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\reliefScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //MotionBlur
        Kernel motion = Kernels.MotionBlur();
        BufferedImage motionImage = filter(img, motion);
        try {
            saveImage(motionImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\motionScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //unsharpMasking
        Kernel unsharp = Kernels.unsharpMasking();
        BufferedImage unsharpImage = filter(img, unsharp);
        try {
            saveImage(unsharpImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\unsharpScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //whatEver
        Kernel whatEver = Kernels.whatEver();
        BufferedImage whatEverImage = filter(img, whatEver);
        try {
            saveImage(whatEverImage, "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\verteilteSysteme\\whatEverScale.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
