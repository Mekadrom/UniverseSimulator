package main.java.com.higgs.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLocation {
    private File resource;
    private String dir;

    public ResourceLocation(String dir) {
        this.dir = dir;
        this.resource = new File(ResourceLocation.getDefaultLocation() + dir);
    }

    public String getDirectory() {
        return dir;
    }

    public File getResource() {
        return resource;
    }

    private static String getDefaultLocation() {
        return Utils.getWorkingDir() + "/resources/";
    }

    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(new ResourceLocation(path).getResource());
        } catch(IOException e) {
            return null;
        }

    }
}
