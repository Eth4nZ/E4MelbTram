package rocks.eth4.e4melbtram.utils

import android.util.Log
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rocks.eth4.e4melbtram.objects.Model


/**
 * Created by eth4 on 18/1/18.
 */

public interface PtvApi {


    @GET("v3/route_types")
    fun getRouteTypes(): Observable<Model.RouteTypesResponse>

    @GET("v3/search/{search_term}")
    fun search(@Path("search_term") searchTerm: String,
               @Query("route_types") routeTypes: List<Int>? = null,
               @Query("latitude") latitude: Float? = null,
               @Query("longitude") longitude: Float? = null,
               @Query("max_distance") maxDistance: Float? = null,
               @Query("include_addresses") includeAddresses: Boolean? = null, //	Placeholder for future development; currently unavailable
               @Query("include_outlets") includeOutlets: Boolean? = null //	Indicates if outlets will be returned in response (default = true)
               ): Observable<Model.SearchResponse>


    companion object {
        val TAG = PtvApi::class.java.simpleName
        fun create(): PtvApi {
            val client = OkHttpClient().newBuilder().addInterceptor({ chain ->
                var request = chain.request()
                val uri = request.url().toString().removePrefix(PtvSignatureUtil.BASE_URL)
                val url = request.url().newBuilder()
                        .addQueryParameter(PtvSignatureUtil.PTV_DEVELOPER_ID_QUERY_NAME, PtvSignatureUtil.DEVELOPER_ID.toString())
                        .addQueryParameter(PtvSignatureUtil.PTV_SIGNATURE_QUERY_NAME, PtvSignatureUtil.calculateSignature(uri))
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }).build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(PtvSignatureUtil.BASE_URL)
                    .client(client)
                    .build()

            return retrofit.create(PtvApi::class.java)
        }
    }

}
