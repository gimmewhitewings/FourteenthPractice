package com.example.fourteenthpractice.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fourteenthpractice.data.JSONHelper
import com.example.fourteenthpractice.databinding.FragmentJsonBinding
import com.example.fourteenthpractice.model.User


class JsonFragment : Fragment() {

    private lateinit var binding: FragmentJsonBinding
    private lateinit var adapter: ArrayAdapter<User>
    private lateinit var listView: ListView
    private var users: MutableList<User> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJsonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, users)
        listView = binding.list
        listView.adapter = adapter

        binding.apply {
            addButton.setOnClickListener { addUser() }
            saveButton.setOnClickListener { save() }
            openButton.setOnClickListener { open() }
        }
    }


    private fun addUser() {
        val name: String = binding.nameText.editText!!.text.toString()
        val age: Int = binding.ageText.editText!!.text.toString().toInt()
        val user = User(name, age)
        users.add(user)
        adapter.notifyDataSetChanged()
    }

    private fun save() {
        val result: Boolean = JSONHelper.exportToJSON(requireContext(), users)
        if (result) {
            Toast.makeText(context, "Данные сохранены", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Не удалось сохранить данные", Toast.LENGTH_LONG).show()
        }
    }

    private fun open() {
        users = JSONHelper.importFromJSON(requireContext()) as MutableList<User>
        if (users != null) {
            adapter =
                ArrayAdapter<User>(requireContext(), android.R.layout.simple_list_item_1, users)
            listView.adapter = adapter
            Toast.makeText(context, "Данные восстановлены", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Не удалось открыть данные", Toast.LENGTH_LONG).show()
        }
    }
}