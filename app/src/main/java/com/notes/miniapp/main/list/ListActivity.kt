package com.notes.miniapp.main.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.notes.miniapp.databinding.ActivityListBinding
import com.notes.miniapp.extension.viewBinding
import com.notes.miniapp.main.adapter.NotesAdapter
import com.notes.miniapp.main.base.BaseActivity
import com.notes.miniapp.main.common.ProfileActivity
import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.utils.DialogInputConfirm
import com.notes.miniapp.utils.ResponseData

class ListActivity : BaseActivity() {

    private val binding by viewBinding(ActivityListBinding::inflate)
    private val viewModel: ListViewModel by viewModels()
    private val notesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        dataObserver()
    }

    private fun initView() {
        binding.txtBack.setOnClickListener {
            finish()
        }

        binding.txtProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.fab.setOnClickListener {
            showInputDialog()
        }

        notesAdapter.onDeleteItem = { model, position ->
            removeData(model, position)
        }

        binding.recycleViewText.adapter = notesAdapter
    }

    private fun dataObserver() {
        viewModel.listNotesLiveData.observe(this) {
            when (it) {
                is ResponseData.Loading -> {
                    showLoading(true)
                }
                is ResponseData.Success -> {
                    showLoading(false)
                    if (it.data.isEmpty()) {
                        Toast.makeText(ListActivity@ this, "Empty List", Toast.LENGTH_SHORT).show()
                    }
                    notesAdapter.updateData(it.data)
                }
                is ResponseData.Error -> {
                    showLoading(false)
                }
            }
        }
        viewModel.getList()
    }


    private fun showInputDialog() {
        val dialog = DialogInputConfirm()
        dialog.onPositiveClick = { text -> addData(text) }
        dialog.show(supportFragmentManager, "TAG")
    }

    private fun addData(text: String) {
        viewModel.addNote(text)
    }

    private fun removeData(noteModel: NoteModel, position: Int) {
        viewModel.removeNote(noteModel)
    }
}
