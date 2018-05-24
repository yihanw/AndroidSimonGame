package cs349.uwaterloo.ca.mvc2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer
{
    // Private Vars
    Model mModel;
    TextView name;
    TextView circleNumText;
    SeekBar circleNum;
    TextView levelText;
    Spinner level;
    Button goBack;
    Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_main);

        // Get Model instance
        mModel = Model.getInstance();
        mModel.addObserver(this);

        // name textfield
        name = (TextView) findViewById(R.id.view1_name);
        //name.setText("setting");

        // circle number seekbar
        circleNumText = (TextView) findViewById(R.id.view1_circleNumText);
        //circleNumText.setText("number of circles: "+mModel.getCircleNum());

        circleNum = (SeekBar) findViewById(R.id.view1_circleNum);
        circleNum.setProgress(mModel.getCircleNum()-1);
        circleNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;

                circleNumText.setText("number of circles: " + (progress+1));
                mModel.setCircleNum(progress+1);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               // circleNumText.setText("number of circles: " + mModel.getCircleNum());

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
                circleNumText.setText("number of circles: " + (progress+1));
                mModel.setCircleNum(progress+1);

            }
        });

        // level
        levelText = (TextView) findViewById(R.id.view1_levelText);
        levelText.setText("level of difficulty: "+mModel.getLevel());

        level = (Spinner) findViewById(R.id.view1_levelSpinner);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Easy");
        categories.add("Normal");
        categories.add("Hard");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        level.setAdapter(dataAdapter);
        level.setSelection(mModel.getLevelSelection());
        //Log.d("DEMO", "level: "+mModel.getLevelSelection());
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = level.getItemAtPosition(position).toString();
                mModel.setLevel(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // goBack button
        goBack = (Button)findViewById(R.id.view1_goBackButton);
        //goBack.setText("go back to home");
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WelcomeScreen.class));
            }
        });

        // startGame button
        startGame = (Button)findViewById(R.id.view1_startGameButton);
        //goBack.setText("go back to home");
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        // Init observers
        mModel.initObservers();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mModel.deleteObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view1, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle interaction based on item selection
        switch (item.getItemId())
        {
            case R.id.setting_goToHome:
                // Create Intent to launch second activity
                Intent intent = new Intent(this, WelcomeScreen.class);

                // Start activity
                startActivity(intent);
                finish();
                return true;
            case R.id.setting_goToGame:
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
