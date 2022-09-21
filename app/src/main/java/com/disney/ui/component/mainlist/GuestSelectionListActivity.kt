package com.disney.ui.component.mainlist

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.disney.R
import com.disney.data.Resource
import com.disney.data.model.NYCGuesttem
import com.disney.databinding.HomeActivityBinding
import com.disney.ui.base.BaseActivity
import com.disney.ui.component.mainlist.adapter.SectionAdapter
import com.disney.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GuestSelectionListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val guestViewModel: GuestViewModel by viewModels()

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.title_activity_list)
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.setHasFixedSize(true)
        handleContinueButton(false) // default disable
        // guestViewModel.getGuestList() // TODO : call api and fetch guest list
        bindListData(null)
    }

    private fun bindListData(list: NYCGuesttem?) {
        // todo bind data in adapter if getting data from api

        val adapter = SectionAdapter(guestViewModel)
        binding.rvList.adapter = adapter

        val decorator = StickyHeaderItemDecorator(adapter)
        decorator.attachToRecyclerView(binding.rvList)

        adapter.items = object : ArrayList<Section>() {
            init {
                var section = 0
                val limit = 15
                for (i in 0..limit) {
                    if (i < limit / 2) {
                        if (i == 0) {
                            section = i
                            add(SectionHeader(section, getString(R.string.have_reservation)))
                        } else {
                            add(SectionItem(section, "Dale Gibson $i", true))
                        }
                    } else {
                        if (i == limit / 2) {
                            section = i
                            add(SectionHeader(section, getString(R.string.need_reservation)))
                        } else {
                            add(SectionItem(section, "Dale Gibson $i", false))
                        }
                    }
                }
                add(SectionFooter(getString(R.string.footer_text)))
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean) {
        binding.rvList.visibility = if (show) VISIBLE else GONE
    }

    private fun showLoadingView() {
        binding.rvList.toGone()
    }

    private fun handleContinueButton(enable: Boolean) {
        binding.buttonContinue.apply {
            if (enable) {
                this.alpha = 1f
                this.isEnabled = true
            } else {
                this.alpha = 0.5f
                this.isEnabled = false
            }
        }
    }

    private fun handleGuestList(status: Resource<NYCGuesttem>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(list = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { guestViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(guestViewModel.guestListLiveData, ::handleGuestList)
        observe(guestViewModel.enableButtonLiveData, ::handleContinueButton)
        observeSnackBarMessages(guestViewModel.showSnackBar)
        observeToast(guestViewModel.showToast)
    }
}
