package shop.youngatae.dgtodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo:Todo)
    @Query("select * from tbl_todo")
    suspend fun getAllTodos():List<Todo>
    @Update
    suspend fun update(todo:Todo)
    @Delete
    suspend fun delete(todo: Todo)
}