package quicky.creativelyblessed.com.quickthinking;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements FragmentSettings.ShareListener, FragmentSettings.DialogListener{

    static MediaPlayer mediaPlayer,mediaPlayer2;
    static Typeface font,font2;
   android.support.v4.app.FragmentManager manager;
    int count=0;


    public void shareApp(View view){
        share();
    }

    @Override
    public void share() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Think Quick");
            String msg = "\nLet me recommend you this application\n\n";
            msg = msg + "link \n\n";
            i.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(i,"Share via"));
        } catch(Exception e) {
            Toast.makeText(this,"Cannot share at this time.Please try again later",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void openDialog() {

        manager = getSupportFragmentManager();
        Dialog dialog=new Dialog();
        dialog.show(manager, "Dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setNotification();

        Button txt = (Button) findViewById(R.id.Math);
        Button txt2=(Button)findViewById(R.id.MyShareButton);
        Button txt3=(Button)findViewById(R.id.Settings);
        Button txt5=(Button)findViewById(R.id.MyPlayButton);
        TextView txt4=(TextView)findViewById(R.id.logo);
        font = Typeface.createFromAsset(getAssets(), "erwig.ttf");
        font2=Typeface.createFromAsset(getAssets(),"blk.TTF");
        txt.setTypeface(font);
        txt2.setTypeface(font);
        txt3.setTypeface(font);
        txt4.setTypeface(font2);
        txt5.setTypeface(font);

        manager = getSupportFragmentManager();

        mediaPlayer= MediaPlayer.create(this,R.raw.twingy);
        mediaPlayer2=MediaPlayer.create(this,R.raw.gameover);
        float vol;
        Boolean mute = this.getSharedPreferences("FragmentSettings",MODE_PRIVATE).getBoolean("mode",false);
        if(mute)
         vol=0f;
        else
        vol = 1f;
        mediaPlayer.setVolume(vol,vol);
        mediaPlayer2.setVolume(vol,vol);
    }

    public void Settings(View view){

        if(count==0){count++;
            // add a fragment for the first time

            FragmentSettings  frag1=new FragmentSettings();
            frag1.setShareListener(this);
            frag1.setDialogListener(this);
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.group,frag1,"A");
            transaction.commit();


        }else{

            //check if fragment is visible is no then attach a fragment else detach a fragment
            android.support.v4.app.Fragment frg = manager.findFragmentByTag("A");

            if(frg.isVisible()){
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                transaction.detach(frg);
               transaction.commit();
            }else{
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                transaction.attach(frg);
                transaction.commit();
            }

        }

    }

    public void setNotification(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,19);
        calendar.set(Calendar.MINUTE,25);
        calendar.set(Calendar.SECOND,30);


        Intent intent=new Intent(getApplicationContext(),NotificationR.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent);
    }
    public void playMath(View view){

        Intent i=new Intent(MainActivity.this,MathActivity.class);
        startActivity(i);

    }

    public void playEnglish(View view){

        Intent i=new Intent(MainActivity.this,PlayActivity.class);
        startActivity(i);

    }


}
