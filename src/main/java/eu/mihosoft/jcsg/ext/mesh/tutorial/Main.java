/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.jcsg.ext.mesh.tutorial;

import eu.mihosoft.jcsg.ext.mesh.MeshTools;
import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Cube;
import eu.mihosoft.vrl.v3d.Sphere;
import eu.mihosoft.vrl.v3d.Transform;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sample.
 * 
 * @author Michael Hoffer (info@michaelhoffer.de)
 */
public class Main {

    public static void main(String[] args) {
        // we use cube and sphere as base geometries
        CSG cube = new Cube(2).toCSG();
        CSG sphere = new Sphere(1.25).toCSG();

        // compute difference between cube and sphere
        CSG cubeMinusSphere = cube.difference(sphere);

        // create a copy of cube-sphere that shall be optimized
        CSG optimized = cubeMinusSphere.
                transformed(Transform.unity().translateX(3));

        // perform the optimization
        CSG all = MeshTools.optimize(
                optimized, // csg object to optimize
                1e-6,      // tolerance
                1e-4,      // max tolerance
                0.25,      // min edge length
                1.5        // max edge length
        );

        try {
            // save optimized mesh as "all.stl"
            Files.write(Paths.get("all.stl"), all.toStlString().getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
