package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentSuccessBinding
import com.kader.kotlin_hangman.ui.game.GameFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSuccessAction()
    }

    private fun buttonSuccessAction() {
        binding.buttonSucces.setOnClickListener {
            val gameFragment = GameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, gameFragment)
            transaction.commit()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}