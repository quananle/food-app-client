package com.leanhquan.deliveryfoodver2.Service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leanhquan.deliveryfoodver2.Common.Common;
import com.leanhquan.deliveryfoodver2.Model.Request;
import com.leanhquan.deliveryfoodver2.OrderListActivity;
import com.leanhquan.deliveryfoodver2.R;

public class ListenOrderService extends Service implements ChildEventListener {

    private FirebaseDatabase database;
    private DatabaseReference requests;

    public ListenOrderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        requests = database.getReference().child("requests");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requests.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Request request = snapshot.getValue(Request.class);
        assert request != null;
        showNotification(snapshot.getKey(), request);
    }

    private void showNotification(String key, Request request) {

        //when using notification in SKD >= 26 must be have Chanelid

        String channelId = "default_channel_id";
        Intent i = new Intent(getBaseContext(), OrderListActivity.class);
        i.putExtra("userPhone", request.getPhone());
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notice = new NotificationCompat.Builder(this, channelId);
        notice.setAutoCancel(true)
                .setPriority(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Delivery App")
                .setContentInfo("Đơn đặt hàng của bạn đã được cập nhật")
                .setContentText("Đơn hàng #"+key+" "+ Common.convertCodeToStatus(request.getStatus()))
                .setContentIntent(pendingIntent)
                .setContentInfo("info")
                .setSmallIcon(R.drawable.ic_message);
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(1,notice.build());
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
