package com.maiconhellmann.statemachine.feature

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.maiconhellmann.statemachine.R
import kotlinx.android.synthetic.main.state_machine_fragment.eject_quarter_button
import kotlinx.android.synthetic.main.state_machine_fragment.insert_quarter_button
import kotlinx.android.synthetic.main.state_machine_fragment.state_text_view
import kotlinx.android.synthetic.main.state_machine_fragment.turn_crank_button

class StateMachineFragment : Fragment() {

    companion object {
        fun newInstance() =
            StateMachineFragment()
    }

    private lateinit var viewModel: StateMachineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.state_machine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StateMachineViewModel::class.java)

        viewModel.stateMachineLiveData.observe(this.viewLifecycleOwner, Observer {
            state_text_view.text = it.stateLog.joinToString("\n")
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insert_quarter_button.setOnClickListener { viewModel.onInsertQuarter() }
        eject_quarter_button.setOnClickListener { viewModel.onEjectQuarter() }
        turn_crank_button.setOnClickListener { viewModel.onTurnCrank() }

        state_text_view.movementMethod = ScrollingMovementMethod()
    }
}
