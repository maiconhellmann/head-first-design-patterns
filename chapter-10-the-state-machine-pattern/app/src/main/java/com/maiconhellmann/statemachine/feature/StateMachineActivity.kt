package com.maiconhellmann.statemachine.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maiconhellmann.statemachine.R

class StateMachineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.state_machine_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    StateMachineFragment.newInstance()).commitNow()
        }
    }
}
