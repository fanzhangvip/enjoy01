package com.zero.navigationdemo.deeplinks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentDeepLinkMainBinding

class DeepLinkMainFragment: LifeCycleFragment() {

    private val binding by lazy { FragmentDeepLinkMainBinding.inflate(layoutInflater) }


    companion object{
        const val CHANNEL_ID = "1"
        const val notificationId = 8
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btnToSettingsFragment.setOnClickListener {
           val bundle = Bundle()
            bundle.putString("params","from DeepLinkMainFragment")
            findNavController().navigate(R.id.action_deepLinkMainFragment_to_deepLinkSettingsFragment,bundle)
        }
        binding.sendNotification.setOnClickListener {
            sendNotification()
        }
        return binding.root
    }

    /**
     * 通过PendingIntent设置，当通知被点击后需要跳转到哪个destination，以及传递的参数
     */
    private fun getPendingIntent(): PendingIntent? {
        if (activity != null) {
            val bundle = Bundle()
            bundle.putString("params", "from Notification")
            return Navigation
                .findNavController(activity!!, R.id.sendNotification)
                .createDeepLink()
                .setGraph(R.navigation.deeplink)
                .setDestination(R.id.deepLinkSettingsFragment)
                .setArguments(bundle)
                .createPendingIntent()
        }
        return null
    }

    /**
     * 向通知栏发送一个通知
     */
    private fun sendNotification() {
        if (activity == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, "ChannelName", importance)
            channel.description = "description"
            val notificationManager = activity!!.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
        val builder =
            NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("DeepLinkDemo")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true)
        val notificationManager: NotificationManagerCompat =
            NotificationManagerCompat.from(requireActivity())
        notificationManager.notify(notificationId, builder.build())
    }
}