/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Michael-PC
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),
    @ServiceProvider(service = IGamePluginService.class)})

public class AsteroidControlSystem implements IEntityProcessingService, IGamePluginService {

    private Entity asteroid;

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 8; i++) {
            asteroid = createAsteroid(gameData, 0, 0, 0);
            world.addEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData, float radius, float x, float y) {
        Entity asteroidShip = new Entity();
        asteroidShip.setType(EntityType.ASTEROIDS);
        Random rand = new Random();

        float randomNumber = rand.nextFloat();

        if (x == 0 && y == 0) {
            if (rand.nextFloat() < 0.25f) {
                asteroidShip.setPosition(gameData.getDisplayWidth() * rand.nextFloat(), 0);
            } else if (0.25f <= rand.nextFloat() && rand.nextFloat() < 0.49f) {
                asteroidShip.setPosition(0, gameData.getDisplayHeight() * rand.nextFloat());
            } else if (0.50f <= rand.nextFloat() && rand.nextFloat() < 0.74f) {
                asteroidShip.setPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight() * rand.nextFloat());
            } else if (0.75f <= rand.nextFloat() && rand.nextFloat() < 1) {
                asteroidShip.setPosition(gameData.getDisplayWidth() * rand.nextFloat(), gameData.getDisplayHeight());
            }
        } else {
            asteroidShip.setPosition(x, y);
        }

        int[] sizes = {8, 16, 32};
        asteroidShip.setSpeed(80);
        if (radius == 0) {
            asteroidShip.setRadius(sizes[rand.nextInt(3)]);
        } else {
            asteroidShip.setRadius(radius);
        }
        asteroidShip.setRadians((float) (Math.PI * 2 * rand.nextFloat()));
        asteroidShip.setWrap(true);
        asteroidShip.setCollision(true);

        return asteroidShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(asteroid);
    }

    @Override
    public void process(GameData gameData, World world) {
        Random rand = new Random();

        for (Entity entity : world.getEntities(EntityType.ASTEROIDS)) {

            if (entity.getIsHit()) {
                gameData.addEvent(new Event(EventType.ASTEROID_SPLIT, entity.getID()));
            }

            for (Event e : gameData.getEvents()) {
                if (e.getType() == EventType.ASTEROID_SPLIT) {
                    for (int i = 0; i < 2; i++) {
                        if (world.getEntity(e.getEntityID()).getRadius() > 8) {
                            asteroid = createAsteroid(gameData, world.getEntity(e.getEntityID()).getRadius() / 2, world.getEntity(e.getEntityID()).getX(), world.getEntity(e.getEntityID()).getY());
                            world.addEntity(asteroid);
                        }
                    }
                    gameData.removeEvent(e);
                    world.removeEntity(e.getEntityID());
                }
            }
            //Moving
            entity.setX(entity.getX() + entity.getSpeed() * (float) Math.cos(entity.getRadians()) * gameData.getDelta());
            entity.setY(entity.getY() + entity.getSpeed() * (float) Math.sin(entity.getRadians()) * gameData.getDelta());

            entity.setShapeX(new float[]{
                entity.getX() - entity.getRadius(),
                entity.getX() - entity.getRadius() / 2,
                entity.getX() + entity.getRadius() / 2,
                entity.getX() + entity.getRadius(),
                entity.getX() + entity.getRadius() / 2,
                entity.getX() - entity.getRadius() / 2});
            entity.setShapeY(new float[]{
                entity.getY(),
                entity.getY() + (float) Math.sqrt((Math.pow(entity.getRadius(), 2) - Math.pow(entity.getRadius() / 2, 2))),
                entity.getY() + (float) Math.sqrt((Math.pow(entity.getRadius(), 2) - Math.pow(entity.getRadius() / 2, 2))),
                entity.getY(),
                entity.getY() - (float) Math.sqrt((Math.pow(entity.getRadius(), 2) - Math.pow(entity.getRadius() / 2, 2))),
                entity.getY() - (float) Math.sqrt((Math.pow(entity.getRadius(), 2) - Math.pow(entity.getRadius() / 2, 2)))});

        }

    }

    public void split(Entity e, World world, GameData gameData) {
        if (e.getRadius() > 4) {
            gameData.getEvents().add(new Event(EventType.ASTEROID_SPLIT, e.getID()));
        } else {
            world.removeEntity(e);
        }
    }
}
