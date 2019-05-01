package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.view.KeyEvent;


public class ParametersActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_parameters);
        initControls();
    }

    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SeekBar mediaVlmSeekBar = (SeekBar) findViewById(R.id.seekBar);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {

            int index = mediaVlmSeekBar.getProgress();
            mediaVlmSeekBar.setProgress(index + 1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            int index = mediaVlmSeekBar.getProgress();
            mediaVlmSeekBar.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
