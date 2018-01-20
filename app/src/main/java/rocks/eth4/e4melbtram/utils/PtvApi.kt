package rocks.eth4.e4melbtram.utils

import android.util.Log
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rocks.eth4.e4melbtram.objects.Model


/**
 * Created by eth4 on 18/1/18.
 */

public interface PtvApi {


    @GET("v3/route_types")
    fun getRouteTypes(): Observable<Model.RouteTypes>


    companion object {
        val TAG = PtvApi::class.java.simpleName
        fun create(): PtvApi {
            val client = OkHttpClient().newBuilder().addInterceptor({ chain ->
                var request = chain.request()
                val url = request.url().newBuilder()
                        .addQueryParameter(PtvSignatureUtil.PTV_DEVELOPER_ID_QUERY_NAME, PtvSignatureUtil.DEVELOPER_ID.toString())
                        .addQueryParameter(PtvSignatureUtil.PTV_SIGNATURE_QUERY_NAME, PtvSignatureUtil.calculateSignature(request.url().encodedPath()))
                        .build()
                request = request.newBuilder().url(url).build()
                Log.d(TAG, request.url().encodedPath())
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
