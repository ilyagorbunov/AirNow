package com.airnow.ui.module.main

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.airnow.App
import com.airnow.R
import com.airnow.contactIntent
import com.airnow.data.model.Source
import com.airnow.data.repo.PostRepo
import com.airnow.data.repo.SourceRepo
import com.airnow.ui.adapter.UIModelClickListener
import com.airnow.ui.adapter.model.SourceUIModel
import com.airnow.ui.common.BaseViewModel
import com.airnow.ui.module.menu.BottomDrawer.Companion.SELECTED_MENU_YOUR_NEWS
import com.airnow.util.event.Event
import com.airnow.util.extention.selectedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
        val app: App,
        sourceRepo: SourceRepo,
        postRepo: PostRepo,
        val sharedPrefs: SharedPreferences
) : BaseViewModel() {

    val resourceEvent = MutableLiveData<Event<Boolean>>()
    val toolbarTitle = MutableLiveData<String>().apply { value = app.resources.getString(R.string.posts_your_news) }
    val isBookmarksShown = MutableLiveData<Boolean>().apply { value = false }
    val isBookmarksButtonChecked = MutableLiveData<Boolean>().apply { value = false }
    val onNavigation = MutableLiveData<Destination>()
    val onUpdateLocations = MutableLiveData<Boolean>()

    val sourceUIModelData: LiveData<List<SourceUIModel>> =
            Transformations.map(sourceRepo.getAll()) { sourceList ->
                sourceList.map { source ->
                    SourceUIModel(source, sourceClickListener)
                }
            }

    private val sourceClickListener = object : UIModelClickListener<Source> {
        override fun onClick(source: Source) {
            source.isActive = !source.isActive
            launch(Dispatchers.IO) {
                /* update source when activated */
                if (source.isActive) {
                    postRepo.refresh(
                            this,
                            listOf(source)
                    )
                }
                sourceRepo.update(source)
            }
        }
    }

    fun onBookmarksEvent() {
        val isCurrentlySelected = (isBookmarksButtonChecked.value ?: false)
        sharedPrefs.selectedItem = SELECTED_MENU_YOUR_NEWS
        isBookmarksShown.postValue(!isCurrentlySelected)
        isBookmarksButtonChecked.postValue(!isCurrentlySelected)

        val title = if (isCurrentlySelected) R.string.posts_your_news else R.string.posts_saved
        toolbarTitle.postValue(app.resources.getString(title))
        onDestinationSelected(Destination.FEED)
    }

    fun onResourceEvent(isEnable: Boolean, title: String) {
        resourceEvent.value = Event(isEnable)
        toolbarTitle.postValue(title)
    }

    fun onOpenSourceEvent(title: String) {
        toolbarTitle.postValue(title)
    }

    fun onAddLocationEvent(title: String) {
        toolbarTitle.postValue(title)
    }

    fun onDestinationSelected(destination: Destination) {
        onNavigation.postValue(destination)
    }

    fun onUpdateLocations() {
        onUpdateLocations.postValue(true)
    }
}