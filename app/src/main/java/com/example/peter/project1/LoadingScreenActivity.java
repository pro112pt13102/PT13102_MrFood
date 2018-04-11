package com.example.peter.project1;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingScreenActivity extends AppCompatActivity {
    ImageView img_logo;
    TextView txt_app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Anhxa();
        //Animation
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // Scale size logo
                ScaleView(img_logo, (float) 2.2);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Change margin top of logo
//                    changeMarginWithanimation();
                changeMarginTopWithanimation1(img_logo,-40);
            }
        },800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                changeMarginWithanimation();
                ChangeAnphaAnimation(txt_app_name,1);
//                changeMarginTopWithanimation();
                changeMarginTopWithanimation1(txt_app_name, 95);
            }
        },1000);
        //  nếu như đăng nhập rồi thì chuyển hản đến trang chủ
        // chưa đăng nhập thì hiển thị màn hình danng98 nhập
        Thread trangchu = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    // dừng 1.5 giây để cho các animation chạy xong
                    // rồi đến trangchuaActivity
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(LoadingScreenActivity.this,
                            TrangChuActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        trangchu.start();

    }

    public void Anhxa(){
        img_logo=findViewById(R.id.img_logo);
        txt_app_name=findViewById(R.id.txt_app_name);
    }
    public void changeMarginTopWithanimation1(final View v, int differentMargin) {
        final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, params.topMargin+differentMargin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                v.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }
    public  void ChangeAnphaAnimation(final View view, float value){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,value);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }
    public void ScaleView(final View view, float value){
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", value);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", value);
        scaleDownX.setDuration(500);
        scaleDownY.setDuration(500);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDownX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                View p = (View) view.getParent();
                p.invalidate();
            }
        });
        scaleDown.start();

    }
}
