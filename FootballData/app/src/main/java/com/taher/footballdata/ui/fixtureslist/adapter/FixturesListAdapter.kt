package com.taher.footballdata.ui.fixtureslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.taher.footballdata.R
import com.taher.footballdata.data.datarepository.DataRepository
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.databinding.ListItemFlixtureBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FixturesListAdapter(mContext: Context): RecyclerView.Adapter<FixturesListAdapter.FixtureViewHolder>(),
    KodeinAware {

    override val kodein by kodein(mContext)
    private val dataRepository: DataRepository by instance()

    private var items = ArrayList<Match>()

    fun submitList(items: List<Match>) {
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_flixture, parent, false
            )
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class FixtureViewHolder(private val binding: ListItemFlixtureBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                val match = items[position]
                viewModel = FixtureItemViewModel(dataRepository, match)
                executePendingBindings()
            }
        }
    }

}