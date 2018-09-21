package site.ufsj.notificacoes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg = (EditText) findViewById(R.id.editText);
    }
    public void onClick2(View v) {
                /*Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                intent.putExtra("mensagem", msg.getText().toString());
                int id = (int) (Math.random()*1000);
                PendingIntent pi = PendingIntent.getActivity(getBaseContext(), id,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new Notification.Builder(getBaseContext())
                        .setContentTitle("De: Andrés Menéndez" )
                        .setContentText(msg.getText()).setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pi).build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(id, notification);*/
        createNotification("Teste", this);
    }


    private NotificationManager notifManager;
    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.putExtra("mensagem", msg.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),NOTIFY_ID ,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String id = context.getString(R.string.app_name); // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }


    public void onClick(View v) {
        EditText segundos = (EditText) findViewById(R.id.editText);

        ComponentName serviceName = new ComponentName(getBaseContext(),
                JobAlarme.class);
        JobInfo jobInfo = new JobInfo.Builder(0, serviceName)
                .setMinimumLatency(Integer.parseInt(segundos.getText().toString())*1000)
                .setRequiresCharging(false)
                .build();
        JobScheduler scheduler = (JobScheduler) getBaseContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS)
            Log.d("MainActivity", "Serviço agendado!");
    }
}
