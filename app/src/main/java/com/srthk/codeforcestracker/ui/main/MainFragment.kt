package com.srthk.codeforcestracker.ui.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.srthk.codeforcestracker.R
import com.srthk.codeforcestracker.data.db.AvailableContests
import com.srthk.codeforcestracker.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MainFragment : Fragment(R.layout.main_fragment), KodeinAware,
    AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: MainViewModel
    private val factory: MainViewModelFactory by instance()
    override val kodein by kodein()
    private val contestList = mutableListOf<AvailableContests>()
    private var filterOptions = arrayOf("ALL", "BEFORE", "FINISHED")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        showLoading(true)
        getData()
    }

    private fun showLoading(flag: Boolean) {
        spinner2.isVisible = !flag
        shimmer.isVisible = flag
        if (flag) {
            shimmer.stopShimmer()
        } else {
            shimmer.stopShimmer()
        }
    }

    private fun setupSpinner() {
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.apply {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@MainFragment
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        showRecyclerView(if (position == 0) {
            contestList.toRecyclerViewItem()
        } else {
            contestList.filter { it.phase == filterOptions[position] }
                .toRecyclerViewItem()
        })
    }

    private fun getData() = CoroutineScope(Dispatchers.Main).launch {
        viewModel.availableContests.await().observe(viewLifecycleOwner, Observer {
            when {
                viewModel.isInternetAvailable && !viewModel.isApiException -> {
                    when {
                        !it.isNullOrEmpty() -> {
                            contestList.addAll(it)
                            setupSpinner()
                        }
                    }
                }
                else -> {
                    this@MainFragment.requireContext()
                        .toast("There's an error better check for internet")
                    showLoading(false)
                }
            }
        })
    }

    private fun showRecyclerView(availableContests: List<AvailableContestsDataItem>) {
        val grpAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(availableContests)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = grpAdapter
        }
        showLoading(false)
    }
}

private fun List<AvailableContests>.toRecyclerViewItem() =
    this.map { AvailableContestsDataItem(it) }
