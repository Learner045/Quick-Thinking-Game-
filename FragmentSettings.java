package quicky.creativelyblessed.com.quickthinking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static android.content.Context.MODE_PRIVATE;


public class FragmentSettings extends Fragment implements View.OnClickListener {

   ShareListener shareListener;
    DialogListener dialogListener;
    ImageView muteIcon,shareIcon,infoIcon;
    public FragmentSettings() {
        // Required empty public constructor
    }

    public void setShareListener(ShareListener listener){
        shareListener=listener;
    }
    public void setDialogListener(DialogListener listener){
        dialogListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Boolean mute = getActivity().getSharedPreferences("FragmentSettings",MODE_PRIVATE).getBoolean("mode",false);
        muteIcon=(ImageView)view.findViewById(R.id.sound);
        shareIcon=(ImageView)view.findViewById(R.id.shareApp);
        infoIcon=(ImageView)view.findViewById(R.id.Info);

        if(!mute)muteIcon.setImageResource(R.drawable.music);
        else muteIcon.setImageResource(R.drawable.mute);

        muteIcon.setOnClickListener(this);
        shareIcon.setOnClickListener(this);
        infoIcon.setOnClickListener(this);

        return view;
    }


    private boolean setMuteModeOn(){

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("FragmentSettings",MODE_PRIVATE);
        //if mute mode is off - set it on

        if(!sharedPreferences.getBoolean("mode",false)) {
            sharedPreferences.edit().putBoolean("mode", true).apply();
            return true;
        }else{
            sharedPreferences.edit().putBoolean("mode", false).apply();
            return false;
        }

    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.sound) {
            if (setMuteModeOn()) {

                MainActivity.mediaPlayer.setVolume(0f,0f);
                MainActivity.mediaPlayer2.setVolume(0,0f);
                muteIcon.setImageResource(R.drawable.mute);

            } else {


                MainActivity.mediaPlayer.setVolume(1f,1f);
                MainActivity.mediaPlayer2.setVolume(1f,1f);
                muteIcon.setImageResource(R.drawable.music);


            }

        }else if(view.getId()==R.id.shareApp){

            shareListener.share();

            //share app
        }else if(view.getId()==R.id.Info){

            dialogListener.openDialog();
        }
    }


    public interface ShareListener{
        void share();
    }

    public interface DialogListener{
        void openDialog();
    }
}
