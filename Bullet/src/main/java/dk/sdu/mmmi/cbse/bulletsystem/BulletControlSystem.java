/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Michael-PC
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),
    @ServiceProvider(service = IGamePluginService.class)})

public class BulletControlSystem implements IEntityProcessingService, IGamePluginService {

    private ArrayList<Entity> bullets;

    @Override
    public void start(GameData gameData, World world) {
        bullets = new ArrayList<Entity>();
    }

    private Entity createBullet(GameData gameData, float x, float y, float radian) {
        Entity bulletInstance = new Entity();
        bulletInstance.setType(EntityType.BULLET);

        bulletInstance.setSpeed(300);

        bulletInstance.setX(x + 15 * (float) Math.cos(radian));
        bulletInstance.setY(y + 15 * (float) Math.sin(radian));

        bulletInstance.setRadians(radian);

        return bulletInstance;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : bullets) {
            world.removeEntity(e);
            bullets.remove(e);
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Event e : gameData.getEvents()) {
            if (e.getType() == EventType.PLAYER_SHOOT) {
                Entity bullet = createBullet(gameData, world.getEntity(e.getEntityID()).getX(), world.getEntity(e.getEntityID()).getY(), world.getEntity(e.getEntityID()).getRadians());
                world.addEntity(bullet);
                bullets.add(bullet);
                gameData.removeEvent(e);
            }
            if (e.getType() == EventType.ENEMY_SHOOT) {
                Entity bullet = createBullet(gameData, world.getEntity(e.getEntityID()).getX(), world.getEntity(e.getEntityID()).getY(), world.getEntity(e.getEntityID()).getRadians());
                world.addEntity(bullet);
                bullets.add(bullet);
                gameData.removeEvent(e);
            }
        }

        for (Entity entity : world.getEntities(EntityType.BULLET)) {
            if (entity.getIsHit()) {
                bullets.remove(entity);
                world.removeEntity(entity);
            }
            entity.setX(entity.getX() + entity.getSpeed() * (float) Math.cos(entity.getRadians()) * gameData.getDelta());
            entity.setY(entity.getY() + entity.getSpeed() * (float) Math.sin(entity.getRadians()) * gameData.getDelta());

            entity.setShapeX(new float[]{/*Left*/entity.getX() + 2.5f * (float) Math.cos(entity.getRadians() + Math.PI * 0.8),
                /*Forward*/ entity.getX() + 3 * (float) Math.cos(entity.getRadians()),
                /*Right*/ entity.getX() + 2.5f * (float) Math.cos(entity.getRadians() + Math.PI * 1.2),
                /*Backward*/ entity.getX() + 1.25f * (float) Math.cos(entity.getRadians() + Math.PI)});

            entity.setShapeY(new float[]{/*Left*/entity.getY() + 2.5f * (float) Math.sin(entity.getRadians() + Math.PI * 0.8),
                /*Forward*/ entity.getY() + 3 * (float) Math.sin(entity.getRadians()),
                /*Right*/ entity.getY() + 2.5f * (float) Math.sin(entity.getRadians() + Math.PI * 1.2),
                /*Backward*/ entity.getY() + 1.25f * (float) Math.sin(entity.getRadians() + Math.PI)});
        }
    }

}
