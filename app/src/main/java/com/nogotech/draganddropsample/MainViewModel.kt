package com.nogotech.draganddropsample

import androidx.lifecycle.ViewModel
import java.util.Collections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {
    private val _listFlow = MutableStateFlow(
        listOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
        )
    )
    val listFlow = _listFlow.asStateFlow()

    var fromPosition = 0
    fun startSelectItem(position: Int) {
        fromPosition = position
    }

    fun endSelectItem(position: Int) {
        _listFlow.value.run {
            if (fromPosition < position) {
                for (i in fromPosition until position) {
                    Collections.swap(this, i, i + 1)
                }
            } else if (fromPosition > position) {
                for (i in fromPosition downTo position + 1) {
                    Collections.swap(this, i, i - 1)
                }
            }

            _listFlow.value = this
        }
    }
}