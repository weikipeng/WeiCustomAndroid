package com.pengjunwei.android.custom.demo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pengjunwei.android.custom.demo.recyclerview.TestCustomRecyclerViewActivity
import com.pengjunwei.android.custom.demo.shadow.TestShadowActivity

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {
    var buttonTestMiuiLifeCycle: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTestMiuiLifeCycle = view?.findViewById(R.id.btnTestMiuiLifeCycle)
        buttonTestMiuiLifeCycle?.setOnClickListener(View.OnClickListener {
//            val miuiIntent = Intent(context,MIUIActivity1::class.java)
//            val miuiIntent = Intent(context,TestEditorActivity::class.java)
            val miuiIntent = Intent(context, TestShadowActivity::class.java)
            startActivity(miuiIntent)
        })
    }
}