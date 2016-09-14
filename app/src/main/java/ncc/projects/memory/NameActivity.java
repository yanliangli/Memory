package ncc.projects.memory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NameActivity extends ActionBarActivity implements View.OnClickListener{
    private Button btn;
    private String nameInput;
    private EditText apple1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        btn = (Button) findViewById( R.id.done);
        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        apple1 = (EditText) findViewById (R.id.name);
        nameInput = apple1.getText().toString();
        // Retrieves name from EditText

        Bundle b = new Bundle();
        b.putString("name" , nameInput);
        // Name inserted into Bundle

        Intent intent = new Intent(this , MainActivity.class);
        // Creates intent

        intent.putExtras( b );
        // Puts bundle in the intent

        startActivity(intent);
        // Starts activity defined by "intent"
    }
}
