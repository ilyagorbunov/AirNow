package com.airnow.ui.module.menu

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.airnow.App
import com.airnow.R
import com.airnow.data.model.Source
import com.airnow.databinding.NavigationViewTasksListsBinding
import com.airnow.ui.module.main.Destination
import com.airnow.ui.module.main.MainViewModel
import com.airnow.util.event.EventObserver
import com.airnow.util.extention.getScreenHeight
import com.airnow.util.extention.selectedItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlinx.android.synthetic.main.navigation_view_tasks_lists.view.*

class BottomDrawer: AppCompatDialogFragment() {

    private lateinit var viewModel: BottomDrawerModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: NavigationViewTasksListsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var app: App

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    //TODO: replace logic to ViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Get resources
        val staturBarIdentifier = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = if (staturBarIdentifier != 0) resources.getDimensionPixelSize(staturBarIdentifier) else 0
        val cornerRadiusStart = resources.getDimension(R.dimen.bottom_sheet_corner_radius)
        val bottomSheetImageMinimalHeight = resources.getDimension(R.dimen.bottom_sheet_decoration_minimal_height)
        val headerElevation = resources.getDimension(R.dimen.header_elevation)
        val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

        if (!::viewModel.isInitialized) {
            viewModel = ViewModelProviders
                    .of(this, viewModelFactory)
                    .get(BottomDrawerModel::class.java)
        }

        if (!::mainViewModel.isInitialized) {
            mainViewModel = ViewModelProviders
                    .of(activity!!)
                    .get(MainViewModel::class.java)
        }

        var context = ContextThemeWrapper(activity, R.style.AppTheme)
        binding = DataBindingUtil.inflate<NavigationViewTasksListsBinding>(LayoutInflater.from(context),
                R.layout.navigation_view_tasks_lists, null, false)
        binding.viewModel = viewModel

        //Create dialog
        val dialog = Dialog(context, R.style.BottomSheetTasksListStyle)
        dialog.setContentView(binding.root)

        //Extends dialog on whole screen (also under status bar)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        var tasksLists:List<Source>? = null

        viewModel.sourceData.observe(this, Observer {
            tasksLists = it
            val menu = binding.navigationViewTasksLists.menu
            menu.clear()

            val selectedList = sharedPrefs.selectedItem

            for ((index, taskList) in  it.withIndex()) {
                menu.add(0,index + MENU_LIST_ITEM_FIRST_INDEX, index, taskList.name).setCheckable(true).isChecked = (taskList.id == selectedList)
            }

            menu.add(1, ID_MENU_YOUR_NEWS, it.size,R.string.posts_your_news).setIcon(R.drawable.ic_check_circle_black_24dp).setCheckable(true).isChecked = (selectedList == SELECTED_MENU_YOUR_NEWS)
            menu.add(2, ID_MENU_FEEDBACK,it.size + 1,R.string.send_feedback).setIcon(R.drawable.ic_feedback)
            menu.add(3, ID_MENU_WEATHER,it.size + 1,R.string.weather).setIcon(R.drawable.ic_cloud_circle_black_24dp).setCheckable(true).isChecked = (selectedList == SELECTED_MENU_WEATHER)
            menu.add(4, ID_MENU_ADD_LOCATION,it.size + 1,R.string.location).setIcon(R.drawable.ic_add_black_24dp).setCheckable(true).isChecked = (selectedList == SELECTED_MENU_ADD_LOCATION)
            menu.add(5, ID_MENU_OPEN_SOURCE,it.size + 1,R.string.opens_source_license).setCheckable(true).isChecked = (selectedList == SELECTED_MENU_OPEN_SOURCE)

            binding.root.invalidate()
        })

        val behaviour = BottomSheetBehavior.from(binding.bottomSheetLayout)

        //Setup peekHeight to 50% of the screen
        val screenHeight = activity?.getScreenHeight()
        if (screenHeight != null) behaviour.peekHeight = (screenHeight * 0.5f).roundToInt() + statusBarHeight

        binding.imageBackground.setPadding(0, statusBarHeight,0,0)
        val lp = binding.imageBackground.layoutParams
        lp.height = (bottomSheetImageMinimalHeight + statusBarHeight).roundToInt()
        binding.imageBackground.layoutParams = lp

