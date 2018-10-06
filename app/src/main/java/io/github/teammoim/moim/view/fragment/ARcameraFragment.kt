package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.*
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.TransformableNode
import android.widget.Button
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment
import io.github.teammoim.moim.App
import org.jetbrains.anko.toast
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.math.Vector3


class ARcameraFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar_camera, container, false)
    }
    private var arFragment: ArFragment? = null
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var testViewRenderable: ViewRenderable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.viewTreeObserver.addOnWindowFocusChangeListener {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        }


        super.onViewCreated(view, savedInstanceState)
        setupView()
        ModelRenderable.builder()
                .setSource(this.activity, R.raw.andy)
                .build()
                .thenAccept { renderable -> andyRenderable = renderable }
                .exceptionally {
                    App.INSTANCE.toast("Unable to load andy renderable")
                    null
                }

        ViewRenderable.builder()
                .setView(this.activity, R.layout.widget_ar)
                .build()
                .thenAccept { renderable ->
                    testViewRenderable = renderable
                    val button : Button = testViewRenderable.view.findViewById(R.id.informationButton) as Button
                    button.setOnClickListener {
                        val bottomSheetDialogFragment = EventInformationFragment()
                        bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
                    }
                }

        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment?.arSceneView?.scene)
            val andy = TransformableNode(arFragment?.transformationSystem)
            andy.setParent(anchorNode)
            andy.renderable = andyRenderable
            andy.select()
            val widget = TransformableNode(arFragment?.transformationSystem)
            widget.setParent(andy)
            widget.renderable = testViewRenderable
            widget.localPosition = Vector3(0.0f, 0.25f, 0.0f)
            widget.select()

        }
    }

    private fun setupView() {
        arFragment = childFragmentManager.findFragmentById(R.id.ux_fragment) as? ArFragment

    }


}