package shop.youngatae.dgtodo.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import shop.youngatae.dgtodo.R
import shop.youngatae.dgtodo.data.Todo
import shop.youngatae.dgtodo.data.TodoDatabase

class AddTodoActivity:AppCompatActivity() {
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo_activity)
//db만들고 title가져오고
        todoDatabase = TodoDatabase.getDatabase(this)

        val inputTitle = findViewById<EditText>(R.id.inputTitle)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val title = inputTitle.text.toString()
            if (title.isNotBlank()) {
                lifecycleScope.launch {
                    val newTodo = Todo(title = title, completed = false)
                    todoDatabase.todoDao().insert(newTodo)

                    // 작업 완료 후 액티비티 종료
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

//        val title = findViewById<EditText>(R.id.textView)
//        val bu

    }
}