package com.developermarshall.androidlevel;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private LevelBubble levelBubble;
    private Level level;

    /**
     * Stop the level
     */
    @Override
    public void onPause() {
        super.onPause();
        level.stop();
    }

    /**
     * Start the level
     */
    @Override
    public void onResume() {
        super.onResume();
        level.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelBubble = findViewById(R.id.level_bubble);
        level = new Level(this, levelBubble);
    }
}
