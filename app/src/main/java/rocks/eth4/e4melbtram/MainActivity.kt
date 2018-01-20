package rocks.eth4.e4melbtram

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import rocks.eth4.e4melbtram.objects.Model
import rocks.eth4.e4melbtram.utils.PtvApi
import rocks.eth4.e4melbtram.utils.PtvSignatureUtil

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


        val btn_get_route_types: Button by lazy { findViewById<Button>(R.id.btn_get_route_types) }
//        val btn_get_route_types: Button by lazy { btn_get_route_types }
        btn_get_route_types.clicks()
                .doOnNext { getRouteTypes() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d(TAG, "clicked") },
                        { error -> Log.e(TAG, error.message) }
                )

        btn_get_route_types.performClick()

    }

    private fun getRouteTypes(): Model.RouteTypes {
        var routeTypes:Model.RouteTypes? = null

        disposable = ptvApi.getRouteTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    result -> routeTypes = result
                    Log.d(TAG, result.toString()) },
                        { error -> Log.e(TAG, error.message) }
                )
        return routeTypes as Model.RouteTypes
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
