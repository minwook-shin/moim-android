package io.github.teammoim.moim.viewModel

import androidx.lifecycle.*

class MainViewModel : ViewModel(), LifecycleObserver {
    val model : MutableLiveData<Int> = MutableLiveData()


//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}