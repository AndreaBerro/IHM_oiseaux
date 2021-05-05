package com.example.projetoiseaux.ui.notifications;

import android.app.Notification;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.Bird.Bird;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public class NotificationCenter extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);
    }

    public void birdRecommendation(Bird bird){
        Notification notification = new NotificationCompat.Builder(this, MainActivity.LOW_CHANNEL)
                .setSmallIcon(R.drawable.ic_thumbs_up)
                .setContentTitle("Bird recommendation")
                .setContentText("According to your recent activity, you might like the " + bird.getName() + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}
