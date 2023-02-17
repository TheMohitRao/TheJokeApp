package com.themohitrao.core_ui

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import com.themohitrao.core_network.util.NetworkUtil

inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}

fun String.snackBar(view: View, duration: Int = Snackbar.LENGTH_LONG): Snackbar {
    return Snackbar.make(view, this, duration).also {
        it.setBackgroundTint(ContextCompat.getColor(view.context, R.color.dodger_blue))
        it.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 4
        it.setTextColor(Color.WHITE)
    }.apply { show() }
}

fun LottieAnimationView.playSafeAnimation(){
    if(NetworkUtil(this.context).isNetworkAvailable()){
        this.playAnimation()
    }else{
        this.context.getString(R.string.check_internet_connection).snackBar(this)
    }
}

fun LottieAnimationView.playViaUrl(url:String){
    this.setAnimationFromUrl(url)
    this.playSafeAnimation()
}
