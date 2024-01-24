package com.example.galleryapp.network
import com.example.galleryapp.ui.screens.account.UserResponse
import com.example.galleryapp.ui.screens.home.Art
import com.example.galleryapp.ui.screens.home.ArtsResponse
import com.example.galleryapp.ui.screens.itemDetails.ItemDetailsResponse
import com.example.galleryapp.ui.screens.register.User
import com.example.galleryapp.utils.navigation.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val BASE_URl = "http://10.0.2.2:5032/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URl)
    .build()

data class DefaultResponse(val isSuccessful: Boolean, val responseMessage: String, val responseCode: String)
data class LoginUser (
    val password: String,
    val email: String,
)

interface GalleryApiService{
    @POST("api/v1/auth/register")
    fun registerUser(@Body user: User): Call<DefaultResponse>
    @POST("api/v1/auth/login")
    fun loginUser(@Body user: LoginUser): Call<TokenResponse>
    @GET("api/v1/auth/current-user")
    suspend fun getUserItems(@Header("Authorization") token: String?): Response<UserResponse>
    @GET("api/v1/art/all")
    suspend fun getAllArt(): Response<ArtsResponse>
    @GET("api/v1/auth/by-id/{Id}")
    suspend fun getItemDetails(): Response<ItemDetailsResponse>

}
object GalleryApi{
    val retrofitService: GalleryApiService by lazy {
        retrofit.create(GalleryApiService::class.java)
    }
}