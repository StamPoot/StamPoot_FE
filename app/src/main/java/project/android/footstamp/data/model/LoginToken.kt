package project.android.footstamp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
class LoginToken(
    @ColumnInfo(name = "provider") val provider: Provider,
    @ColumnInfo(name = "date") val date: String
) {
    @PrimaryKey(autoGenerate = false)
    var token: String = ""

    fun insertToken(token: String) {
        this@LoginToken.token = token
    }
}