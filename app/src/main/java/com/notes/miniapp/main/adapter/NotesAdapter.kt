package com.notes.miniapp.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notes.miniapp.R
import com.notes.miniapp.databinding.ItemTextBinding
import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.utils.AppUtils

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.DataViewHolder>() {

    var data = arrayListOf<NoteModel>()
    var onDeleteItem: ((text: NoteModel, position: Int) -> Unit)? = null

    fun updateData(list: List<NoteModel>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val text = data[position]
        val context = holder.binding.root.context
        holder.bind(text)
        holder.binding.btnDelete.setOnClickListener {
            AppUtils.showAlertDialog(holder.itemView.context, context.getString(R.string.str_question_delete), {
                onDeleteItem?.invoke(text, position)
            }, {})
        }
    }

    override fun getItemCount(): Int = data.size

    class DataViewHolder(val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NoteModel) {
            binding.txtValue.text = data.model.note ?: ""
        }
    }
}
