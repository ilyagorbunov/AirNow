package com.airnow.ui.module.feed

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import com.airnow.R
import com.airnow.databinding.FragmentFeedBinding
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.UIModelPaginatedAdapter
import com.airnow.ui.common.BaseFragment
import com.airnow.ui.common.WrapContentLinearLayoutManager
import com.airnow.ui.module.main.MainViewModel
import com.airnow.util.CustomTab
import com.airnow.util.event.EventObserver
import com.airnow.util.extention.isOnline
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject


class FeedFragment : BaseFragment("feed") {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFeedBinding

    private val paginatedAdapter by lazy { UIModelPaginatedAdapter(this) }

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var customTab: CustomTab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(FeedViewModel::class.java)

        mainViewModel = ViewModelProviders
                .of(activity!!)
                .get(MainViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFeedBinding.inflate(
                inflater,
                container,
                false
        ).apply {
            viewModel = feedViewModel
            lifecycleOwner = this@FeedFragment
        }

        subscribePosts()
        subscribeIsRefreshing()
        subscribePostOpenEvent()
        subscribeUnBookmarkEvent()
        subscribeBookmarksOpenEvent()
        subscribeResourceSelectEvent()
        subscribePostShareEvent()

        feedViewModel.setFeedType(FeedType.POSTS)

        initFeed()
        initSwipeRefresh()

        return binding.root
    }

    private fun initFeed() {
        binding.newsRecyclerView.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            swapAdapter(
                    paginatedAdapter,
                    false
            )
        }
    }

    private fun subscribeIsRefreshing() {
        feedViewModel.isRefreshing.observe(viewLifecycleOwner, Observer { isRefreshing ->
            swipeRefreshPosts.isRefreshing = isRefreshing
        })
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshPosts.setOnRefreshListener {
            when {
                context?.isOnline() == true -> feedViewModel.refresh()
                else -> {
                    binding.swipeRefreshPosts.isRefreshing = false
                    Snackbar.make(
                            binding.root,
                            R.string.info_no_internet,
                            Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun subscribePosts() {
        feedViewModel.postsLiveData.observe(viewLifecycleOwner, Observer { pagedList ->
            paginatedAdapter.submitList(pagedList as PagedList<BaseUIModelAlias>)
        })
    }

    private fun subscribePostOpenEvent() {
        feedViewModel.openPostDetail.observe(viewLifecycleOwner, EventObserver { post ->
            customTab.showTab(post.link)
        })
    }


    private fun subscribeUnBookmarkEvent() {
        feedViewModel.showUndoBookmarkSnack.observe(viewLifecycleOwner, EventObserver { onUndo ->
            Snackbar.make(
                    binding.root,
                    R.string.info_bookmark_removed,
                    Snackbar.LENGTH_LONG
            ).apply {
                setActionTextColor(Color.YELLOW)
                setAction(R.string.undo) { onUndo() }
            }.run {
                show()
            }
        })
    }

    private fun subscribeBookmarksOpenEvent() {
        mainViewModel.isBookmarksShown.observe(viewLifecycleOwner, Observer { isEnabled ->
            val feedType = if (isEnabled) FeedType.BOOKMARKS else FeedType.POSTS
            feedViewModel.setFeedType(feedType)
        })
    }

    private fun subscribeResourceSelectEvent() {
        mainViewModel.resourceEvent.observe(viewLifecycleOwner, EventObserver { isEnabled ->
            val feedType = if (isEnabled) FeedType.SOURCE else FeedType.POSTS
            feedViewModel.setFeedType(feedType)
        })
    }

    private fun subscribePostShareEvent() {
        feedViewModel.sharePost.observe(viewLifecycleOwner, EventObserver { post ->
            startActivityForResult(post.getShareIntent(), REQUEST_CODE_SHARE)
        })
    }

    companion object {
        private const val REQUEST_CODE_SHARE = 4122
    }
}