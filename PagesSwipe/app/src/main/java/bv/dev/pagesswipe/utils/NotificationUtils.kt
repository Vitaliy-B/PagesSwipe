package bv.dev.pagesswipe.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import bv.dev.pagesswipe.MainActivity
import bv.dev.pagesswipe.R
import bv.dev.pagesswipe.fragments.PageFragment

class NotificationUtils {
    companion object {
        private const val channelId = "DEFAULT_CH"

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.val_text_notif_ch_default)
                val descriptionText = context.getString(R.string.text_notif_ch_default_descr)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }

                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(channel)
            }
        }

        private fun buildNotification(context: Context, id: Int, number: Int) : Notification {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(PageFragment.ARG_PAGE, number)
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, id, intent, 0, null)

            val builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(R.string.text_notif_msg))
                .setContentText(context.getString(R.string.text_notif) + " ${(number + 1)}")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null)?.toBitmap())

            return builder.build()
        }

        fun showNotification(context: Context, id: Int, number: Int) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(id, buildNotification(context, id, number))
        }

        fun cancelNotification(context: Context, id: Int) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .cancel(id)
        }
    }
}