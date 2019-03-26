package com.airnow.ui.module.location.add

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.airnow.R
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.airnow.data.DataStatus
import com.airnow.data.model.Location
import com.airnow.databinding.DialogAddLocationBinding
import com.airnow.ui.module.location.LocationViewModel
import com.airnow.ui.module.main.MainViewModel
import com.airnow.util.extention.hideKeyboard
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class AddDialog : DialogFragment() {

    private lateinit var viewModel: AddViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: DialogAddLocationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::viewModel.isInitialized) {
            viewModel = ViewModelProviders
                    .of(this, viewModelFactory)
                    .get(AddViewModel::class.java)
        }

        if (!::mainViewModel.isInitialized) {
            mainViewModel = ViewModelProviders
                    .of(activity!!)
                    .get(MainViewModel::class.java)
        }

        var context = ContextThemeWrapper(activity, R.style.AppTheme_FullScreenDialog)
        binding = DataBindingUtil.inflate<DialogAddLocationBinding>(LayoutInflater.from(context),
                R.layout.dialog_add_location, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { v ->
            binding.locationField.hideKeyboard()
            dismiss()
        }
        binding.toolbar.title = resources.getString(R.string.add_location)
        binding.toolbar.inflateMenu(R.menu.add_location_dialog)
        binding.toolbar.setOnMenuItemClickListener { item ->
            viewModel.saveWeatherLocation(Location(cityName = binding.locationField.text.toString()))
            true
        }

        binding.locationField.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            binding.locationField.post {
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(binding.locationField, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        binding.locationField.requestFocus()

        subscribeSaveLocation()
    }

    private fun subscribeSaveLocation() {
        viewModel.saveWeatherLocationdStateLiveData.observe(this, Observer<DataStatus<List<Location>>> { state ->
            state?.let {
                when (state) {
                    is DataStatus.Successful -> {
                        mainViewModel.onUpdateLocations()
                        binding.locationField.hideKeyboard()
                        dismiss()
                    }
                    is DataStatus.Failed -> {
                        binding.locationField.hideKeyboard()
                        dismiss()
                    }
                }
            }
        })
    }

    companion object {
        val TAG = "add_location_dialog"

        fun display(fragmentManager: FragmentManager): AddDialog {
            val exampleDialog = AddDialog()
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}
