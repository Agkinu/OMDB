package com.commcrete.omdb.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberAsyncImagePainter
import com.commcrete.omdb.databinding.FragmentDetailsBinding
import com.commcrete.omdb.model.MovieItem
import com.commcrete.omdb.view.activity.MainActivity
import com.commcrete.omdb.viewmodel.MovieViewModel

class DetailsFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.composeView.setContent {

            val selectedMovie by viewModel.selectedMovie.collectAsState()

            selectedMovie?.let {
                MovieDetailsScreen(it)
            } ?: run {
                Text("Loading...", modifier = Modifier.fillMaxSize())
            }
        }
        return binding.root

    }
}
@Composable
fun MovieDetailsScreen(movie: MovieItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = movie.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = "Release Year: ${movie.year}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        DetailsRow(label = "Rated", value = movie.rated)
        DetailsRow(label = "Released", value = movie.released)
        DetailsRow(label = "Runtime", value = movie.runtime)
        DetailsRow(label = "Genre", value = movie.genre)
        DetailsRow(label = "Director", value = movie.director)
        DetailsRow(label = "Writer", value = movie.writer)
        DetailsRow(label = "Actors", value = movie.actors)
        DetailsRow(label = "Plot", value = movie.plot)
        DetailsRow(label = "Language", value = movie.language)
        DetailsRow(label = "Country", value = movie.country)
        DetailsRow(label = "Awards", value = movie.awards)
        DetailsRow(label = "Metascore", value = movie.metascore)
        DetailsRow(label = "IMDb Rating", value = movie.imdbRating)
        DetailsRow(label = "IMDb Votes", value = movie.imdbVotes)
        DetailsRow(label = "Box Office", value = movie.boxOffice)
        DetailsRow(label = "Production", value = movie.production)
        DetailsRow(label = "Website", value = movie.website)
    }
}

@Composable
fun DetailsRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = value ?: "N/A", style = MaterialTheme.typography.bodyLarge)
    }
}
