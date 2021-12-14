package com.nogotech.draganddropsample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _listFlow = MutableStateFlow(
        listOf(
            "1",
            "2",
            "3",
            "4",
            "5"
        )
    )
    val listFlow = _listFlow.asStateFlow()
}