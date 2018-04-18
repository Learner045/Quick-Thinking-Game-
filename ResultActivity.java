package quicky.creativelyblessed.com.quickthinking;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {
    int score;
    TextView bestscoreText;
    SharedPreferences sharedPreferences;
    RelativeLayout relativeLayout;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==2){

            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                if(ContextCompat.checkSelfPermission(ResultActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    Bitmap bitmap = takeScreenshot();
                    saveBitmap(bitmap);
                    shareIt();
                }
            }else{

                Toast.makeText(ResultActivity.this,"Please give permission first",Toast.LENGTH_LONG).show();
            }
        }

    }


    File imagePath;


    public void check(){

        //we check if we already have permission

        if(ContextCompat.checkSelfPermission(ResultActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ResultActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }else{

            Bitmap bitmap = takeScreenshot();
            saveBitmap(bitmap);
            shareIt();

        }

    }

    public void getScreenShot(View view)
    {
        if(Build.VERSION.SDK_INT<23){
            Bitmap bitmap = takeScreenshot();
            saveBitmap(bitmap);
            shareIt();
        }else {
            check();

        }


    }

    public Bitmap takeScreenshot() {
        View rootView =  getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        return bitmap;

    }


    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.getFD().sync();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(this,"Please give app permission",Toast.LENGTH_LONG).show();
        }
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/png");
        String shareBody = "In Quick Thinking, My highest score with screen shot";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Beat me if you can!!");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }catch(Exception e){

            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
    public void Home(View view){

        Intent i=new Intent(ResultActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void PlayAgain(View view){

        //play again
        Intent i=new Intent(ResultActivity.this,PlayActivity.class);
        startActivity(i);
        finish();

    }




    public void bestScore(){

        sharedPreferences=this.getSharedPreferences("ResultActivity",MODE_PRIVATE);
        if(score>sharedPreferences.getInt("bestscore",0)) {
            sharedPreferences.edit().putInt("bestscore", score).apply();
        }

        bestscoreText.setText(  String.valueOf(sharedPreferences.getInt("bestscore",0)));

    }

    private void Animat(){
        relativeLayout.animate().rotation(360).translationYBy(-2000f).setDuration(500);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView result=(TextView)findViewById(R.id.resulttext);
        TextView scoreText=(TextView)findViewById(R.id.scoreText);
        bestscoreText=(TextView)findViewById(R.id.bestScoreText);

        Button world=(Button)findViewById(R.id.Play);
        world.setTypeface(MainActivity.font);
        Button share=(Button)findViewById(R.id.Share);
        share.setTypeface(MainActivity.font);
        Button home=(Button)findViewById(R.id.Home);
        home.setTypeface(MainActivity.font);
        Intent i=getIntent();
        score=i.getIntExtra("score",0);
        scoreText.setText(String.valueOf(score));
        relativeLayout=(RelativeLayout)findViewById(R.id.activity_result);
        relativeLayout.setTranslationY(2000f);
        Animat();


        bestScore();




    }
}
