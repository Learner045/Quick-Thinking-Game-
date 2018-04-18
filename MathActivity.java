package quicky.creativelyblessed.com.quickthinking;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MathActivity extends AppCompatActivity {

    int ansList[]=new int[4];
    String operator[]=new String[]{"+","-","*","/"};
    TextView question,scoreText;
    Button button0,button1,button2,button3;
    int correctLocation;
    int score=0;
    SeekBar seekbar;
    CountDownTimer countDownTimer;
    RelativeLayout relativeLayout;


    @Override
    public void onBackPressed(){}
    public void countDownBegins(final long time){

        GenerateQuestion();

        countDownTimer=new CountDownTimer(time,1000) {
            @Override
            public void onTick(long l) {

                seekbar.setProgress((int)l/1000);

            }

            @Override
            public void onFinish() {

                MainActivity.mediaPlayer2.start();
                seekbar.setVisibility(View.INVISIBLE);
                showResult();
                //a beep sound
            }
        }.start();
    }

    public void showResult(){

        Intent i=new Intent(MathActivity.this,ResultActivity.class);
        i.putExtra("score",score);
        startActivity(i);
        finish();
    }
    public void chooseAns(View view)
    {

        if(view.getTag().equals(String.valueOf(correctLocation))){

            //correct ans chosen
            MainActivity.mediaPlayer.start();
            score++;


            if(score<9){
                scoreText.setText(getResources().getString(R.string.score, score));
            }
            // scoreText.setText("0"+String.valueOf(score)); //09
            else
                scoreText.setText(String.valueOf(score));

            countDownTimer.cancel();
            countDownBegins(2000);


        }else{

            //incorrect chosen

            MainActivity.mediaPlayer2.start();
            countDownTimer.cancel();
            showResult();

        }


    }

    public void GenerateQuestion()
    {
        Random rand=new Random();

        int a,b,op;
        int ans=0;
        a=rand.nextInt(25);
        b=rand.nextInt(25)+1;
        op=rand.nextInt(4);
        String operateOn=operator[op];
        switch(operateOn){

            case "*":question.setText(a+operateOn+b);ans=a*b;
                break;
            case "+":question.setText(a+operateOn+b);ans=a+b;
                break;
            case "/":question.setText(a+operateOn+b);ans=a/b;
                break;
            case "-":question.setText(a+operateOn+b);ans=a-b;
                break;

        }



        correctLocation=rand.nextInt(4);

        for(int i=0;i<4;i++){

            if(correctLocation==i){
                ansList[i]=ans;
            }
            else
            {   //ans = Math.abs(ans);
                int incorrectans=rand.nextInt(ans+15);
                while(incorrectans==ans){
                    incorrectans=rand.nextInt(ans+15);
                }
                ansList[i]=incorrectans;

            }

        }

        button0.setText(String.valueOf(ansList[0]));
        button1.setText(String.valueOf(ansList[1]));
        button2.setText(String.valueOf(ansList[2]));
        button3.setText(String.valueOf(ansList[3]));


    }

    private void Animat(){

        relativeLayout.animate().translationYBy(-2000f).setDuration(1000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        question=(TextView)findViewById(R.id.questionText);
        button0=(Button)findViewById(R.id.button0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        seekbar.setProgress(3);
        seekbar.setMax(3);
        scoreText=(TextView)findViewById(R.id.scoreText);
        relativeLayout=(RelativeLayout)findViewById(R.id.activity_math);
        relativeLayout.setTranslationY(2000f);
        Animat();




        countDownBegins(4000);


    }
}
