package com.belly;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Slide extends Activity {

ViewFlipper flipper;

public Animation inFromRightAnimation() {

Animation inFromRight = new TranslateAnimation(
Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
);
inFromRight.setDuration(500);
inFromRight.setInterpolator(new AccelerateInterpolator());
return inFromRight;
}
public Animation outToLeftAnimation() {
Animation outtoLeft = new TranslateAnimation(
  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
);
outtoLeft.setDuration(500);
outtoLeft.setInterpolator(new AccelerateInterpolator());
return outtoLeft;
}

public Animation inFromLeftAnimation() {
Animation inFromLeft = new TranslateAnimation(
Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
);
inFromLeft.setDuration(500);
inFromLeft.setInterpolator(new AccelerateInterpolator());
return inFromLeft;
}
public Animation outToRightAnimation() {
Animation outtoRight = new TranslateAnimation(
  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
);
outtoRight.setDuration(500);
outtoRight.setInterpolator(new AccelerateInterpolator());
return outtoRight;
}


}