package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.GameSession;
import model.Player;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Alex on 27.11.2016.
 */
public class SplitMsg extends Message {
    @NotNull
    private final int playerId;


    public SplitMsg(@NotNull int playerId) {
        super(ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress(), ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.playerId = playerId;
    }


    @Override
    public void exec(Abonent abonent) {
        List<GameSession> activeGameSessions =  ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions();
        for (GameSession session: activeGameSessions){
            List<Player> players = session.getPlayers();
            for (Player player: players){
                if (player.getId() == playerId){
                    player.split();
                    return;
                }
            }
        }
    }
}
