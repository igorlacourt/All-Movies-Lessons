package com.example.allmovies

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters  {
   @JvmStatic
   @BindingAdapter("isVisible")
   fun displayView(view: View, isVisible: Boolean) {
       view.visibility = if(isVisible) { View.VISIBLE } else { View.GONE }
   }
}