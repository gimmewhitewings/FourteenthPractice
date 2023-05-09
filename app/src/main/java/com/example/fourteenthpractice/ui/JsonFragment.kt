package com.example.fourteenthpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fourteenthpractice.databinding.FragmentJsonBinding

class JsonFragment : Fragment() {

    private lateinit var binding: FragmentJsonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJsonBinding.inflate(layoutInflater)
        return binding.root
    }


}