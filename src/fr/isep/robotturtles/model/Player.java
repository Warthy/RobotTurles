package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.*;
import java.util.*;

public class Player extends Pawn implements Comparable {
    public static final int PLAYER_START_ROW = 7;

    private Card[] deck = new Card[5];
    private LinkedList<Card> stack = new LinkedList<>();
    private List<Card> trash = new ArrayList<>();
    private int jewelPoint = 0;

    private Obstacle[] obstacleDeck = new Obstacle[5];
    private Boolean hasUsedBug = false;
    private List<Card> instructionsList = new LinkedList<>();
    private PlayerColor color;
    private Orientation orientation;

    // We only keep col coordinate as row is constant (PLAYER_START_ROW)
    private int startCoordinate;


    public Player(PlayerColor color, int startCoordinate){
        this.color = color;
        this.startCoordinate = startCoordinate;
        setCoordinates(PLAYER_START_ROW, startCoordinate);


        orientation = Orientation.UP;
        stack.addAll(Collections.nCopies(18, new Card(CardType.BLUE)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.YELLOW)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.PURPLE)));
        stack.addAll(Collections.nCopies(3, new Card(CardType.LASER)));
        Collections.shuffle(stack);

        Arrays.fill(obstacleDeck, 0, 3, new Obstacle(ObstacleType.STONE));
        Arrays.fill(obstacleDeck, 3, 5, new Obstacle(ObstacleType.ICE));

        for(int i =0; i<5;i++){
            deck[i] = stack.remove();
        }
    }

    public void draw(){
        for(int i = 0; i<deck.length; i++){
            if(deck[i] == null){
                if(stack.size() == 0) resetStack();
               deck[i] = stack.remove();
            }
        }
    }

    private void resetStack(){
        stack.addAll(trash);
        Collections.shuffle(trash);
    }

    public void returnStartPosition(){
        orientation = Orientation.UP;
        coordinates[0] = PLAYER_START_ROW;
        coordinates[1] = startCoordinate;
    }

    public Card[] getDeck() {
        return deck;
    }

    public List<Card> getStack() {
        return stack;
    }

    public List<Card> getTrash() {
        return trash;
    }

    public Obstacle[] getObstacleDeck() {
        return obstacleDeck;
    }

    public void setObstacleDeck(Obstacle[] obstacleDeck) {
        this.obstacleDeck = obstacleDeck;
    }

    public void removeFromObstacleDeck(int index){
        this.obstacleDeck[index] = null;
    }

    public Boolean getHasUsedBug() {
        return hasUsedBug;
    }

    public void setHasUsedBug(Boolean hasUsedBug) {
        this.hasUsedBug = hasUsedBug;
    }

    public List<Card> getInstructionsList() {
        return instructionsList;
    }

    public void emptyInstructions(){
        trash.addAll(instructionsList);
        instructionsList.clear();
    }

    public void discardCard(int cardIndex){
        trash.add( deck[cardIndex]);
        deck[cardIndex] = null;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getStartCoordinate() {
        return startCoordinate;
    }

    public int getJewelPoint() {
        return jewelPoint;
    }

    public void setJewelPoint(int jewelPoint) {
        this.jewelPoint = jewelPoint;
    }

    @Override
    public PawnType getPawnType(){
        return PawnType.PLAYER;
    }

    @Override
    public int compareTo(Object o) {
        return this.getJewelPoint() > ((Player) o).getJewelPoint() ? -1: 1;
    }
}
