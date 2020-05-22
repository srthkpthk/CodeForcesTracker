package com.srthk.codeforcestracker.ui.main

import android.os.Bundle
import android.view.View
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

class MainFragment : Fragment(R.layout.main_fragment), KodeinAware {
    private lateinit var viewModel: MainViewModel
    private val factory: MainViewModelFactory by instance()
    override val kodein by kodein()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        getData()
    }

    private fun getData() = CoroutineScope(Dispatchers.Main).launch {
        viewModel.availableContests.await().observe(viewLifecycleOwner, Observer {
            when {
                viewModel.isInternetAvailable && !viewModel.isApiException -> {
                    println(it)
                    when {
                        !it.isNullOrEmpty() -> {
                            showRecyclerView(it.filter { it.phase != "FINISHED" }
                                .toRecyclerViewItem())
                        }
                    }
                }
                else -> {
                    this@MainFragment.requireContext()
                        .toast("There's an error better check for internet")
//                    showLoading(false)
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
    }


}

private fun List<AvailableContests>.toRecyclerViewItem() =
    this.map { AvailableContestsDataItem(it) }
