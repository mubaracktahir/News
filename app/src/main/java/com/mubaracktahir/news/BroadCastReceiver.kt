package com.mubaracktahir.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


/**
 * Created by Mubarak Tahir on 6/21/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
/*
class BroadCastReceiver : BroadcastReceiver() {
    companion object {
        val KEY_ACTION_SOURCE = "com.mubaracktahir.news.ui.home"
        val ACTION_ACTION_BUTTON = 1
        val ACTION_MENU_ITEM = 2
        val ACTION_TOOLBAR = 3
    }

    override fun onReceive(context: Context?, p1: Intent?) {
        val ul = p1?.dataString
        if (p1 != null && p1.hasExtra(KEY_ACTION_SOURCE))
            Toast.makeText(
                context,
                takeAction(context!!, p1.getIntExtra(KEY_ACTION_SOURCE, -1), ul!!),
                Toast.LENGTH_LONG
            ).show()
    }

    private fun takeAction(context: Context, actionId: Int, url: String) = when (actionId) {
        ACTION_ACTION_BUTTON -> "heart_button was triggered $url"
        ACTION_MENU_ITEM -> "ACTION_MENU_ITEM"
        ACTION_TOOLBAR -> "ACTION_TOOLBAR"
        else -> "a ghost button has been triggered here $url"
    }

}*/
