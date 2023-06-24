package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.*;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.util.EventListener;
/**
 * Initializes the buttons and views for interacting with a birthday cake
 *
 * @author Comingipwithnames
 * @version 1
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = (CakeView) findViewById(R.id.cakeview);
        CakeController cakeControlled = new CakeController(cakeView);

        Button blowOut = (Button) findViewById(R.id.candleOutButton);
        blowOut.setOnClickListener(cakeControlled);

        CompoundButton candleButton = (CompoundButton) findViewById(R.id.candleSwitch);
        candleButton.setOnCheckedChangeListener(cakeControlled);

        CompoundButton frostingButton = (CompoundButton) findViewById(R.id.frostingSwitch);
        frostingButton.setOnCheckedChangeListener(cakeControlled);

        SeekBar candleBar = (SeekBar) findViewById(R.id.candleBar);
        candleBar.setOnSeekBarChangeListener(cakeControlled);
    }

    public void goodbye(View button) {
        Log.i("exitButton","Goodbye");
    }

}
