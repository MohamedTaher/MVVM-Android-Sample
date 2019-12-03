package com.taher.footballdata.ui.fixtureslist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.taher.footballdata.R
import com.taher.footballdata.common.ViewModelProviderFactory
import com.taher.footballdata.data.model.Match
import com.taher.footballdata.databinding.FragmentFixturesListBinding
import com.taher.footballdata.ui.fixtureslist.adapter.SectionFixturesListAdapter
import com.taher.footballdata.utilities.DataWrapper
import com.taher.footballdata.utilities.extension.showSnackBar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FixturesListFragment: Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var binding: FragmentFixturesListBinding
    private lateinit var viewModel: FixturesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setTitle(R.string.recent_fixtures)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentFixturesListBinding.inflate(inflater, container, false)
        binding.hasFixtures = false
        binding.isLoading = true

        val viewModelFactory: ViewModelProviderFactory by kodein.instance()
        viewModel = viewModelFactory.create(FixturesListViewModel::class.java)
        initUi()

        return binding.root
    }

    private fun initUi() {
        val adapter = SectionFixturesListAdapter(requireContext())
        binding.fixturesList.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: SectionFixturesListAdapter) {
        viewModel.fixturesList.observe(this, Observer <DataWrapper<List<Match>>>{ matches ->
            binding.isLoading = false

            if (matches.status == DataWrapper.Status.ERROR) {
                val errorMessage = matches.message ?: getString(R.string.internet_connection_error)
                view?.showSnackBar(requireContext(), errorMessage, R.string.retry) {
                    binding.isLoading = true
                    viewModel.getRemoteFixtures()
                }
                return@Observer
            }

            val matchData = matches.data ?: listOf()
            binding.hasFixtures = (matchData.count() > 0)
            adapter.submitList(matchData)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fixtures_list_filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.recent_fixtures -> {
                Log.i("item id ", item.itemId.toString() + "")
                if (viewModel.filterNumber.value != FixturesListViewModel.REMOTE_FIXTURES) {
                    binding.isLoading = true
                    activity?.setTitle(R.string.recent_fixtures)
                    viewModel.getRemoteFixtures()
                }
                true
            }
            R.id.favorite_fixtures -> {
                Log.i("item id ", item.itemId.toString() + "")
                if (viewModel.filterNumber.value != FixturesListViewModel.FAVORITE_FIXTURES) {
                    binding.isLoading = true
                    activity?.setTitle(R.string.favorite_fixtures)
                    viewModel.getFavoriteFixtures()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}