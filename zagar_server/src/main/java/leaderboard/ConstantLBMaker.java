package leaderboard;

import java.io.IOException;
import java.util.Map;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.eclipse.jetty.websocket.api.Session;
/**
 * Created by pashe on 28-Nov-16.
 */
public class ConstantLBMaker implements LdrBrdMaker {
    @Override
    public void makeLB() throws IOException {
        String[] leaderboard = new String[10];
        leaderboard[0] = "SashaNumberOne --- 1337";

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketLeaderBoard(leaderboard).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
