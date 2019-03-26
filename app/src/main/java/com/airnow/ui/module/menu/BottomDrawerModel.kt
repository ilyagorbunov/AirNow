package com.airnow.ui.module.menu

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.airnow.contactIntent
import com.airnow.data.repo.SourceRepo
import com.airnow.ui.common.BaseViewModel
import com.airnow.util.event.Event
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class BottomDrawerModel @Inject constructor(
        sourceRepo: SourceRepo
) : BaseViewModel() {

    val sourceData = sourceRepo.getAll()
    val source = sourceRepo

    val startIntent = MutableLiveData<Event<Intent>>()

    fun contactEmail() {
        startIntent.postValue(Event(contactIntent))
    }
}