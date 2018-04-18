package quicky.creativelyblessed.com.quickthinking;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    ImageView imageView;
    Button b0,b1;
    SeekBar seekbar;
    TextView scoreText;
    RelativeLayout relativeLayout;
    String[][]list=new String[][]{{"Dog_01","Cat_02"},{"Earth_03","Moon_04"},{"Teacher_05","Painter_06"},
            {"Flower_07","Garden_08"},{"Bug_09","Worm_010"},{"Happy_011","Unhappy_012"},
            {"Granny_013","Grandpa_014"},{"ToothPaste_015","Teeth_016"},{"Watch_017","Compass_018"},
            {"Tractor_019","Car_020"},{"Lime_021","Orange_022"},{"Eyeball_023","Eye_024"},
            {"Flute_025","Guitar_026"},{"Boots_027","Heels_028"},{"Crane_029","Bird_030"},
            {"Elephant_031","Penguin_032"},{"Eyeglass_033","mirror_034"},{"Bat_035","Butterfly_036"},
            {"Sad_037","Shock_038"},{"Unicorn_039","Horse_040"},{"Rainy_041","Sunny_042"},{"Wifi_043","sound_044"},
            {"Superman_045","Cpt.America_046"},{"Wifi_047","Bluetooth_048"},{"Jigsaw_049","Rubik's_050"},
            {"Pen_051","Pencil_052"},{"Paint_053","Play_054"},{"Swim_055","Sleep_056"},{"Bed_057","Bedroom_058"},
            {"squirrel_059","rooster_060"},{"waiter_061","chef_062"},{"noodle_063","Poodle_064"},{"notepad_065","NoteBook_066"},
            {"Freedom_067","Peace_068"},{"witch_069","wizard_070"},{"cauldron_071","pot_072"},{"true_073","false_074"},
            {"true_075","false_076"},{"3/4_077","1/4_078"},{"true_079","false_080"},{"<_081",">_082"}
    };


    String[]answerslist;
    String rightString,wrongString;
    int score=0;int correctlocation;
    CountDownTimer countDownTimer;


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
    private void Animat(){

        relativeLayout.animate().translationYBy(2000f).setDuration(1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        imageView=(ImageView)findViewById(R.id.image);
        b0=(Button)findViewById(R.id.button0);
        b1=(Button)findViewById(R.id.button1);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        seekbar.setProgress(3);
        seekbar.setMax(3);
        scoreText=(TextView)findViewById(R.id.scoreText);
        relativeLayout=(RelativeLayout)findViewById(R.id.activity_play);
        relativeLayout.setTranslationY(-2000f);
        Animat();



        answerslist=new String[2];


        countDownBegins(3500);


    }
    public void showResult(){

        Intent i=new Intent(PlayActivity.this,ResultActivity.class);
        i.putExtra("score",score);
        startActivity(i);
        finish();
    }

    public void chooseAns(View view){

        if(view.getTag().equals(String.valueOf(correctlocation)))
        {
            //right answer selected by user
            //a beep sound
            MainActivity.mediaPlayer.start();
            score++;
            if(score<9)
                scoreText.setText(getResources().getString(R.string.score,score));
            else
                scoreText.setText(String.valueOf(score));

            countDownTimer.cancel();
            countDownBegins(2100);
        }
        else{


            MainActivity.mediaPlayer2.start();
            countDownTimer.cancel();
            showResult();
        }

    }

    public void GenerateQuestion(){


        Random rand=new Random();

        int subcode=rand.nextInt(81)+1;

        String imagename="img_0"+subcode;

        int resourceId=getResources().getIdentifier(imagename,"drawable",getPackageName());

        imageView.setImageResource(resourceId);


        for(String[] string:list)
        {
            if(string[0].contains("_0"+subcode+""))
            {
                rightString=string[0].substring(0,string[0].indexOf("_"));

                wrongString=string[1].substring(0,string[1].indexOf("_"));

                break;
            }
            if(string[1].contains("_0"+subcode+""))
            {
                rightString=string[1].substring(0,string[1].indexOf("_"));

                wrongString=string[0].substring(0,string[0].indexOf("_"));

                break;
            }

        }

        correctlocation=rand.nextInt(2);
        for(int i=0;i<2;i++)
        {
            if(i==correctlocation)
                answerslist[i]=rightString;
            else
                answerslist[i]=wrongString;
        }

        b0.setText(answerslist[0]);
        b1.setText(answerslist[1]);

    }




}
