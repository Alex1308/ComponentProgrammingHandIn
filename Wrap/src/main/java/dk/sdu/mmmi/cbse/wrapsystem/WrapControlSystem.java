/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.wrapsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Michael-PC
 */
@ServiceProvider(service = IEntityProcessingService.class)

public class WrapControlSystem implements IEntityProcessingService {

    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(EntityType.ASTEROIDS, EntityType.BULLET, EntityType.ENEMY, EntityType.PLAYER)) {

            if (entity.getX() >= gameData.getDisplayWidth()) {
                if (entity.isWrap()) {
                    entity.setX(0);
                } else {
                    world.removeEntity(entity);
                }
            }
            if (entity.getY() >= gameData.getDisplayHeight()) {
                if (entity.isWrap()) {
                    entity.setY(0);
                } else {
                    world.removeEntity(entity);
                }
            }
            if (entity.getX() < 0) {
                if (entity.isWrap()) {
                    entity.setX(gameData.getDisplayWidth());
                } else {
                    world.removeEntity(entity);
                }
            }
            if (entity.getY() < 0) {
                if (entity.isWrap()) {
                    entity.setY(gameData.getDisplayHeight());
                } else {
                    world.removeEntity(entity);
                }
            }
        }
    }
}
