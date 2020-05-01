package com.maiconhellmann.statemachine.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateMachineViewModel : ViewModel() {
    val stateMachineLiveData = MutableLiveData<GunballStateMachine>().apply {
        postValue(GunballStateMachine(10) )
    }

    fun onInsertQuarter() {
        stateMachineLiveData.value?.apply {
            state.insertQuarter()
            stateMachineLiveData.postValue(this)
        }
    }

    fun onEjectQuarter() {
        stateMachineLiveData.value?.apply {
            state.ejectQuarter()
            stateMachineLiveData.postValue(this)
        }
    }

    fun onTurnCrank() {
        stateMachineLiveData.value?.apply {
            state.turnCrank()
            stateMachineLiveData.postValue(this)
        }
    }

    fun onInsertBalls(amount: Int) {
        stateMachineLiveData.value?.apply {
            state.insertGunballs(amount)
            stateMachineLiveData.postValue(this)
        }
    }
}
