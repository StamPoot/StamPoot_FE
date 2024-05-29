package project.android.footstamp.data.data_source

import project.android.footstamp.data.dto.response.auth.AuthToken
import project.android.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService : BaseService {

    @GET("/login/oauth2/code/{provider}")
    suspend fun authLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>
}