package com.higgs.sim.obj;

import java.util.ArrayList;

public class Material {
    public static ArrayList<Material> MATERIAL_REGISTRY = new ArrayList<>();

    public double density = 1; //stored in mass/area

    public Material() {
        Material.MATERIAL_REGISTRY.add(this);
    }
}
