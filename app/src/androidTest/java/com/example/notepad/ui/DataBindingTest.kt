package com.example.notepad.ui

import android.util.Log
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.test.core.app.ApplicationProvider
import com.example.notepad.DataBinderMapperImpl
import com.example.notepad.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DataBindingTest {
    @Test
    fun test_gatLayoutId() {
        assertTrue(DataBinderMapperImpl().getLayoutId("layout/fragment_note_editable_0")!=0) {"Tag is not valid"}
        assertTrue(DataBinderMapperImpl().getLayoutId("layout/fragment_note_editable")==0) {"A layout exists for the given tag name"}
        assertTrue(DataBinderMapperImpl().getLayoutId(null)==0) {"Some error occured"}
    }

    @Test
    fun test_getDataBinder() {
        class MyDataBindingComponent: DataBindingComponent
        val dataBindComp = MyDataBindingComponent()
        val nullViewArray: Array<View>? = null
        val viewArray = arrayOf(View(ApplicationProvider.getApplicationContext()), View(ApplicationProvider.getApplicationContext()))
        assertThrows<RuntimeException> { DataBinderMapperImpl().getDataBinder(dataBindComp, viewArray, R.layout.fragment_note_editable) }
        assertEquals(null, DataBinderMapperImpl().getDataBinder(dataBindComp, arrayOf<View>(), R.layout.fragment_note_editable))
        assertEquals(null, DataBinderMapperImpl().getDataBinder(dataBindComp, nullViewArray, R.layout.fragment_note_editable))
        assertEquals(null,DataBinderMapperImpl().getDataBinder(dataBindComp, viewArray, 0) )
    }

    @Test
    @Disabled
    fun test_getDataBinderV2() {
        class MyDataBindingComponent: DataBindingComponent
        var view = View(ApplicationProvider.getApplicationContext())
        var viewWithTag = view.findViewById<View>(R.id.noteEditableFragment)
        Log.d("USER_TEST","${view.tag}")
        var layoutId = R.layout.fragment_note_editable
        assertThrows<java.lang.RuntimeException> {
            DataBinderMapperImpl().getDataBinder(MyDataBindingComponent(), view, layoutId) }
        assertThrows<IllegalArgumentException> {
            DataBinderMapperImpl().getDataBinder(MyDataBindingComponent(), viewWithTag, layoutId)
        }
    }
}