package com.example.appshortcut


import android.app.SearchManager.ACTION_KEY
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.appshortcut.databinding.ActivityNewVisitBinding


class NewVisitActivity : AppCompatActivity() {

    lateinit var binding : ActivityNewVisitBinding
    var ACTION_KEY = "anything.any"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (ACTION_KEY == intent.action){
            Toast.makeText(applicationContext,"Shortcut1 clicked",
                Toast.LENGTH_LONG).show();
        }
        if (intent != null && intent.getStringExtra("key") != null) {
            val bundleString = intent.getStringExtra("key")
            binding.intentValue.text = bundleString
        }
    }
}