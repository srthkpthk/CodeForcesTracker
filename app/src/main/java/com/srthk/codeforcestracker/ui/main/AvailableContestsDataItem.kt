package com.srthk.codeforcestracker.ui.main

import com.srthk.codeforcestracker.R
import com.srthk.codeforcestracker.data.db.AvailableContests
import com.srthk.codeforcestracker.databinding.RecyclerViewItemBinding
import com.xwray.groupie.databinding.BindableItem

class AvailableContestsDataItem(private val availableContests: AvailableContests) :
    BindableItem<RecyclerViewItemBinding>() {
    override fun getLayout() = R.layout.recycler_view_item

    override fun bind(viewBinding: RecyclerViewItemBinding, position: Int) {
        viewBinding.contest = availableContests
//        viewBinding.contest.startTimeSeconds = toTime(availableContests.startTimeSeconds)
    }

//    private fun toTime(startTimeSeconds: Int): Int {
//        fun toTime(startTimeSeconds: Int): Int {
//            return if (startTimeSeconds / Constants.HOUR_DIV > 24) {
//                startTimeSeconds / Constants.DAY_DIV
//            } else {
//                startTimeSeconds / Constants.HOUR_DIV
//            }
//        }
//    }
}