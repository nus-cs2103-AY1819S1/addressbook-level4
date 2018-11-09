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
}
