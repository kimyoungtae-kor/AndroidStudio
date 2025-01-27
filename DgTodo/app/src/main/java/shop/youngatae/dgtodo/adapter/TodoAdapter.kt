package shop.youngatae.dgtodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shop.youngatae.dgtodo.R
import shop.youngatae.dgtodo.data.Todo

class TodoAdapter(private val onTodoClick: (Todo) -> Unit)
    : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    private val todos = mutableListOf<Todo>()
    fun submitList(newTodos : List<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int = todos.size
    //

    inner class  TodoViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        private val title = view.findViewById<TextView>(R.id.textView)
        fun bind(todo: Todo){
            checkBox.isChecked = todo.completed
            title.text = todo.title
            itemView.setOnClickListener { onTodoClick(todo) }
        }
    }
}