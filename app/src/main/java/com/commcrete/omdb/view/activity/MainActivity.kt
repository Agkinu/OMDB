package com.commcrete.omdb.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.commcrete.omdb.R
import com.commcrete.omdb.retrofit.RetrofitInstance
import com.commcrete.omdb.room.database.MovieDatabase
import com.commcrete.omdb.room.repository.MovieRepository
import com.commcrete.omdb.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var repository: MovieRepository
    lateinit var viewModelFactory: MovieViewModel.MovieViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val api = RetrofitInstance.api
        val dao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(api, dao)

        viewModelFactory = MovieViewModel.MovieViewModelFactory(repository, application)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            ?: return

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
