package quicky.creativelyblessed.com.quickthinking;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class Dialog extends android.support.v4.app.DialogFragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.custom_dialog,container,false);
        Button ohk= (Button) view.findViewById(R.id.ohk);
        ohk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
