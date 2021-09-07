package com.cesoft.organizate3.ui.composables

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.cesoft.organizate3.R
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO: cuando pierde el foco se vuelve a llamar , da problemas porque se olvida de zoom etc ¿?
@Composable
fun MapCompo(value: LatLng, zoom: Float=8f, onValueChange: (LatLng) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(top = 8.dp)
            .size(400.dp)
    ) {
        android.util.Log.e("MapCompo", "--------------------------------------------- $value")
        val rememberMapView = rememberMapView()
        var rememberMarker: Marker? = remember { null }
        var rememberZoom: Float = remember { zoom }
        val markerTitle = stringResource(R.string.new_task)
        AndroidView({ rememberMapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = true
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(value, rememberZoom))
                map.setOnCameraMoveListener {
                    rememberZoom = map.cameraPosition.zoom
                    //onZoomChange(map.cameraPosition.zoom)
                }
                map.setOnMapClickListener { latLng: LatLng ->
                    onValueChange(latLng)
                    //
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    val markerOptions = MarkerOptions()
                        .title(markerTitle)
                        .position(latLng)
                    rememberMarker?.remove()
                    rememberMarker = map.addMarker(markerOptions)
                }
            }
        }
    }
}

@Composable
private fun rememberMapView(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, mapView) {
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
