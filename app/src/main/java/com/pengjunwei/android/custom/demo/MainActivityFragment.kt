package com.pengjunwei.android.custom.demo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.pengjunwei.android.custom.demo.contextmenu.ContextMenuActivity
import com.pengjunwei.android.custom.demo.recyclerview.TestCustomRecyclerViewActivity
import com.pengjunwei.android.custom.demo.shadow.TestShadowActivity

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {
    var buttonTestMiuiLifeCycle: Button? = null
    var deviceInfo : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTestMiuiLifeCycle = view.findViewById(R.id.btnAction)
        buttonTestMiuiLifeCycle?.setOnClickListener(View.OnClickListener {
//            val miuiIntent = Intent(context,MIUIActivity1::class.java)
//            val miuiIntent = Intent(context,TestEditorActivity::class.java)
//            val miuiIntent = Intent(context, TestShadowActivity::class.java)
            val miuiIntent = Intent(context, ContextMenuActivity::class.java)
            startActivity(miuiIntent)
        })

        //---
        var deviceInfoBuilder = StringBuilder()
        deviceInfoBuilder.append("Build.MANUFACTURER:").append(Build.MANUFACTURER)
        deviceInfoBuilder.append("\nBuild.BRAND:").append(Build.BRAND)
        deviceInfo = view.findViewById(R.id.deviceInfo)
        deviceInfo?.text = deviceInfoBuilder

    }
}