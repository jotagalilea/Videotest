package com.jg.videotest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jg.videotest.R
import com.jg.videotest.databinding.RowCategoryBinding
import com.jg.videotest.model.ui.ContentUi
import com.jg.videotest.ui.hide
import com.jg.videotest.ui.show

class CategoriesAdapter(
    private val videoClickListener: VideosAdapter.VideoClickListener
): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var _rowCategoryBinding: RowCategoryBinding? = null
    private val rowCategoryBinding get() = _rowCategoryBinding!!
    private val categoriesUiList: MutableList<ContentUi> = mutableListOf()
    var lastUnfolded = -1


    fun updateItems(items: List<ContentUi>) {
        categoriesUiList.clear()
        categoriesUiList.addAll(items)
        notifyItemRangeChanged(0, categoriesUiList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.CategoryViewHolder {
        _rowCategoryBinding = RowCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(rowCategoryBinding)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categoriesUiList[position])
    }

    override fun getItemCount(): Int = categoriesUiList.size



    //region ViewHolder
    inner class CategoryViewHolder(
        private val binding: RowCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: ContentUi) {
            binding.rowCategoryTitle.text = item.category.title
            setupSubRecycler(item)
            updateIcon(item)
            binding.root.setOnClickListener {
                this.onClick(it)
            }
        }

        private fun setupSubRecycler(item: ContentUi) {
            binding.rowVideosSublist.apply {
                layoutManager = object : LinearLayoutManager(
                    binding.root.context, HORIZONTAL, false
                ) {
                    override fun canScrollVertically() = false
                }
                setRecycledViewPool(viewPool)
                setHasFixedSize(true)
                adapter = VideosAdapter(item.videosList, videoClickListener)
                if (item.folded) hide() else show()
            }
        }

        private fun updateIcon(item: ContentUi) {
            with(binding){
                if (item.folded) {
                    this.ivFoldIcon.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this.root.context,
                            R.drawable.ic_arrow_down
                        )
                    )
                }
                else {
                    this.ivFoldIcon.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this.root.context,
                            R.drawable.ic_arrow_up
                        )
                    )
                }
            }
        }

        override fun onClick(v: View?) {
            updateListFolds()
            rescaleRecycler()
            notifyItemChanged(bindingAdapterPosition)
        }

        private fun updateListFolds() {
            categoriesUiList[bindingAdapterPosition].folded = !categoriesUiList[bindingAdapterPosition].folded
            if ((lastUnfolded > -1) && (lastUnfolded != bindingAdapterPosition)){
                categoriesUiList[lastUnfolded].folded = true
                notifyItemChanged(lastUnfolded)
            }
            lastUnfolded = bindingAdapterPosition
        }

        private fun rescaleRecycler() {
            binding.rowVideosSublist.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

    }
    //endregion

}