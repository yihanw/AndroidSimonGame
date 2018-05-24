package cs349.uwaterloo.ca.mvc2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SecondActivity extends AppCompatActivity implements Observer
{
    // Private Vars
    Model mModel;
    TextView score;
    TextView msg;
    Button home;
    List<Button> buttonList;
    ToggleButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(String.valueOf(R.string.DEBUG_MVC_ID), "SecondActivity: OnCreate");
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_second);

        // Get Model instance
        mModel = Model.getInstance();
        mModel.addObserver(this);

        // score and msg
        score = (TextView) findViewById(R.id.view2_score);
        score.setText("Score: 0");

        msg = (TextView) findViewById(R.id.view2_msg);
        msg.setText("press any circle to start");

        // home button
        home = (Button)findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.reset();
                startActivity(new Intent(SecondActivity.this, WelcomeScreen.class));
            }
        });

        // buttons
        buttonList = new ArrayList<Button>();
        final Button button0 = (Button) findViewById(R.id.button0);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);
        buttonList.add(button0);
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        //animation.setBackgroundColor(Color.rgb(255, 128, 128));
        animation.setDuration(mModel.getSpeed()); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

       // Log.d("State is ", ""+ mModel.getState());

       // if (mModel.getState() == State.HUMAN) {
            button0.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mModel.getState() == State.HUMAN) {
                        button0.startAnimation(animation);
                        mModel.verifyButton(0);
                    }
                    //Log.d("circle"," 0, state: "+mModel.getState());
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mModel.getState() == State.HUMAN) {
                        button1.startAnimation(animation);
                        mModel.verifyButton(1);
                        //Log.d("TEST", " 1");
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mModel.getState() == State.HUMAN) {
                        button2.startAnimation(animation);
                        mModel.verifyButton(2);
                    }
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mModel.getState() == State.HUMAN) {
                        button3.startAnimation(animation);
                        mModel.verifyButton(3);
                        //Log.d("TEST", " 3");
                    }
                }
            });
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                        if (mModel.getState() == State.HUMAN) {
                        button4.startAnimation(animation);
                            mModel.verifyButton(4);
                       // Log.d("TEST", " 4");
                    }
                }
            });
            button5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mModel.getState() == State.HUMAN) {
                        button5.startAnimation(animation);
                        mModel.verifyButton(5);
                        //Log.d("TEST", " 5");
                    }
                }
            });
    //    }

        // start toggle button
        startButton = (ToggleButton) findViewById(R.id.startButton);
        startButton.setText("Start");
        startButton.setTextOff("Start");
        startButton.setTextOn("In game");
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (startButton.isChecked()) { //in game, just clicked
                    mModel.newRound();
                }
            }
        });

        // Init observers
        mModel.initObservers();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
       // Log.d(String.valueOf(R.string.DEBUG_MVC_ID), "MainActivity: onDestory");

        // Remove observer when activity is destroyed.
        mModel.deleteObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle interaction based on item selection
        switch (item.getItemId())
        {
            case R.id.game_GoToHome:
                // Create Intent to launch second activity
                mModel.reset();
                Intent intent = new Intent(this, WelcomeScreen.class);

                // Start activity
                startActivity(intent);
                finish();
                return true;
            case R.id.game_GoToSetting:
                // Create Intent to launch second activity
                mModel.reset();
                Intent intent2 = new Intent(this, MainActivity.class);

                startActivity(intent2);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        for(int i = mModel.getCircleNum(); i < 6; i++){
            buttonList.get(i).setVisibility(View.INVISIBLE);
        }

        // toggle start button
        if (mModel.getState()==State.COMPUTER || mModel.getState()==State.HUMAN){
            startButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
            startButton.setChecked(false);
            //startButton.setText("Start");
        }

        // flash
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(mModel.getSpeed()); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //buttonList.get(mModel.getButtonIndex(mModel.getIndex())).setHighlightColor(Color.rgb(255, 128, 128));
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationEnd(Animation animation) {

                Log.d("FLASH", " index: "+ mModel.getIndex());
                if (mModel.getIndex() < mModel.getLength()) {
                    //Log.d("Demo:", "index: " + mModel.getIndex());
                    buttonList.get(mModel.getButtonIndex(mModel.getIndex())).startAnimation(animation);

                    if (mModel.getButtonIndex(mModel.getIndex()) != mModel.getButtonIndex((mModel.getIndex()-1))) {
                        //Log.d("test:", " "+ mModel.getIndex());
                        buttonList.get(mModel.getButtonIndex(mModel.getIndex() - 1)).clearAnimation();
                    }
                    mModel.increaseIndex();
                } else {
                    mModel.setState(State.HUMAN);
                    mModel.setIndex(0);
                    msg.setText("Your turn ...");

                }
            }
        });

        //home.setVisibility(View.INVISIBLE);
        score.setText("Score: "+mModel.getScore());
        switch(mModel.getState()){
            case START:
                msg.setText("Press Start button to start");
                //home.setVisibility(View.VISIBLE);
                break;
            case COMPUTER:
                msg.setText("Watch what I do ...");
                Log.d("index: ", ""+mModel.getIndex());
                if (mModel.getIndex() == 0) {
                    buttonList.get(mModel.getButtonIndex(0)).startAnimation(animation);
                    mModel.increaseIndex();
                }
                break;
            case HUMAN:
                msg.setText("Your turn ...");
                break;
            case LOSE:
                msg.setText("You lose. Press Start to continue.");
               // home.setVisibility(View.VISIBLE);
                break;
            case WIN:
                msg.setText("You won! Press Start to continue.");
                break;
            default:
                break;
        }

    }
}