package com.jeebley.task.ui.splash

import android.os.Bundle
import com.jeebley.task.R
import com.jeebley.task.ui.main.view.HomeActivity
import com.jeebley.task.ui.base.BaseActivity
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {
    private val delayTime: Long = 3000
    private var delayJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Navigate with delay
        delayJob = delaySplashScreen()
    }

    public override fun onDestroy() {
        delayJob?.cancel()
        super.onDestroy()
    }

    private fun delaySplashScreen() = launch(UI) {
        async(CommonPool) { delay(delayTime) }.await()
        startActivity<HomeActivity>()
        finish()
    }
}
