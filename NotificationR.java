package quicky.creativelyblessed.com.quickthinking;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationR extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingintent=new Intent(context,MainActivity.class);
        repeatingintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeatingintent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Quicky Thinking")
                .setContentText("Time to Exercise your brain!!!Go for it")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());

    }
}
