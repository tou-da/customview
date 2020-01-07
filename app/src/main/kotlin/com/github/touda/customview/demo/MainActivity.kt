package com.github.touda.customview.demo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.touda.customview.adapter.PinnedAdapter
import com.github.touda.customview.bean.Pinned
import com.github.touda.customview.bean.PinnedBean
import com.github.touda.customview.demo.databinding.ActivityMainBinding
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 构造数据
        val data = mutableListOf<Pinned>()
        repeat(5) {i->
            data.add(PinnedTitleBean(i + 1))
            repeat(18 - i) {
                data.add(PinnedItemBean(i + 1))
            }

            repeat(i) {
                data.add(PinnedItemBean(i + 1, true))
            }

        }
        // 调试信息开关
        mBinding.pinned.setDebug(true)
        mBinding.pinned.layoutManager = StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL)

        // 设置固定试图
        mBinding.pinned.setPinned(-1,
            LayoutInflater.from(this).inflate(R.layout.item_title, mBinding.root, false)
        ) {section, view, isUpdate->
            if (isUpdate) {
                repeat(data.size) {
                    if (data[it] is PinnedTitleBean && data[it].section == section) {
                        val title = (data[it] as? PinnedTitleBean)?.title
                        (view.findViewById(R.id.tvChapter) as? TextView)?.text = title
                        Log.e(TAG, "---- 测试信息 $title")
                        return@repeat
                    }
                }
            }
            Log.e(TAG, "---- 测试信息 是否更新 $isUpdate")
            view.setBackgroundColor(Color.RED)
        }

        val obj: PinnedAdapter  = object: PinnedAdapter(R.layout.item_title, R.layout.item_content) {

            override fun convertHeader(helper: BaseViewHolder, item: Pinned) {
                super.convertHeader(helper, item)
                if (item is PinnedTitleBean) {
                    helper.setText(R.id.tvChapter, item.title)
                }
            }

            override fun convert(holder: BaseViewHolder, item: Pinned) {
                val entity = item as PinnedItemBean
                if (entity.isHolder) {
                    holder.itemView.visibility = View.INVISIBLE
                    return
                }
                holder.itemView.visibility = View.VISIBLE
                super.convert(holder, item)
                val obj = holder.itemView.getTag(R.id.pinnedLast)
                if (obj.javaClass.isArray) {
                    val num = (holder.layoutPosition + 1 - item.section).minus((obj as IntArray)[1])
                    holder.setText(R.id.tvResult, num.toString())
                }
            }
        }
        obj.apply {
            // 设置item偏移
            setItem(resources.getDimensionPixelSize(R.dimen.dp_21))
            // 设置固定数据
            setPinned(PinnedBean(data, listOf(18, 17, 16, 15, 14), listOf(0, 1, 2, 3, 4)))
            mBinding.pinned.adapter = this
        }

        val d = Observable.just(data.size).delay(1, TimeUnit.SECONDS)
            .subscribe{
                // 自动滚动到索引位置
                mBinding.pinned.startPinned(Random().nextInt(46) + 1)
            }
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}

class PinnedTitleBean(section: Int, override val isHeader: Boolean = true): Pinned(section) {

    val title = "测试title $section"
}

class PinnedItemBean(section: Int, val isHolder: Boolean = false): Pinned(section) {
    override val lastCount: Int
        get() = section - 2
}