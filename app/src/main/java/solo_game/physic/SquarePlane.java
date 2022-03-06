/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solo_game.physic;

import org.joml.Vector3f;

/**
 *
 * @author abdulmelik
 */
public class SquarePlane extends Plane {

    private final Vector3f a;
    private final Vector3f b;
    private final Vector3f c;
    private final Vector3f d;

    public SquarePlane(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }


}
