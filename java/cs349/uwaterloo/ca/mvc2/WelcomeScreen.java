package cs349.uwaterloo.ca.mvc2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;
import android.widget.TextView;


public class WelcomeScreen extends AppCompatActivity implements Observer {
    // Private Vars
    Model mModel;
    TextView instruction;
    Button settingButton;
    Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(String.valueOf(R.string.DEBUG_MVC_ID), "MainActivity: OnCreate");
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_welcome);

        // Get Model instance
        mModel = Model.getInstance();
        mModel.addObserver(this);



        // Get TextView Reference
        instruction = (TextView) findViewById(R.id.welcome_textView);

        instruction.setText("Welcome to Simon Electronic Game. \n\n Instructions:\n\n" +
                "In the game, the computer plays first by highlighting a sequence of circles," +
                "then, you try to reproduce the sequence. \nIf you get all the circles right, " +
                "the score increases by 1 and sequence length increases by 1," +
                "otherwise, the games ends and your score goes back to 0 ");

        // setting button
        settingButton = (Button)findViewById(R.id.welcome_settingButton);
        settingButton.setText("change game setting");
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
            }
        });

        // startGame button
        settingButton = (Button)findViewById(R.id.welcome_startGameButton);
        settingButton.setText("start the game");
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, SecondActivity.class));
            }
        });

        // Init observers
        mModel.initObservers();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(String.valueOf(R.string.DEBUG_MVC_ID), "MainActivity: onDestory");

        // Remove observer when activity is destroyed.
        mModel.deleteObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcomeview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle interaction based on item selection
        switch (item.getItemId())
        {
            case R.id.welcomeMenu_GoToSetting:
                // Create Intent to launch second activity
                Intent intent = new Intent(this, MainActivity.class);

                // Start activity
                startActivity(intent);
                finish();
                return true;
            case R.id.welcomeMenu_GoToGame:
                // Create Intent to launch second activity
                Intent intent2 = new Intent(this, SecondActivity.class);

                // Start activity
                startActivity(intent2);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg)
    {
    }
}

