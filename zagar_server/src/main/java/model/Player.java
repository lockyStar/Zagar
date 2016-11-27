package model;

import main.ApplicationContext;
import network.ClientConnectionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class Player {
  private final static @NotNull Logger log = LogManager.getLogger(ClientConnectionHandler.class);

  public static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id;
  @NotNull
  private String name;
  @NotNull
  private final List<PlayerCell> cells = new ArrayList<>();

  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
  }

  public void addCell(@NotNull PlayerCell cell) {
    cells.add(cell);
  }

  public void removeCell(@NotNull PlayerCell cell) {
    cells.remove(cell);
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public List<PlayerCell> getCells() {
    return cells;
  }

  public int getId() {
    return id;
  }

  public void move(float dx, float dy){
  log.info(this + " was moved on " + dx + " " + dy);
  }

  public void ejectMass(){
    log.info(this + " ejected mass");
  }

  public void split(){
    log.info(this + " was splited");
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
