package com.example.shakepop;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Mosaic.GameEvent {
    Mosaic mosaic = null;
    Mosaic.Card gameBackground;
    Mosaic.Card cardBottle;
    Mosaic.Card cardSoda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mosaic = findViewById(R.id.mosaic);
        initGame();
    }

    private void initGame() {
        mosaic.setScreenGrid(100, 160);
        mosaic.listener(this);
        gameBackground = mosaic.addCardColor(Color.rgb(0,0,0));
        cardSoda = mosaic.addCardColor(Color.rgb(255,128,0), 0, 140, 100, 20);
        cardBottle = mosaic.addCard(R.drawable.bottle_inverse);
    }

    // User Event start ====================================

    public void onBtnStart(View v) {
        cardSoda.screenRect(0, 140, 100, 20);
        mosaic.startSensorAccelerometer();
    }

    // User Event end ====================================

    // Game Event start ====================================

    @Override
    public void onGameWorkEnded(Mosaic.Card card, Mosaic.WorkType workType) {}

    @Override
    public void onGameTouchEvent(Mosaic.Card card, int action, float x, float y) {}

    @Override
    public void onGameSensor(int sensorType, float x, float y, float z) {
        if(sensorType == Sensor.TYPE_ACCELEROMETER) {
            float v1 = Math.abs(x), v2 = Math.abs(y), cut = 12, rate = 0.1f;
            float v = Math.max(v1,v2);
            if(v > cut) {
                v = (v - cut) * rate;
                cardSoda.screenRectGap(0, -v, 0, v);
                if(cardSoda.screenRect().top < 10) {
                    mosaic.stopSensorAccelerometer();
                    mosaic.vibrate(0.5);
                    mosaic.popupDialog(null, "Conguratulate! You pop the bottle.", "Close");
                }
            }
        }
    }

    @Override
    public void onGameCollision(Mosaic.Card card1, Mosaic.Card card2) {}

    @Override
    public void onGameTimer() {}

    // Game Event end ====================================

}