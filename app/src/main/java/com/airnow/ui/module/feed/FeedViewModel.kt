package com.airnow.ui.module.feed

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.airnow.data.model.Post
import com.airnow.data.repo.PostRepo
import com.airnow.data.repo.SourceRepo
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.model.PostUIModel
import com.airnow.ui.common.BaseViewModel
import com.airnow.util.event.Event
import com.airnow.util.extention.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("UNCHECKED_CAST", "WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class FeedViewModel @Inject constructor(
        private val sourceRepo: SourceRepo,
        private val postRepo: PostRepo,
        private val sharedPrefs: SharedPreferences
) : BaseViewModel() {

    private val feedType = MutableLiveData<FeedType>()
    private var refreshJob = Job()

    val postsLiveData: LiveData<PagedList<PostUIModel>> = switchMap(feedType) { type ->
        when (type) {
            FeedType.POSTS -> {
                postRepo.getAll()
                        .asLiveData(pagedListConfig) { posts ->
                            createPostUIModel(posts)
                        }
            }
            FeedType.BOOKMARKS -> {
                postRepo.getBookmarked()
                        .asLiveData(pagedListConfig) { posts ->
                            createPostUIModel(posts)
                        }
            }
            FeedType.SOURCE -> {
                postRepo.getPostsFromSource()
                        .asLiveData(pagedListConfig) { posts ->
                            createPostUIModel(posts)
                        }
            }
        }
    }

    val isProgressVisible = MutableLiveData<Boolean>().apply { value = false }
    val isRefreshing = MutableLiveData<Boolean>().apply { value = false }
    val openPostDetail = MutableLiveData<Event<Post>>()
    val sharePost = MutableLiveData<Event<Post>>()
    val showUndoBookmarkSnack = MutableLiveData<Event<() -> Unit>>()

    init {
        refreshJob = refresh()
    }

    private val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .setEnablePlaceholders(false)
            .build()

    private fun createPostUIModel(posts: List<Post>): List<PostUIModel> {
        /* mark first item of every page as large */
        if (posts.isNotEmpty()) {
            posts[0].layoutType = UIModelType.POST_LARGE
        }

        return posts.map { PostUIModel(it, postClickCallback) }
    }

    companion object {
        private const val ITEM_PER_PAGE = 20
    }

    private val postClickCallback =
            object : PostClickListener {
                override fun onItemClick(post: Post) {
                    openPostDetail.postValue(Event(post))
                }

                override fun onShareClick(post: Post) {
                    sharePost.postValue(Event(post))
                }

                override fun onBookmarkClick(post: Post) {
                    togglePostBookmark(post)
                }
            }


    fun setFeedType(feedType: FeedType) {
        if (this.feedType.value != feedType || this.feedType.value == FeedType.SOURCE) {
            this.feedType.value = feedType
        }
    }

    fun refresh() = launch(Dispatchers.IO) {
        if (postsLiveData.value?.isEmpty() == true) {
            isProgressVisible.postValue(true)
        }

        postRepo.refresh(
                this,
                sourceRepo.getActives()
        )

        isProgressVisible.postValue(false)
        isRefreshing.postValue(false)
    }

    fun togglePostBookmark(post: Post) {
        if (post.isBookmarked()) {
            post.bookmarked = 0

            if (feedType.value == FeedType.BOOKMARKS) {
                showUndoBookmarkSnack.postValue(Event({
                    togglePostBookmark(post)
                }))
            }
        } else {
            post.bookmarked = 1
        }
        launch(Dispatchers.IO) {
            postRepo.updatePost(post)
        }
    }
}