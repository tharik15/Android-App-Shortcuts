package com.example.appshortcut

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appshortcut.databinding.ActivityMainBinding
import android.content.Intent
import android.content.pm.ShortcutInfo
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import java.util.*
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var activityMainBinding : ActivityMainBinding
    companion object {
        var ACTION_KEY = "anything.any"
    }
    var shortcutManagerList : MutableList<ShortcutInfoCompat> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.btnNext.setOnClickListener(this)
        activityMainBinding.btnDynamicShortcut.setOnClickListener(this)
        activityMainBinding.btnRemoveShortcut.setOnClickListener(this)
        activityMainBinding.disableShortcut.setOnClickListener(this)
        activityMainBinding.enableShortcut.setOnClickListener(this)
        activityMainBinding.updateShortcut.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnNext -> { startActivity(Intent(this@MainActivity,
                NewVisitActivity::class.java)) }
            R.id.btnDynamicShortcut -> {
//                createDynamicShortCut()
                dynamicShortcutWithDynamicList()
            }
            R.id.btnRemoveShortcut -> {removeShortcuts() }
            R.id.disableShortcut -> {disableShortcut() }
            R.id.enableShortcut -> {enableShortcut() }
            R.id.updateShortcut -> {updateShortcut() }

        }
    }


    private fun createDynamicShortCut(){
        val intent1 = Intent(applicationContext, NewVisitActivity::class.java)
        intent1.action = ACTION_KEY
        val shortcut1 = ShortcutInfoCompat.Builder(this, "id1")
            .setShortLabel("Dynamic Shortcut 1")
            .setLongLabel("This is the shortcut 1")
            .setRank(0)
            .setIntent(intent1)
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_add))
            .build()

        val intent2 = Intent(applicationContext, SecondPage::class.java)
        intent2.action = ACTION_KEY
        val bundle = Bundle()
        bundle.putString("fromString", "From the Second Shortcut")
        intent2.putExtras(bundle)
        val shortcut2 = ShortcutInfoCompat.Builder(this, "id2")
            .setShortLabel("Dynamic Shortcut 2")
            .setLongLabel("This is the shortcut 2")
            .setRank(1)
            .setIntents(
                arrayOf(
                    Intent(this, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setAction(ACTION_KEY),
                    intent2
                ))
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_chat))
            .build()

        val intent3 = Intent(applicationContext, SecondPage::class.java)
        intent3.action = ACTION_KEY
        val shortcut3 = ShortcutInfoCompat.Builder(this, "id3")
            .setShortLabel("Dynamic Shortcut 3")
            .setLongLabel("This is the shortcut 3")
            .setRank(2)
            .setIntent(intent3)
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_chat))
            .build()
        shortcutManagerList.add(shortcut1)

        ShortcutManagerCompat.setDynamicShortcuts(this, listOf(shortcut1,shortcut2,
            shortcut3))

//        disableShortcut()
    }

    private fun dynamicShortcutWithDynamicList(){
        var shortcutItemList : MutableList<ShortcutBO> = mutableListOf()
        try {
            val intent1 = Intent(applicationContext, NewVisitActivity::class.java)
            intent1.action = ACTION_KEY

            val intent2 = Intent(applicationContext, SecondPage::class.java)
            intent2.action = ACTION_KEY
            val bundle = Bundle()
            bundle.putString("fromString", "From the Second Shortcut")
            intent2.putExtras(bundle)

            val intent3 = Intent(applicationContext, SecondPage::class.java)
            intent3.action = ACTION_KEY

            shortcutItemList.add(
                ShortcutBO(
                    "id1",
                    "Dynamic Shortcut 1",
                    "",
                    0,
                    intent1,
                    R.drawable.ic_add
                )
            )
            shortcutItemList.add(
                ShortcutBO(
                    "id2",
                    "Dynamic Shortcut 2",
                    "",
                    1,
                    intent2,
                    R.drawable.ic_chat
                )
            )
            shortcutItemList.add(
                ShortcutBO(
                    "id3",
                    "Dynamic Shortcut 3",
                    "",
                    2,
                    intent3,
                    R.drawable.ic_chat
                )
            )

            shortcutItemList.forEach {
                val shortcut = ShortcutInfoCompat.Builder(this, "${it.Id}")
                    .setShortLabel(it.shortLabel)
                    .setLongLabel(it.longLabel)
                    .setRank(it.rank)
                    .setDisabledMessage("Restricted")
                    .setIntent(it.intent!!)
                    .setIcon(IconCompat.createWithResource(this, it.icon!!))
                    .build()
                shortcutManagerList.add(shortcut)
            }
            ShortcutManagerCompat.setDynamicShortcuts(this, shortcutManagerList)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun removeShortcuts() {
        ShortcutManagerCompat.removeDynamicShortcuts(this, listOf("id2"))
    }

    private fun disableShortcut() {
        ShortcutManagerCompat.disableShortcuts(this,listOf("id2"),"Restricted")
    }

    private fun enableShortcut() {
        val intent2 = Intent(applicationContext, SecondPage::class.java)
        intent2.action = ACTION_KEY
        val bundle = Bundle()
        bundle.putString("fromString", "From the Second Shortcut")
        intent2.putExtras(bundle)
        val shortcut2 = ShortcutInfoCompat.Builder(this, "id2")
            .setShortLabel("Dynamic Shortcut 2")
            .setLongLabel("This is the shortcut 2")
            .setRank(1)
            .setIntents(
                arrayOf(
                    Intent(this, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setAction(ACTION_KEY),
                    intent2
                ))
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_chat))
            .build()
        ShortcutManagerCompat.enableShortcuts(this, listOf(shortcut2))
    }


    private fun updateShortcut() {
        val intent2 = Intent(applicationContext, SecondPage::class.java)
        intent2.action = ACTION_KEY
        val bundle = Bundle()
        bundle.putString("fromString", "From the Second Shortcut")
        intent2.putExtras(bundle)
        val shortcut2 = ShortcutInfoCompat.Builder(this, "id2")
            .setShortLabel("Dynamic Shortcut 2")
            .setLongLabel("This is the shortcut 2")
            .setRank(1)
            .setIntents(
                arrayOf(
                    Intent(this, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setAction(ACTION_KEY),
                    intent2
                ))
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_chat))
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(this,shortcut2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun pinnedShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        shortcutManager!!.isRequestPinShortcutSupported
        val intent2 = Intent(applicationContext, SecondPage::class.java)
        intent2.action = ACTION_KEY
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
        val pinnedShortcutCallbackIntent =  shortcutManager.createShortcutResultIntent(pinShortcutInfo)
        // Configure the intent so that your app's broadcast receiver gets
        // the callback successfully.For details, see PendingIntent.getBroadcast().
        val successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
            pinnedShortcutCallbackIntent, /* flags */ 0)
        shortcutManager.requestPinShortcut(pinShortcutInfo,
            successCallback.intentSender)
    }

}