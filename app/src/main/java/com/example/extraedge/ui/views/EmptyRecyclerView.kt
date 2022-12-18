package biz.flexcoder.atsonline.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EmptyRecyclerView : RecyclerView {
    private var _context: Context
    private var emptyView: View? = null
    private var emptyImage: ImageView? = null
    private var emptyText: TextView? = null
    private var views: Array<View>? = null
    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(_context: Context) : super(_context) {
        this._context = _context
    }

    constructor(
        _context: Context,
        attrs: AttributeSet?
    ) : super(_context, attrs) {
        this._context = _context
    }

    constructor(
        _context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(_context, attrs, defStyle) {
        this._context = _context
    }

    fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter!!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            if (views != null) {
                for (v in views!!) v.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            }
        }else if (emptyView != null && adapter == null){
            emptyView!!.visibility = View.VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
        checkIfEmpty()
    }

    fun getEmptyView(): View? {
        return emptyView
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
        checkIfEmpty()
    }

    fun setEmptyTextView(textView: TextView?) {
        emptyText = textView
    }

    fun setEmptyImageView(imageView: ImageView?) {
        emptyImage = imageView
    }

    fun setEmptyText(text: String?) {
        if (emptyText != null) emptyText!!.text = text
    }

    fun setEmptyImage(resource: Int) {
        if (emptyImage != null) emptyImage!!.setImageResource(resource)
    }

    fun setEmptyImage(url: String?) {
        if (emptyImage != null) Glide.with(_context).load(url).into(emptyImage!!)
    }

    fun hideEmptyView() {
        if (emptyImage != null) emptyView!!.visibility = View.GONE
    }

    fun setAdditionalHelperView(views: Array<View>?) {
        this.views = views
    }
}