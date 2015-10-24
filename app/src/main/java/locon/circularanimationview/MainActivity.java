package locon.circularanimationview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CircularView circle;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle = (CircularView) findViewById(R.id.circle_animation);
        circle.setFillAngle(0);
        circle.postDelayed(new Runnable() {
            int fillAngle = 0;

            //20 times this is called, one second gets over
            // did this coz this is smoother
            @Override
            public void run() {
                fillAngle = (fillAngle + 3) % 360;
                Log.d("Som", "" + fillAngle);
                circle.setFillAngle(fillAngle);
                circle.postDelayed(this, 50);
                incrementCount();
            }
        }, 50);
    }

    private void incrementCount() {
        count++;
        Log.d("Som", "count " + count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
