package com.example.notepad.ui.fragments

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope{
//    private lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        job = Job()
//    }

//    override fun onDestroy() {
//        super.onDestroy()
////        job.cancel()
//    }
}