package com.example.fourteenthpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.fourteenthpractice.R
import com.example.fourteenthpractice.data.UserResourceParser
import com.example.fourteenthpractice.databinding.FragmentXmlBinding
import org.xmlpull.v1.XmlPullParser

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val xpp: XmlPullParser = resources.getXml(R.xml.users)
        val parser = UserResourceParser()
        if (parser.parse(xpp)) {
            val userList = parser.users
            binding.listView.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, userList)
        }
    }

}