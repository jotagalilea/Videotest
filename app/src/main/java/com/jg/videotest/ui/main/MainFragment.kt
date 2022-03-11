package com.jg.videotest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jg.videotest.R
import com.jg.videotest.databinding.FragmentMainBinding
import com.jg.videotest.model.Video
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.adapter.CategoriesAdapter
import com.jg.videotest.ui.adapter.VideosAdapter
import com.jg.videotest.ui.base.BaseFragment
import com.jg.videotest.ui.common.DataStatus
import com.jg.videotest.ui.common.DataStatus.*
import com.jg.videotest.ui.hide
import com.jg.videotest.ui.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : BaseFragment(), VideosAdapter.VideoClickListener {

    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        binding.rvCategories.layoutManager = LinearLayoutManager(this.context)
        binding.rvCategories.adapter = CategoriesAdapter(this)
    }

    override fun initializeState(savedInstanceState: Bundle?) {
        collectLatestLifecycleFlow(viewModel.getContentStateFlow()) { data ->
            handleDataStatus(data)
        }
        (binding.rvCategories.adapter as CategoriesAdapter).lastUnfolded = viewModel.lastUnfoldedSavedState
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        viewModel.fetchContent(this.requireContext())
    }


    private fun <T> handleDataStatus(status: DataStatus<T>) = CoroutineScope(Dispatchers.Main).launch {
        when (status){
            is Loading -> showLoading()
            is Empty -> showEmpty(status.emptyMessage)
            is Error -> showError(status.errorMessage)
            is Success -> showSuccess(status.data)
        }
    }

    private fun showLoading(){
        binding.apply {
            rvCategories.hide()
            tvInfo.hide()
            pbLoader.show()
        }
    }

    private fun showEmpty(message: String?) {
        binding.apply {
            rvCategories.hide()
            tvInfo.text = message ?: ""
            tvInfo.show()
            pbLoader.hide()
        }
    }

    private fun showError(message: String?) {
        binding.apply {
            rvCategories.hide()
            tvInfo.text = message ?: ""
            tvInfo.show()
            pbLoader.hide()
        }
    }

    private fun <T> showSuccess(data: T) {
        binding.apply {
            tvInfo.hide()
            pbLoader.hide()
            rvCategories.show()
            val items = data as List<*>
            items.filterIsInstance<ContentUi>().also {
                (rvCategories.adapter as CategoriesAdapter).updateItems(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onVideoClick(video: Video) {
        Log.d("Lanzando vídeo...", video.name)
        viewModel.lastUnfoldedSavedState = (binding.rvCategories.adapter as CategoriesAdapter).lastUnfolded
        val bundle = bundleOf("video" to video)
        (activity as MainActivity).navController.navigate(
            R.id.action_mainFragment_to_playerFragment,
            bundle
        )
    }

}


fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collector)
        }
    }
}