package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
/**
 * Controller class for displaying and interacting with a drawable cake using Android Studio
 *
 * @author Comingupwithnames
 * @version 1
 */
public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private CakeView controlledCakeView;
    private CakeModel controlledCakeModel;

    public CakeController(CakeView cakeView){
    controlledCakeView = cakeView;
    controlledCakeModel = cakeView.getCakeData();
    }

    @Override
    public void onClick(View view) {
        Button currButton = (Button)view;
        if(controlledCakeModel.candlesLit)
        {
            controlledCakeModel.candlesLit = false;
            currButton.setText("RELIGHT");
            controlledCakeView.invalidate();

            return;
        }
        else if(!(controlledCakeModel.candlesLit))
        {
            controlledCakeModel.candlesLit = true;
            currButton.setText("BLOWOUT");
            controlledCakeView.invalidate();
            return;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId())
        {
            case R.id.candleSwitch:
                if(isChecked)
                {
                    controlledCakeModel.hasCandles = true;
                    controlledCakeView.invalidate();
                    return;
                }
                else if(!isChecked)
                {
                    controlledCakeModel.hasCandles = false;
                    controlledCakeView.invalidate();
                    return;

                }
                break;

            case R.id.frostingSwitch:
                if(isChecked)
                {
                    controlledCakeModel.hasFrosting = true;
                    controlledCakeView.invalidate();
                    return;
                }
                else if(!isChecked)
                {
                    controlledCakeModel.hasFrosting = false;
                    controlledCakeView.invalidate();
                    return;

                }
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progressNum, boolean fromUser) {
     controlledCakeModel.numCandles = progressNum;
     controlledCakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Do nothing
    }
}
