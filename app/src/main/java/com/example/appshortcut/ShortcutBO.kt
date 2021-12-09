package com.example.appshortcut

import android.content.Intent

data class ShortcutBO(val Id :String? = "",val shortLabel:String= "",
val longLabel:String = "",val rank:Int = 0,val intent:Intent? = null,
                      val icon:Int ? =null)
