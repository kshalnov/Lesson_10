package ru.gb.course1.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Nullable
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toast_button).setOnClickListener(v -> showToast("Я тост сообщение! Ура!"));
        findViewById(R.id.snack_button).setOnClickListener(v -> showSnack(v));
        findViewById(R.id.cancel_snack_button).setOnClickListener(v -> cancelSnack());
        findViewById(R.id.alert_button).setOnClickListener(v -> showAlert());
        findViewById(R.id.custom_alert_button).setOnClickListener(v -> showCustomAlert());
        findViewById(R.id.dialog_fragment_button).setOnClickListener(v -> showDialogFragment());
        findViewById(R.id.bottom_sheet_button).setOnClickListener(v -> showBottomSheet());
        findViewById(R.id.notification_button).setOnClickListener(v -> showNotification());
    }

    private void showNotification() {
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Заголовок")
                .setContentText(LOREM_IPSUM)
                .setSmallIcon(R.drawable.ic_baseline_star_outline_24)
                .build();
        NotificationManagerCompat.from(this).notify(42, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat.from(this).createNotificationChannel(
                    new NotificationChannel(
                            CHANNEL_ID, "Name",
                            NotificationManager.IMPORTANCE_HIGH
                    )
            );
        }
    }

    private void showBottomSheet() {
        MyBottomSheetDialog dialog = new MyBottomSheetDialog();
        dialog.show(getSupportFragmentManager(), null);
    }

    private void showDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show(getSupportFragmentManager(), null);
    }

    private void showCustomAlert() {
        View customView = getLayoutInflater().inflate(R.layout.dialog_with_buttons, null, false);
        customView.findViewById(R.id.button_1).setOnClickListener(v -> showToast("1"));
        customView.findViewById(R.id.button_2).setOnClickListener(v -> showToast("2"));
        new AlertDialog.Builder(this)
                .setTitle("Заголовок")
                .setView(customView)
                .setPositiveButton("Ок", (dialog, id) -> showToast("Нажали ОК"))
                .show();
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Заголовок")
                .setIcon(R.drawable.ic_baseline_star_outline_24)
                .setMessage(LOREM_IPSUM)
                .setPositiveButton("Да", (dialog, id) -> showToast("Нажали ДА"))
                .setNegativeButton("Нет", (dialog, id) -> showToast("Нажали НЕТ"))
//                .setNeutralButton("Не знаю", (dialog, id) -> showToast("Нажали НЕ ЗНАЮ"))
                .setNeutralButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("Нажали НЕ ЗНАЮ");
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void showSnack(View view) {
        snackbar = Snackbar.make(view, "Hello, I'm Snack!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Пока", v -> cancelSnack())
                .setActionTextColor(Color.MAGENTA)
                .setBackgroundTint(Color.CYAN)
                .setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void cancelSnack() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

}