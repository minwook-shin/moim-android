package io.github.teammoim.moim.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.R
import io.github.teammoim.moim.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_a.*

class TimeLineFragment : BaseFragment(){
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val observer = Observer<Int> { it ->
        it?.let { printCount(it)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectViewModel()
        clickEvent()
    }

    private fun clickEvent(){
        testButton.setOnClickListener {
            viewModel.increment()
        }
    }

    private fun connectViewModel(){
        viewModel.model.observe(this,observer)
        lifecycle.addObserver(viewModel)
    }

    private fun printCount(value: Int) {
        test_text.text = value.toString()
    }
}
