package com.commcrete.omdb.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.commcrete.omdb.R
import com.commcrete.omdb.databinding.FragmentSearchBinding
import com.commcrete.omdb.databinding.PopupMoviePreviewBinding
import com.commcrete.omdb.model.MovieItem
import com.commcrete.omdb.utils.CustomSnapHelper
import com.commcrete.omdb.utils.DividerItemDecoration
import com.commcrete.omdb.view.activity.MainActivity
import com.commcrete.omdb.view.adapters.MovieAdapter
import com.commcrete.omdb.viewmodel.MovieViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private val viewModel: MovieViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter { selectedMovie ->
            centerItemAndShowPopup(selectedMovie)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val snapHelper = CustomSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), R.drawable.divider))

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable { viewModel.setSearchQuery(s.toString()) }
                searchHandler.postDelayed(searchRunnable!!, 500) // Wait 500ms
            }
        })

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
    }

    private fun centerItemAndShowPopup(movie: MovieItem) {
        val position = adapter.currentList.indexOf(movie)

        if (position != -1) {
            binding.recyclerView.smoothScrollToPosition(position)
        }

        showMoviePopup(movie)
    }

    private fun showMoviePopup(movie: MovieItem) {
        val popupBinding = PopupMoviePreviewBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(popupBinding.root).create()

        popupBinding.movieTitle.text = movie.title
        popupBinding.movieYear.text = "Year: ${movie.year}"
        popupBinding.moviePlot.text = movie.plot ?: "No description available"

        Glide.with(popupBinding.moviePoster.context)
            .load(movie.poster)
            .into(popupBinding.moviePoster)

        popupBinding.btnMoreDetails.setOnClickListener {
            viewModel.selectMovie(movie)
            findNavController().navigate(R.id.detailsFragment)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
