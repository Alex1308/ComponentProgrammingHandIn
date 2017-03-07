package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import static dk.sdu.mmmi.cbse.common.data.EntityType.PLAYER;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author jcs
 */
@ServiceProviders(value={
        @ServiceProvider (service = IEntityProcessingService.class),
        @ServiceProvider(service = IGamePluginService.class)})

public class PlayerControlSystem implements IEntityProcessingService, IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        Entity playerShip = new Entity();
        playerShip.setType(PLAYER);

        playerShip.setPosition(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        playerShip.setMaxSpeed(150);
        playerShip.setAcceleration(30);
        playerShip.setDeacceleration(5);
        playerShip.setWrap(true);
        playerShip.setLife(5);
        playerShip.setCollision(true);

        playerShip.setRadians(3.1415f / 2);
        playerShip.setRotationSpeed(2);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(EntityType.PLAYER)) {

            if (gameData.getKeys().isDown(0)) {
                //Forward
                if (entity.getSpeed() < entity.getMaxSpeed()) {
                    entity.setSpeed(entity.getSpeed() + entity.getAcceleration());
                } else {
                    entity.setSpeed(entity.getMaxSpeed());
                }
            }
            if (gameData.getKeys().isDown(2)) {
                //Backward
                if (entity.getSpeed() > -entity.getMaxSpeed()) {
                    entity.setSpeed(entity.getSpeed() - entity.getAcceleration());
                } else {
                    entity.setSpeed(-entity.getMaxSpeed());
                }
            }
            if (gameData.getKeys().isDown(1)) {
                //Left
                entity.setRadians(entity.getRadians() + entity.getRotationSpeed() * 0.75f * (float) Math.PI * gameData.getDelta());
            }

            if (gameData.getKeys().isDown(3)) {
                //Right
                entity.setRadians(entity.getRadians() - entity.getRotationSpeed() * 0.75f * (float) Math.PI * gameData.getDelta());
            }
            if (gameData.getKeys().isPressed(6)) {
                //Shoot
                gameData.addEvent(new Event(EventType.PLAYER_SHOOT, entity.getID()));
            }
            if (entity.getSpeed() > 0 && !gameData.getKeys().isDown(1) && !gameData.getKeys().isDown(1)) {
                entity.setSpeed(entity.getSpeed() - entity.getDeacceleration());
            }
            if (entity.getSpeed() < 0 && !gameData.getKeys().isDown(1) && !gameData.getKeys().isDown(1)) {
                entity.setSpeed(entity.getSpeed() + entity.getDeacceleration());
            }

            entity.setX(entity.getX() + entity.getSpeed() * (float) Math.cos(entity.getRadians()) * gameData.getDelta());
            entity.setY(entity.getY() + entity.getSpeed() * (float) Math.sin(entity.getRadians()) * gameData.getDelta());

            entity.setShapeX(new float[]{/*Left*/entity.getX() + 10 * (float) Math.cos(entity.getRadians() + Math.PI * 0.8),
                /*Forward*/ entity.getX() + 12 * (float) Math.cos(entity.getRadians()),
                /*Right*/ entity.getX() + 10 * (float) Math.cos(entity.getRadians() + Math.PI * 1.2),
                /*Backward*/ entity.getX() + 5 * (float) Math.cos(entity.getRadians() + Math.PI)});

            entity.setShapeY(new float[]{/*Left*/entity.getY() + 10 * (float) Math.sin(entity.getRadians() + Math.PI * 0.8),
                /*Forward*/ entity.getY() + 12 * (float) Math.sin(entity.getRadians()),
                /*Right*/ entity.getY() + 10 * (float) Math.sin(entity.getRadians() + Math.PI * 1.2),
                /*Backward*/ entity.getY() + 5 * (float) Math.sin(entity.getRadians() + Math.PI)});
        }

    }
}
