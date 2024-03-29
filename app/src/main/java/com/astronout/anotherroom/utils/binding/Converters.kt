package com.astronout.anotherroom.utils.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.astronout.anotherroom.R

class Converters {

    companion object {

        @JvmStatic
        @BindingAdapter("binding")
        fun bindAppCompatEditText(appCompatEditText: EditText, string: ObservableField<String>){
            val pair: Pair<ObservableField<String>, TextWatcherAdapter>? = appCompatEditText.getTag(R.id.bound_observable) as Pair<ObservableField<String>, TextWatcherAdapter>?
            if (pair==null || pair.first != string) {
                if (pair != null) {
                    appCompatEditText.removeTextChangedListener(pair.second)
                }
                val watcher = object : TextWatcherAdapter(){
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        super.onTextChanged(p0, p1, p2, p3)
                        string.set(p0?.toString())
                    }
                }
                appCompatEditText.setTag(R.id.bound_observable, Pair(string, watcher))
                appCompatEditText.addTextChangedListener(watcher)
            }
            val newValue = string.get()
            if (appCompatEditText.text.toString() != newValue){
                appCompatEditText.setText(newValue)
            }
        }
    }

}