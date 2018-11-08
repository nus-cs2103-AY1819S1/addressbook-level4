package seedu.jxmusic.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerManagerTest {

    private PlayerManager playerManager = PlayerManager.getInstance();

    @Test
    public void getInstance_sameInstance() {
        PlayerManager playerManager2 = PlayerManager.getInstance();
        assertEquals(playerManager, playerManager2);
    }

    @Test
    public void play() {
    }

    @Test
    public void play1() {
    }

    @Test
    public void play2() {
    }

    @Test
    public void stop() {
    }

    @Test
    public void pause() {
    }

    @Test
    public void seek() {
    }

    @Test
    public void getStatus() {
        assertEquals(Playable.Status.UNINITIALIZED, playerManager.getStatus());
    }
}
