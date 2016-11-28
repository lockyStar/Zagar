package leaderboard;

import java.io.*;
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
        try (InputStream in = new FileInputStream(new File("src/main/resources/leaderboardinput.txt"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            int i = 0;
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                leaderboard[i] = line;
                System.out.println(leaderboard[i]);
                i++;
            }

        }


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
