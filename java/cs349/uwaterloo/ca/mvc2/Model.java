package cs349.uwaterloo.ca.mvc2;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

enum State { START, COMPUTER, HUMAN, LOSE, WIN };

class Model extends Observable
{
    // Create static instance of this mModel
    private static final Model ourInstance = new Model();
    static Model getInstance()
    {
        return ourInstance;
    }

    // Private Variables
    private int circleNum;
    private String level;

    private State state;
    private int score;
    private String msg;
    private int length;
    List<Integer> sequence;
    int index;

    /**
     * Model Constructor:
     * - Init member variables
     */
    Model() {
        circleNum = 4;
        level = "Normal";
        length = 1;
        state = State.START;
        score = 0;
        msg = "";
        sequence = new ArrayList<Integer>();
        index = 0;
    }

    // setting: circle number
    public int getCircleNum(){
        return circleNum;
    }

    public void setCircleNum(int n){
        circleNum = n;
        setChanged();
        notifyObservers();
    }

    // setting: level of difficulty
    public String getLevel(){
        return level;
    }

    public int getSpeed(){
        if (level == "Easy"){
            return 700;
        } else if (level == "Normal"){
            return 500;
        } else if (level == "Hard"){
            return 300;
        } else {
            return 500;
        }
    }

    public void setLevel(String s){
        level = s;
        setChanged();
        notifyObservers();
    }

    public int getLevelSelection() {
        switch(level){
            case "Easy":
                return 0;
            case "Normal":
                return 1;
            case "Hard":
                return 2;
            default:
                return 1;
        }

    }

    // game
    public State getState(){
        return state;
    }

    public void setState(State s){
        state = s;
    }

    public int getLength(){
        return length;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int i){
        index = i;
    }

    public void increaseIndex(){
        index++;
    }

    public int getButtonIndex(int i){
        return sequence.get(i);
    }

    public int getScore(){
        return score;
    }

    public void reset(){
        circleNum = 4;
        level = "Normal";
        length = 1;
        state = State.START;
        score = 0;
        msg = "";
        sequence.clear();
        index = 0;
        setChanged();
        notifyObservers();
    }

    public void newRound(){
        if (state == State.LOSE) {
            length = 1;
            score = 0;
           // circleNum = 4;
          //  level = "Normal";
        }
        sequence.clear();

        for(int i=0; i<length; i++){
            int b = 0 + (int)(Math.random() * ((circleNum-1 - 0) + 1));
            Log.d("DEMO","b = "+b);
            sequence.add(b);
        }

        index = 0;
        state = State.COMPUTER;

        setChanged();
        notifyObservers();
    }

    public int nextButton() {

        if (state != State.COMPUTER) {
            return -1;
        }

        // this is the next button to show in the sequence
        int button = sequence.get(index);
        index++;

        // if all the buttons were shown, give
        // the human a chance to guess the sequence
        if (index >= sequence.size()) {
            index = 0;
            state = State.HUMAN;
        }

        setChanged();
        notifyObservers();

        return button;
    }

    public boolean verifyButton(int button) {

        if (state != State.HUMAN) {
            return false;
        }

        // did they press the right button?
        boolean correct = (button == sequence.get(index));
        index++;

        // pushed the wrong buttons
        if (!correct) {
            state = State.LOSE;

            // they got it right
        } else {
            // if last button, then the win the round
            if (index == sequence.size()) {
                state = State.WIN;
                // update the score and increase the difficulty
                score++;
                length++;
            }
        }

        setChanged();
        notifyObservers();

        return correct;
    }

    public void initObservers()
    {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer o)
    {
        super.deleteObserver(o);
    }

    @Override
    public synchronized void addObserver(Observer o)
    {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObservers()
    {
        super.deleteObservers();
    }

    @Override
    public void notifyObservers()
    {
        super.notifyObservers();
    }
}
