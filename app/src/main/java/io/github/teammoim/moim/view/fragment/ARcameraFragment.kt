package io.github.teammoim.moim.view.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.*
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.TransformableNode
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.ar.core.*
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.ux.ArFragment
import io.github.teammoim.moim.App
import org.jetbrains.anko.toast
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.assets.RenderableSource
import uk.co.appoly.arcorelocation.LocationMarker
import uk.co.appoly.arcorelocation.LocationScene
import uk.co.appoly.arcorelocation.rendering.LocationNode
import uk.co.appoly.arcorelocation.rendering.LocationNodeRender
import uk.co.appoly.arcorelocation.utils.ARLocationPermissionHelper
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


class ARcameraFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar_camera, container, false)
    }

    private var arFragment: ArFragment? = null
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var arSceneView: ArSceneView
    private var locationScene: LocationScene? = null
    private var exampleLayoutRenderable: ViewRenderable? = null
    private var hasFinishedLoading = false


    private val GLTF_ASSET = "https://github.com/teammoim/ar-modeling/raw/master/Marker.gltf"


    private lateinit var testViewRenderable: ViewRenderable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arSceneView = view.findViewById(R.id.ux_fragment)
        view.viewTreeObserver.addOnWindowFocusChangeListener {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        }
        super.onViewCreated(view, savedInstanceState)
//        setupView()

                val exampleLayout = ViewRenderable.builder()
                        .setView(this.activity, R.layout.widget_ar)
                        .build()

                val andy = ModelRenderable.builder()
                        .setSource(this.activity, R.raw.andy)
                        .build()

                CompletableFuture.allOf(exampleLayout,
                        andy).handle { _, throwable ->
                    if (throwable != null) {
                        return@handle null
                    }

                    try {
                        exampleLayoutRenderable = exampleLayout.get()
                andyRenderable = andy.get()
                hasFinishedLoading = true

            } catch (ex: InterruptedException) {
            } catch (ex: ExecutionException) {
            }

            null
        }

        @Suppress("DEPRECATION")
        arSceneView
                .scene
                .setOnUpdateListener { _ ->
                    if (!hasFinishedLoading) {
                        return@setOnUpdateListener
                    }

                    if (locationScene == null) {

                        // If our locationScene object hasn't been setup yet, this is a good time to do it
                        // We know that here, the AR components have been initiated.
                        locationScene = LocationScene(this.activity, this.activity, arSceneView)

                        // Now lets create our location markers.
                        // First, a layout
                        val layoutLocationMarker = LocationMarker(
                                126.99769700000002,
                                37.6096409,
                                getExampleView()
                        )

                        // An example "onRender" event, called every frame
                        // Updates the layout with the markers distance
                        layoutLocationMarker.renderEvent = LocationNodeRender { node ->
                            val eView = exampleLayoutRenderable?.view
                            val distanceTextView = eView?.findViewById<TextView>(R.id.infoCard)
                            distanceTextView?.text = node.distance.toString() + "M"
                        }
                        // Adding the marker
                        locationScene?.mLocationMarkers?.add(layoutLocationMarker)

                        // Adding a simple location marker of a 3D model
                        locationScene?.mLocationMarkers?.add(
                                LocationMarker(
                                        -0.119677,
                                        51.478494,
                                        getAndy()))
                    }

                    val frame = arSceneView.arFrame ?: return@setOnUpdateListener

                    if (frame!!.camera.trackingState != TrackingState.TRACKING) {
                        return@setOnUpdateListener
                    }

                    locationScene?.processFrame(frame)


                }

//        ModelRenderable.builder()
//                .setSource(this.activity, R.raw.andy)
//                .build()
//                .thenAccept { renderable -> andyRenderable = renderable }
//                .exceptionally {
//                    App.INSTANCE.toast("Unable to load andy renderable")
//                    null
//                }
//
//        ModelRenderable.builder()
//                .setSource(this.activity, RenderableSource.builder().setSource(
//                        this.activity,
//                        Uri.parse(GLTF_ASSET),
//                        RenderableSource.SourceType.GLTF2).build())
//                .setRegistryId(GLTF_ASSET)
//                .build()
//                .thenAccept { renderable -> andyRenderable = renderable }

//        ViewRenderable.builder()
//                .setView(this.activity, R.layout.widget_ar)
//                .build()
//                .thenAccept { renderable ->
//                    testViewRenderable = renderable
//                    val button: Button = testViewRenderable.view.findViewById(R.id.informationButton) as Button
//                    button.setOnClickListener {
//                        val bottomSheetDialogFragment = EventInformationFragment()
//                        bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
//                    }
//                }




//        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
//            val anchor = hitResult.createAnchor()
//            val anchorNode = AnchorNode(anchor)
//            anchorNode.setParent(arFragment?.arSceneView?.scene)
//            val andy = TransformableNode(arFragment?.transformationSystem)
//            andy.setParent(anchorNode)
//            andy.renderable = andyRenderable
//            andy.select()
//            val widget = TransformableNode(arFragment?.transformationSystem)
//            widget.setParent(andy)
//            widget.renderable = testViewRenderable
//            widget.localPosition = Vector3(0.0f, 0.25f, 0.0f)
//            widget.select()
//
//        }
    }

//    private fun setupView() {
//        arFragment = childFragmentManager.findFragmentById(R.id.ux_fragment) as? ArFragment
//
//    }

    /**
     * Example node of a layout
     *
     * @return
     */
    private fun getExampleView(): Node {
        val base = Node()
        base.renderable = exampleLayoutRenderable
        val c = this
        // Add  listeners etc here
        val eView = exampleLayoutRenderable?.view
        eView?.setOnTouchListener { v, event ->
            return@setOnTouchListener false
        }

        return base
    }

    /***
     * Example Node of a 3D model
     *
     * @return
     */
    private fun getAndy(): Node {
        val base = Node()
        base.renderable = andyRenderable
        val c = this
        base.setOnTapListener { v, event ->

        }
        return base
    }

    @Throws(UnavailableException::class)
    private fun createArSession(activity: Activity): Session? {
        var session: Session? = null
        // if we have the camera permission, create the session
        if (ARLocationPermissionHelper.hasPermission(activity)) {
            session = Session(activity)
            // IMPORTANT!!!  ArSceneView needs to use the non-blocking update mode.
            val config = Config(session)
            config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            session.configure(config)
        }
        return session
    }

    override fun onResume() {
        super.onResume()
        if (locationScene != null) {
            locationScene?.resume()
        }
        if (arSceneView.session == null) {
            try {
                val session = createArSession(this.activity as Activity)
                if (session == null) {
                    return
                } else {
                    arSceneView.setupSession(session)
                }
            } catch (e: UnavailableException) {
            }

        }

        try {
            arSceneView.resume()
        } catch (ex: CameraNotAvailableException) {
            return
        }
    }


    /**
     * Make sure we call locationScene.pause();
     */
    override fun onPause() {
        super.onPause()

        if (locationScene != null) {
            locationScene?.pause()
        }

        arSceneView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        arSceneView.destroy()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, @NonNull permissions: Array<String>, @NonNull results: IntArray) {
        if (!ARLocationPermissionHelper.hasPermission(this.activity)) {
            if (!ARLocationPermissionHelper.shouldShowRequestPermissionRationale(this.activity)) {
                // Permission denied with checking "Do not ask again".
                ARLocationPermissionHelper.launchPermissionSettings(this.activity)
            } else {
            }
        }
    }
}