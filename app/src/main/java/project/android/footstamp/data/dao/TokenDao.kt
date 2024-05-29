package project.android.footstamp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.android.footstamp.data.model.LoginToken

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setToken(token: LoginToken)

    @Query("SELECT * FROM token")
    fun getToken(): LoginToken?

    @Query("DELETE FROM token")
    fun deleteToken()
}