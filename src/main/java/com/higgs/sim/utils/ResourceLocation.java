package com.higgs.sim.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLocation {
    private final File resource;
    private final String dir;

    public ResourceLocation(final String dir) {
        this.dir = dir;
        this.resource = new File(ResourceLocation.getDefaultLocation() + dir);
    }

    public String getDirectory() {
        return this.dir;
    }

    public File getResource() {
        return this.resource;
    }

    private static String getDefaultLocation() {
        return Utils.getWorkingDir() + "/resources/";
    }

    public static BufferedImage getImage(final String path) {
        try {
            return ImageIO.read(new ResourceLocation(path).getResource());
        } catch (final IOException e) {
            return null;
        }

    }
}
