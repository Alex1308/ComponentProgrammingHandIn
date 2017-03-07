/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Michael-PC
 */
@ServiceProvider(service = IEntityProcessingService.class)

public class CollisionControlSystem implements IEntityProcessingService {

    public boolean collidedWith(Shape s1, Shape s2) {
        return s1.getBounds2D().intersects(s2.getBounds2D()) || s2.getBounds2D().intersects(s1.getBounds2D());
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(EntityType.ASTEROIDS, EntityType.BULLET, EntityType.ENEMY, EntityType.PLAYER)) {
            if (entity.isCollision()) {
                if (entity.getType().equals(EntityType.PLAYER) || entity.getType().equals(EntityType.ENEMY)) {
                    Polygon p = new Polygon();
                    for (int i = 0; i < entity.getShapeX().length; i++) {
                        p.addPoint((int) (entity.getShapeX()[i] * 10), (int) (entity.getShapeY()[i] * 10));
                    }
                    for (Entity n : world.getEntities(EntityType.BULLET)) {
                        Polygon p2 = new Polygon();
                        for (int i = 0; i < n.getShapeX().length; i++) {
                            p2.addPoint((int) (n.getShapeX()[i] * 10), (int) (n.getShapeY()[i] * 10));
                        }
                        if (collidedWith(p, p2)) {
                            entity.setIsHit(true);
                            n.setIsHit(true);
                        }
                    }
                }

                if (entity.getType() == EntityType.ASTEROIDS) {
                    Polygon p = new Polygon();
                    for (int i = 0; i < entity.getShapeX().length; i++) {
                        p.addPoint((int) (entity.getShapeX()[i] * 10), (int) (entity.getShapeY()[i] * 10));
                    }
                    for (Entity n : world.getEntities(EntityType.BULLET)) {
                        Polygon p2 = new Polygon();
                        for (int i = 0; i < n.getShapeX().length; i++) {
                            p2.addPoint((int) (n.getShapeX()[i] * 10), (int) (n.getShapeY()[i] * 10));
                        }
                        if (collidedWith(p, p2)) {
                            entity.setIsHit(true);
                            n.setIsHit(true);
                        }
                    }
                    for (Entity n : world.getEntities(EntityType.PLAYER)) {
                        Polygon p2 = new Polygon();
                        for (int i = 0; i < n.getShapeX().length; i++) {
                            p2.addPoint((int) (n.getShapeX()[i] * 10), (int) (n.getShapeY()[i] * 10));
                        }
                        if (collidedWith(p, p2)) {
                            n.setLife(0);
                        }
                    }
                }
            }
        }
    }
}
