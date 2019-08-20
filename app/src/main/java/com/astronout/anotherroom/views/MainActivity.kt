package com.astronout.anotherroom.views

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.astronout.anotherroom.AdapterOnClickListener
import com.astronout.anotherroom.R
import com.astronout.anotherroom.adapter.Adapter
import com.astronout.anotherroom.databinding.ActivityMainBinding
import com.astronout.anotherroom.models.Task
import com.astronout.anotherroom.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), AdapterOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: Adapter
    private var listTask: MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = MainViewModel(this)

        setupBinding()

        viewModel.showAllTask()

        setupRecycler()

    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rv.layoutManager = layoutManager

        adapter = Adapter(this, listTask, this)
        binding.rv.adapter = adapter
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = viewModel

        viewModel.listTask.observe(this, Observer {
            onListDataChange(it!!)
        })
    }

    private fun onListDataChange(listTask: MutableList<Task>) {
        if (listTask.isNotEmpty()) {
            this.listTask.clear()
            for (i: Int in 0 until (listTask.size)) {
                this.listTask.add(listTask[i])
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onItemAdapterClickedEdit(task: Task) {
        viewModel.showUpdateTask(task, this)
    }

    override fun onItemAdapterClickedDelete(task: Task, position: Int) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Delete Data")
        alert.setMessage("Are you sure you want to delete this data?")
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            // continue with delete
            viewModel.deleteTask(task)
            adapter.removeItem(binding.rv, position)
            Toast.makeText(this@MainActivity, "Deleted!", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            // close dialog
            dialog.cancel()
        }
        alert.show()
    }

    override fun onUpdateCallback() {
        adapter.updateItem(binding.rv)
    }

}
