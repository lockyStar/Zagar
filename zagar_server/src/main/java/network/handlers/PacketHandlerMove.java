package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import messageSystem.messages.ReplicateMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.util.Map;

public class PacketHandlerMove {
  public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
    CommandMove commandMove;
    try {
      commandMove = JSONHelper.fromJSON(json, CommandMove.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    //Need to be refactored
    int playerId = -1;

    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(connection.getValue().equals(session)){
        playerId = connection.getKey().getId();
        break;
      }
    }
    if (playerId == -1){
      return;
    }
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new MoveMsg(commandMove.getDx(), commandMove.getDy(), playerId);
    messageSystem.sendMessage(message);

  }
}
