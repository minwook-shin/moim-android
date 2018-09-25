package io.github.teammoim.moim.viewModel

import androidx.lifecycle.*

class MainViewModel(private var count: Int = 0) : ViewModel(), LifecycleObserver {
    val model : MutableLiveData<Int> = MutableLiveData()
    fun increment() {
        model.value = ++count
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}