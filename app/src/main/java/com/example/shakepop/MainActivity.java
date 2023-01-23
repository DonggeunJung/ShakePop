package com.example.shakepop;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements JGameLib.GameEvent {
    JGameLib gameLib = null;
    JGameLib.Card gameBackground;
    JGameLib.Card cardBottle;
    JGameLib.Card cardSoda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLib = findViewById(R.id.gameLib);
        initGame();
    }

    private void initGame() {
        gameLib.setScreenGrid(100, 160);
        gameLib.listener(this);
        gameBackground = gameLib.addCardColor(Color.rgb(0,0,0));
        cardSoda = gameLib.addCardColor(Color.rgb(255,128,0), 0, 140, 100, 20);
        cardBottle = gameLib.addCard(R.drawable.bottle_inverse);
    }

    // User Event start ====================================

    public void onBtnStart(View v) {
        cardSoda.screenRect(0, 140, 100, 20);
        gameLib.startSensorAccelerometer();
    }

    // User Event end ====================================

    // Game Event start ====================================

    @Override
    public void onGameWorkEnded(JGameLib.Card card, JGameLib.WorkType workType) {}

    @Override
    public void onGameTouchEvent(JGameLib.Card card, int action, float blockX, float blockY) {}

    @Override
    public void onGameSensor(int sensorType, float x, float y, float z) {
        if(sensorType == Sensor.TYPE_ACCELEROMETER) {
            float v1 = Math.abs(x), v2 = Math.abs(y), cut = 12, rate = 0.1f;
            float v = Math.max(v1,v2);
            if(v > cut) {
                v = (v - cut) * rate;
                cardSoda.screenRectGap(0, -v, 0, v);
                if(cardSoda.screenRect().top < 10) {
                    gameLib.stopSensorAccelerometer();
                    gameLib.vibrate(0.5);
                    gameLib.popupDialog(null, "Conguratulate! You pop the bottle.", "Close");
                }
            }
        }
    }

    @Override
    public void onGameCollision(JGameLib.Card card1, JGameLib.Card card2) {}

    @Override
    public void onGameTimer(int what) {}

    // Game Event end ====================================

}