package com.santosfabi.catgallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.santosfabi.catgallery.R
import com.santosfabi.catgallery.data.model.PictureDetails
import com.santosfabi.catgallery.databinding.FragmentGalleryBinding
import com.santosfabi.catgallery.domain.model.UiState
import com.santosfabi.catgallery.ui.gallery.adapter.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel: GalleryViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupViewsListeners()
    }

    private fun setupViewsListeners() {
        with(binding) {
            binding.srlReload.setOnRefreshListener {
                viewModel.fetchPictures()
            }

            btnTryAgain.setOnClickListener {
                viewModel.fetchPictures()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = GalleryAdapter()
        binding.rvGallery.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvGallery.layoutManager = layoutManager
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            rvGallery.visibility = View.GONE
            tvErrorFeedback.visibility = View.GONE
            srlReload.isRefreshing = false
            btnTryAgain.visibility = View.GONE
        }
    }

    private fun showSuccess(data: List<PictureDetails>) {
        with(binding) {
            pbLoading.visibility = View.GONE
            rvGallery.visibility = View.VISIBLE
            tvErrorFeedback.visibility = View.GONE
            srlReload.isRefreshing = false
            adapter.submitList(data)
            btnTryAgain.visibility = View.GONE
        }
    }

    private fun showError(errorMsg: String?) {
        with(binding) {
            pbLoading.visibility = View.GONE
            rvGallery.visibility = View.GONE
            tvErrorFeedback.visibility = View.VISIBLE
            btnTryAgain.visibility = View.VISIBLE
            tvErrorFeedback.text = errorMsg ?: "An unexpected error occurred"
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.galleryState.collect { state ->
                when (state) {
                    is UiState.Loading -> showLoading()

                    is UiState.Success -> showSuccess(state.data)

                    is UiState.Failure -> showError(state.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
