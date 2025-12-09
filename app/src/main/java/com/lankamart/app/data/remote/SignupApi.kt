import com.lankamart.app.data.remote.SignupRequest
import com.lankamart.app.data.remote.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {
    @POST("register_user.php")
    suspend fun registerUser(@Body request: SignupRequest): SignupResponse
}
