package com.example.appshortcut

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.appshortcut.databinding.ActivityNewVisitBinding
import com.example.appshortcut.databinding.ActivitySecondPageBinding

class SecondPage : AppCompatActivity() {
    lateinit var binding: ActivitySecondPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null && intent.extras != null) {
            var bundle: Bundle = intent.extras!!
            var fromMessage = bundle.getString("fromString")
            binding.textView.text = fromMessage
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pinnedShortcut()
        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun pinnedShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        if (shortcutManager!!.isRequestPinShortcutSupported) {
            // Assumes there's already a shortcut with the ID "my-shortcut".
            // The shortcut must be enabled.
            val intent2 = Intent(applicationContext, SecondPage::class.java)
            intent2.action = MainActivity.ACTION_KEY
            val bundle = Bundle()
            bundle.putString("fromString", "From the Second Shortcut")
            intent2.putExtras(bundle)
            val pinShortcutInfo = ShortcutInfo.Builder(this, "id2").
            setShortLabel("Dynamic Shortcut 2")
                .setShortLabel("Dynamic Shortcut 2")
                .setLongLabel("This is the shortcut 2")
                .setRank(2)
                .setIntent(intent2)
                .build()

            val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)


            val successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                pinnedShortcutCallbackIntent, /* flags */ 0)

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                successCallback.intentSender)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}