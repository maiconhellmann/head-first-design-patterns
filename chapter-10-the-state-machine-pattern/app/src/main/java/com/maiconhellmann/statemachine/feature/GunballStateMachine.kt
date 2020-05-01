package com.maiconhellmann.statemachine.feature

import android.util.Log
import com.maiconhellmann.statemachine.feature.GunballStateMachine.Companion.TAG

/*
 * This file is part of State Machine.
 * 
 * Created by maiconhellmann on 01/05/2020
 * 
 * (c) 2020 
 */
data class GunballStateMachine(var gunballAmount: Int = 0) {
    var state: State = if (gunballAmount > 0) {
        NoQuarter(this)
    } else {
        OutOfGunballs(this)
    }
    set(value) {
        field = value
        stateLog.add(value)
    }

    val stateLog = mutableListOf<State>(state)

    companion object {
        const val TAG = "GunballStateMachine"
    }
}

sealed class State(protected val stateMachine: GunballStateMachine) {
    open fun insertQuarter() {
        Log.w(TAG, "fun insertQuarter() not allowed!")
    }

    open fun ejectQuarter() {
        Log.w(TAG, "fun ejectQuarter() not allowed!")
    }

    open fun turnCrank() {
        Log.w(TAG, "fun turnCrank() not allowed!")
    }

    open fun dispense() {
        Log.w(TAG, "fun dispense() not allowed!")
    }

    open fun insertGunballs(amount: Int) {
        Log.w(TAG, "fun insertGunballs() not allowed!")
    }

    override fun toString(): String {
        return this::class.java.simpleName
    }
}

class OutOfGunballs(stateMachine: GunballStateMachine) : State(stateMachine) {
    override fun insertGunballs(amount: Int) {
        stateMachine.gunballAmount = amount
        stateMachine.state = NoQuarter(stateMachine)
    }
}

class NoQuarter(stateMachine: GunballStateMachine) : State(stateMachine) {
    override fun insertQuarter() {
        stateMachine.state = HasQuarter(stateMachine)
    }
}

class HasQuarter(stateMachine: GunballStateMachine) : State(stateMachine) {
    override fun ejectQuarter() {
        stateMachine.state = NoQuarter(stateMachine)
    }

    override fun turnCrank() {
        stateMachine.state = GunballSold(stateMachine)
        stateMachine.state.dispense()
    }
}

class GunballSold(stateMachine: GunballStateMachine) : State(stateMachine) {
    override fun dispense() {
        if (stateMachine.gunballAmount > 0) {
            Log.d(TAG, "Gunball sold")
            stateMachine.gunballAmount--
            stateMachine.state = NoQuarter(stateMachine)
        } else {
            Log.d(TAG, "There is no gunballs available!")
            stateMachine.state = OutOfGunballs(stateMachine)
        }
    }
}
