package com.airnow.ui.module.licences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.airnow.databinding.FragmentLicenceBinding
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.UIModelAdapter
import com.airnow.ui.common.BaseFragment
import com.airnow.ui.common.WrapContentLinearLayoutManager
import com.airnow.util.CustomTab
import com.airnow.util.event.EventObserver
import javax.inject.Inject

class LicencesFragment : BaseFragment("licences") {

    private lateinit var licencesViewModel: LicencesViewModel
    private lateinit var binding: FragmentLicenceBinding

    private val linearLayoutManager = LinearLayoutManager(context)
    private val licenceAdapter: UIModelAdapter by lazy {
        UIModelAdapter(
                this,
                linearLayoutManager
        )
    }

    @Inject
    lateinit var customTab: CustomTab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        licencesViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(LicencesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLicenceBinding.inflate(
                inflater,
                container,
                false
        ).apply {
            lifecycleOwner = this@LicencesFragment
        }

        subscribeOpenUrl()
        subscribeLicenceUIModels()

        initLicenses()

        return binding.root
    }

    private fun subscribeOpenUrl() {
        licencesViewModel.openUrl.observe(viewLifecycleOwner, EventObserver { url ->
            customTab.showTab(url)
        })
    }

    private fun subscribeLicenceUIModels() {
        licencesViewModel.licenceUIModels.observe(viewLifecycleOwner, Observer { uiModels ->
            licenceAdapter.addUIModels(uiModels as List<BaseUIModelAlias>)
        })
    }

    private fun initLicenses() {
        binding.recyclerView.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            swapAdapter(
                    licenceAdapter,
                    false
            )
        }
    }

}