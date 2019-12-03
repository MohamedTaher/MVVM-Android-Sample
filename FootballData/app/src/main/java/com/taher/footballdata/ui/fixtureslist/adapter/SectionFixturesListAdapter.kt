package com.taher.footballdata.ui.fixtureslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.taher.footballdata.R
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.databinding.ListSectionFixturesBinding
import java.util.*
import kotlin.collections.ArrayList

class SectionFixturesListAdapter(private val mContext: Context): RecyclerView.Adapter<SectionFixturesListAdapter.SectionViewHolder>() {

    private var items: List<List<Match>> = ArrayList()

    fun submitList(items: List<Match>) {
        val itemsByDay = items.groupBy {
            val calendar = Calendar.getInstance()
            calendar.time = it.utcDate
            calendar.get(Calendar.DAY_OF_YEAR)
        }

        this.items = ArrayList(itemsByDay.values)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_section_fixtures, parent, false
            )
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class SectionViewHolder(private val binding: ListSectionFixturesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {

                val adapter = FixturesListAdapter(mContext)

                fixturesList.setHasFixedSize(true)
                fixturesList.adapter = adapter
                fixturesList.addItemDecoration(DividerItemDecoration(mContext, LinearLayout.VERTICAL))

                val matchesSection = items[position]
                matchesSection.let {
                    val date = it.first().utcDate
                    viewModel = SectionFixturesListViewModel(mContext, date)
                    adapter.submitList(matchesSection)
                    executePendingBindings()
                }
            }
        }
    }
}