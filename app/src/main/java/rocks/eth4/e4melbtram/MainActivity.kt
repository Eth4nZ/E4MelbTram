package rocks.eth4.e4melbtram

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import rocks.eth4.e4melbtram.objects.Model
import rocks.eth4.e4melbtram.utils.PtvApi

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    val ptvApi by lazy {
        PtvApi.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val btn_search: Button by lazy { findViewById<Button>(R.id.btn_search) }
        btn_search.clicks()
                .doOnNext {
                    val searchBar = findViewById<EditText>(R.id.et_search_bar)
                    searchPTV(searchTerm = searchBar.text.toString(), latitude = (-37).toFloat(), longitude = 144.toFloat(), maxDistance = 300.toFloat()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d(TAG, "clicked") },
                        { error -> Log.e(TAG, error.message) }
                )

        val btn_get_route_types: Button by lazy { findViewById<Button>(R.id.btn_get_route_types) }
        btn_get_route_types.clicks()
                .doOnNext {
                    getRouteTypes() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d(TAG, "clicked") },
                        { error -> Log.e(TAG, error.message) }
                )

//        btn_get_route_types.performClick()

    }

    private fun getRouteTypes(){
        val tv_show_info = findViewById<TextView>(R.id.tv_show_info)
        disposable = ptvApi.getRouteTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            tv_show_info.text = result.toString()
                            Log.d(TAG, result.toString())},
                        { error ->
                            tv_show_info.text = error.message
                            Log.e(TAG, error.message) }
                )
    }

    private fun searchPTV(
            searchTerm: String,
            routeTypes: List<Int>? = null,
            latitude: Float? = null,
            longitude: Float? = null,
            maxDistance: Float? = null,
            includeAddresses: Boolean? = null, //	Placeholder for future development; currently unavailable
            includeOutlets: Boolean? = null //	Indicates if outlets will be returned in response (default = true)
    ) {
        val tv_show_info = findViewById<TextView>(R.id.tv_show_info)
        disposable = ptvApi.search(searchTerm, routeTypes, latitude, longitude, maxDistance, includeAddresses, includeOutlets)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            tv_show_info.text = result.toString()
                            Log.d(TAG, result.toString()) },
                        { error ->
                            tv_show_info.text = error.message
                            Log.e(TAG, error.message) }
                )
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
