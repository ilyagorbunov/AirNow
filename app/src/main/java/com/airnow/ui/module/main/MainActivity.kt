package com.airnow.ui.module.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.airnow.R
import com.airnow.databinding.ActivityMainBinding
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.UIModelAdapter
import com.airnow.ui.common.BaseActivity
import com.airnow.ui.module.feed.FeedFragment
import com.airnow.ui.module.menu.BottomDrawer
import com.airnow.ui.module.menu.BottomDrawer.Companion.SELECTED_MENU_YOUR_NEWS
import com.airnow.util.extention.selectedItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainActivity: BaseActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var navController: MenuNavController

    private val fragmentManager = supportFragmentManager
    private val containerId = R.id.fragmentContainer

    private var activeFragment: Fragment? = null

    private val feedFragment: FeedFragment by lazy {
        FeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        ).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        init()
        initFilterDrawer()

        subscribeNavigation()
    }

    private fun init() {
        openFeedFragment()
        setSupportActionBar(binding.bottomBar)

        binding.slidingLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }

            override fun onPanelStateChanged(panel: View?,
                                             previousState: SlidingUpPanelLayout.PanelState?,
                                             newState: SlidingUpPanelLayout.PanelState?) {
                when(newState) {
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                }
            }

        })
    }

    private fun initFilterDrawer() {
        val linearLayouManager = FlexboxLayoutManager(this)
        linearLayouManager.flexDirection = FlexDirection.ROW
        linearLayouManager.justifyContent = JustifyContent.CENTER
        val uiModelAdapter = UIModelAdapter(this, linearLayouManager)

        mainViewModel.sourceUIModelData.observe(this, Observer { sourceUIModels ->
            uiModelAdapter.addUIModels(sourceUIModels as List<BaseUIModelAlias>)
        })

        binding.filterRecycler.apply {
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            adapter = uiModelAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = linearLayouManager
        }
    }

    fun openFeedFragment() {
        sharedPrefs.selectedItem = SELECTED_MENU_YOUR_NEWS
        changeFragment(feedFragment)
    }

    private fun changeFragment(fragment: Fragment) {
        activeFragment = fragment

        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out
                )
                .replace(containerId, fragment)
                .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                BottomDrawer().show(supportFragmentManager, BottomDrawer.TAG)
                true
            }
            R.id.action_more_options -> {
                binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeNavigation() {
        mainViewModel.onNavigation.observe(this, Observer { destination ->
            if (navController.activeDestination != destination) {
                navController.open(destination)
            }
        })
    }
}