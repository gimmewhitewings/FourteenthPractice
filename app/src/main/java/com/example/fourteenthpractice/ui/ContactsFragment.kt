package com.example.fourteenthpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fourteenthpractice.adapters.ContactsListAdapter
import com.example.fourteenthpractice.data.ContactManager
import com.example.fourteenthpractice.databinding.FragmentContactsBinding
import com.example.fourteenthpractice.model.Contact


class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactManager: ContactManager
    private lateinit var contactListAdapter: ContactsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contactManager = ContactManager(requireContext())

        // создаем адаптер и устанавливаем его в RecyclerView
        contactListAdapter = ContactsListAdapter(contactManager.getAllContacts())
        binding.contactsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactListAdapter
        }

        binding.floatingActionButton.setOnClickListener {
            val newContact = Contact(binding.contactName.editText!!.text.toString())
            contactManager.addContact(newContact)

            // обновляем данные адаптера
            contactListAdapter.updateData(contactManager.getAllContacts())
        }
    }
}