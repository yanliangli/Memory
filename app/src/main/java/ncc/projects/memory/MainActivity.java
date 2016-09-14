package ncc.projects.memory;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageButton[] buttons;
    private Button reset;
    private int numClicked;
    private final String TAG = new String("Memory");
    private int[] imageNums;
    private int[] buttonsClicked;
    private boolean[] matched;

    /**
     * You can assume that the instance variables, imageNums and
     * matched, are declared and given appropriate values
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = this.getIntent().getExtras();
        String userName = b.getString( "name" );

        TextView theView = (TextView) findViewById (R.id.welcome_text);
        theView.setText("Welcome " + userName + "!");
        // display welcome message

        // create the array to store references to the buttons
        buttons = new ImageButton[12];

        // get the id of the first button
        int idIndex = R.id.button0;

        // store the references into the array by cycling through the id indices & set the listener
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (ImageButton) findViewById(idIndex++);
            buttons[i].setOnClickListener(this);
        }

        //get the id's for the images and store 2 of each
        imageNums = new int[12];
        imageNums[0] = R.drawable.angry;
        imageNums[1] = R.drawable.angry;
        imageNums[2] = R.drawable.blushing;
        imageNums[3] = R.drawable.blushing;
        imageNums[4] = R.drawable.crying;
        imageNums[5] = R.drawable.crying;
        imageNums[6] = R.drawable.haha;
        imageNums[7] = R.drawable.haha;
        imageNums[8] = R.drawable.sad;
        imageNums[9] = R.drawable.sad;
        imageNums[10] = R.drawable.smile;
        imageNums[11] = R.drawable.smile;

        // randomize the values
        int r1, r2;
        int temp;
        for (int i = 0; i < 20; i++) {
            r1 = (int) (Math.random() * 12);
            r2 = (int) (Math.random() * 12);
            temp = imageNums[r1];
            imageNums[r1] = imageNums[r2];
            imageNums[r2] = temp;
        }

        //create and initialize the matched array which keeps track of
        //which buttons have been matched
        matched = new boolean[12];
        for (int i = 0; i < matched.length; i++)
            matched[i] = false;

        reset = (Button) findViewById(R.id.reset_button);
        reset.setOnClickListener(this);

        numClicked = 0;
        buttonsClicked = new int[2];
        Log.d(TAG, "onCreate");
    }

    public void onClick(View view) {
        // determine which button has been clicked
        int buttonClicked = view.getId() - R.id.button0;

        switch (view.getId()) {
            case R.id.reset_button:
                buttonClicked = -1;
                resetGame();
        }

        // make sure the reset button wasn't clicked
        if (buttonClicked != -1) {
            // show the image on the button by using the imageNums array
            buttons[buttonClicked].setImageResource(imageNums[buttonClicked]);

            // store this as a button that has been clicked
            buttonsClicked[numClicked] = buttonClicked;

            // increment the number of clicks
            numClicked++;

            // if 2 buttons are clicked, then check to see if they are a match
            if (numClicked == 2) {
                if (imageNums[buttonsClicked[0]] == imageNums[buttonsClicked[1]]) {
                    // if there is a match disable the button so it can't be clicked again
                    buttons[buttonsClicked[0]].setClickable(false);
                    buttons[buttonsClicked[1]].setClickable(false);
                    matched[buttonsClicked[0]] = true;
                    matched[buttonsClicked[1]] = true;
                } else {
                    /* When posting or sending to a Handler, you can either allow the item to be processed
				as soon as the message queue is ready to do so, or specify a delay before it gets
				processed or absolute time for it to be processed. The latter two allow you to
				implement timeouts, ticks, and other timing-based behavior.
				http://developer.android.com/reference/android/os/Handler.html */

                    new Handler().postDelayed(new Runnable() {
                        // delay setting the images back to the default so the user can see what was chosen
                        public void run() {
                            // not a match, so set the image back to the default
                            buttons[buttonsClicked[0]].setImageResource(R.drawable.defaultsmile);
                            buttons[buttonsClicked[1]].setImageResource(R.drawable.defaultsmile);
                        }
                    }, 500);
                }
                // set the numClicked back to 0 for the next pair
                numClicked = 0;

            } //end if(numClicked == 2)
        }
    } //end onClick

    private void resetGame() {
        // need code here to reset the game board
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    protected void onResume() {
        super.onResume();
		/*for (int i=0; i<matched.length; i++)
		{
			if (matched[i])
			{
				buttons[i].setImageResource(imageNums[i]);
				buttons[i].setClickable(false);
			}
		}*/
        Log.d(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ImageNums", imageNums);
        outState.putBooleanArray("Matched", matched);
        Log.d(TAG, "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        imageNums = savedState.getIntArray("ImageNums");
        matched = savedState.getBooleanArray("Matched");
        for (int i = 0; i < matched.length; i++) {
            if (matched[i]) {
                buttons[i].setImageResource(imageNums[i]);
                buttons[i].setClickable(false);
            }
        }
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.Restart){
            resetGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
