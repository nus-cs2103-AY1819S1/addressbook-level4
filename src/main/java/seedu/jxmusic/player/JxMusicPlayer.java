package seedu.address.player;

/**
 * The actual implemented player to be used by Logic component
 */
public class JxMusicPlayer implements Player {
    private static PlayablePlaylist pp;
    @Override
    public void play() {
        // todo take in Playlist1 model as parameter, then construct PlayablePlaylist from it
        System.out.println("jxmusicplayer play");
        pp = new PlayablePlaylist();
        pp.play();
    }

    @Override
    public void stop() {
        System.out.println("jxmusicplayer stop");
        pp.stop();
    }
    @Override
    public void pause() {
        System.out.println("jxmusicplayer pause");
        pp.pause();
    }
}
