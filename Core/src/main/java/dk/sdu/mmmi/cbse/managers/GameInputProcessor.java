package dk.sdu.mmmi.cbse.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

	public boolean keyDown(int k) {
		if(k == Keys.W) {
                    gameData.getKeys().setKey(GameKeys.W, true);
		}
		if(k == Keys.A) {
                    gameData.getKeys().setKey(GameKeys.A, true);
		}
		if(k == Keys.S) {
                    gameData.getKeys().setKey(GameKeys.S, true);
		}
		if(k == Keys.D) {
                    gameData.getKeys().setKey(GameKeys.D, true);
		}
		if(k == Keys.ENTER) {
                    gameData.getKeys().setKey(GameKeys.ENTER, true);
		}
		if(k == Keys.ESCAPE) {
                    gameData.getKeys().setKey(GameKeys.ESCAPE, true);
		}
		if(k == Keys.SPACE) {
                    gameData.getKeys().setKey(GameKeys.SPACE, true);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
                    gameData.getKeys().setKey(GameKeys.SHIFT, true);
		}
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.W) {
                    gameData.getKeys().setKey(GameKeys.W, false);
		}
		if(k == Keys.A) {
                    gameData.getKeys().setKey(GameKeys.A, false);
		}
		if(k == Keys.S) {
                    gameData.getKeys().setKey(GameKeys.S, false);
		}
		if(k == Keys.D) {
                    gameData.getKeys().setKey(GameKeys.D, false);
		}
		if(k == Keys.ENTER) {
                    gameData.getKeys().setKey(GameKeys.ENTER, false);
		}
		if(k == Keys.ESCAPE) {
                    gameData.getKeys().setKey(GameKeys.ESCAPE, false);
		}
		if(k == Keys.SPACE) {
                    gameData.getKeys().setKey(GameKeys.SPACE, false);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
                    gameData.getKeys().setKey(GameKeys.SHIFT, false);
		}
		return true;
	}
	
}