        behaviour.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, p1: Float) {

                val interpolator = Math.max(p1 - 0.75f, 0f) * 4f

                if (binding.coordinatorLayout.height <= binding.bottomSheetLayout.height) {
                    binding.root.close_button.alpha = interpolator
                    val paddingTop = (1f - interpolator) * statusBarHeight
                    binding.imageBackground.setPadding(0, paddingTop.roundToInt(), 0, 0)
                }

                val drawable = GradientDrawable()
                val newRadius = (1f - interpolator) * cornerRadiusStart
                drawable.cornerRadii = floatArrayOf(newRadius, newRadius, newRadius, newRadius, 0f, 0f, 0f, 0f)
                drawable.setColor(backgroundColor)

                binding.imageBackground.setImageDrawable(drawable)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dialog.cancel()
                }
            }
        })

        //Hide bottom sheet before close
        fun hideBottomSheet() {
            behaviour.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.navigationViewTasksLists.setNavigationItemSelectedListener {
            when(it.itemId) {
                ID_MENU_YOUR_NEWS -> {
                    sharedPrefs.selectedItem = SELECTED_MENU_YOUR_NEWS
                    mainViewModel.onResourceEvent(false, getString(R.string.posts_your_news))
                    mainViewModel.onDestinationSelected(Destination.FEED)
                    hideBottomSheet()
                }
                ID_MENU_FEEDBACK -> {
                    viewModel.contactEmail()
                    hideBottomSheet()
                }
                ID_MENU_ADD_LOCATION -> {
                    sharedPrefs.selectedItem = SELECTED_MENU_ADD_LOCATION
                    mainViewModel.onDestinationSelected(Destination.ADD_LOCATION)
                    mainViewModel.onAddLocationEvent(getString(R.string.location))
                    hideBottomSheet()
                }
                ID_MENU_WEATHER -> {
                    sharedPrefs.selectedItem = SELECTED_MENU_WEATHER
                    mainViewModel.onDestinationSelected(Destination.WEATHER)
                    mainViewModel.onAddLocationEvent(getString(R.string.weather))
                    hideBottomSheet()
                }
                ID_MENU_OPEN_SOURCE -> {
                    sharedPrefs.selectedItem = SELECTED_MENU_OPEN_SOURCE
                    mainViewModel.onDestinationSelected(Destination.OPEN_SOURCE_LICENSES)
                    mainViewModel.onOpenSourceEvent(getString(R.string.opens_source_license))
                    hideBottomSheet()
                }
                else -> {
                    val task = tasksLists?.get(it.itemId - MENU_LIST_ITEM_FIRST_INDEX)
                    if (task != null) {
                        sharedPrefs.selectedItem = task.id
                        mainViewModel.onResourceEvent(true, it.title as String)
                    }
                    mainViewModel.onDestinationSelected(Destination.FEED)
                    hideBottomSheet()
                }
            }
            true
        }

        //Add elevation to header when scrolling
        binding.nestedScrollViewTasksLists.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            binding.appBarTasksLists.elevation = if (scrollY == 0) 0f else headerElevation
        }

        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                hideBottomSheet()
                true
            }
            false
        }


        binding.root.close_button.setOnClickListener {
            hideBottomSheet()
        }

        subscribeStartIntentEvent()

        return dialog
    }

    private fun subscribeStartIntentEvent() {
        viewModel.startIntent.observe(this, EventObserver { intent ->
            startActivity(intent)
        })
    }

    private fun showNotImplemented() {
        Toast.makeText(activity,getString(R.string.not_implemented),Toast.LENGTH_SHORT).show()
    }

    companion object {
        val TAG = BottomDrawer::class.java.simpleName
        const val ID_MENU_YOUR_NEWS = 0
        const val ID_MENU_FEEDBACK = 1
        const val ID_MENU_WEATHER = 2
        const val ID_MENU_ADD_LOCATION = 3
        const val ID_MENU_OPEN_SOURCE = 4
        const val MENU_LIST_ITEM_FIRST_INDEX = 5

        const val SELECTED_MENU_YOUR_NEWS = -1
        const val SELECTED_MENU_WEATHER = -2
        const val SELECTED_MENU_ADD_LOCATION = -3
        const val SELECTED_MENU_OPEN_SOURCE = -4
    }
}