package com.example.notepad.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

object NavigationHelper {
    fun switchFragment(view: View, direction: NavDirections) =
        Navigation.findNavController(view).navigate(direction)
}
