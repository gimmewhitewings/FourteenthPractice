package com.example.fourteenthpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fourteenthpractice.databinding.FragmentXmlBinding

class XmlFragment : Fragment() {


    private lateinit var binding: FragmentXmlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentXmlBinding.inflate(layoutInflater)
        return binding.root
    }

}