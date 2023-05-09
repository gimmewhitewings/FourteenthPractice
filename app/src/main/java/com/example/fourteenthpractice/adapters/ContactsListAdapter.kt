package com.example.fourteenthpractice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fourteenthpractice.databinding.ContactItemBinding
import com.example.fourteenthpractice.model.Contact

class ContactsListAdapter(
    private var values: List<Contact>
) : RecyclerView.Adapter<ContactsListAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.contactNameTextView.text = values[adapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return values.size
    }

    fun updateData(contacts: List<Contact>) {
        values = contacts
        notifyDataSetChanged()
    }

}
