package id.web.wazapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import id.web.wazapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //Var
    public static int SPLASH_SCR = 5000;

    Animation topAnim,botAnim;
    ImageView image;
    TextView tvLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        image = findViewById(R.id.imageView2);
        tvLogo = findViewById(R.id.textView);

        image.setAnimation(topAnim);
        tvLogo.setAnimation(botAnim);

        new Handler().postDelayed((Runnable) () -> {
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            finish();
        },SPLASH_SCR);
    }
}