package com.kader.kotlin_hangman.ui.score


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kader.kotlin_hangman.databinding.FragmentScoresBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.adapter.ScoresAdapter
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment(override val screenName: String = ScreenName.SCORES_SCREEN) :
    BaseFragment<FragmentScoresBinding, ScoresViewModel>() {

    private lateinit var adapter: ScoresAdapter
    override fun initView() {

        adapter = ScoresAdapter()
        binding.scoreRecylerView.adapter = adapter
        binding.scoreRecylerView.layoutManager = LinearLayoutManager(context)

        viewModel.allScores.observe(viewLifecycleOwner) { scores ->
            scores.let { adapter.setScores(it) }
        }
    }

    override val viewModel by viewModels<ScoresViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScoresBinding = FragmentScoresBinding.inflate(inflater, container, false)
}