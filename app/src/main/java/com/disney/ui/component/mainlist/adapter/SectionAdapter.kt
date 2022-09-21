package com.disney.ui.component.mainlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.disney.databinding.RecyclerViewFooterItemBinding
import com.disney.databinding.RecyclerViewHeaderItemBinding
import com.disney.databinding.RecyclerViewItemBinding
import com.disney.ui.base.listeners.RecyclerItemListener
import com.disney.ui.component.mainlist.GuestViewModel
import com.disney.utils.Section
import com.disney.utils.SectionItem
import com.disney.utils.StickyAdapter
import kotlinx.android.synthetic.main.recycler_view_header_item.view.*

class SectionAdapter(
    private val guestViewModel: GuestViewModel,
) : StickyAdapter<RecyclerView.ViewHolder?, RecyclerView.ViewHolder?>() {
    var items: MutableList<Section> = ArrayList()
    private val checkedPos: MutableList<Int> = ArrayList()
    private val checkedPosItems: MutableList<SectionItem> = ArrayList()

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelectedsss(list: ArrayList<SectionItem>) {
            guestViewModel.manageContinueButton(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Section.HEADER -> {
                val itemBinding =
                    RecyclerViewHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return HeaderViewHolder(itemBinding)
            }
            Section.FOOTER -> {
                val itemBinding =
                    RecyclerViewFooterItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return FooterViewHolder(itemBinding)
            }
            else -> {
                val itemBinding =
                    RecyclerViewItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sec: Section = items[position]
        val type = sec.type()
        val sectionTitle = sec.sectionTitle()
        when (type) {
            Section.HEADER -> {
                (holder as HeaderViewHolder).bind(sectionTitle)
            }
            Section.ITEM -> {
                (holder as ItemViewHolder).bind(
                    sec,
                    checkedPos,
                    checkedPosItems,
                    onItemClickListener
                )
            }
            else -> {
                (holder as FooterViewHolder).bind(sectionTitle)
            }
        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?, headerPosition: Int) {
        (holder as HeaderViewHolder).itemView.text_view.text = items[headerPosition].sectionTitle()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return items[itemPosition].sectionPosition()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return createViewHolder(parent, Section.HEADER)
    }


    class HeaderViewHolder(private val itemBinding: RecyclerViewHeaderItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(title: String) {
            itemBinding.textView.text = title
        }
    }

    class FooterViewHolder(private val itemBinding: RecyclerViewFooterItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(title: String) {
            itemBinding.textView.text = title
        }
    }

    class ItemViewHolder(private val itemBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            sec: Section,
            checkedPos: MutableList<Int>,
            checkedPosItems: MutableList<SectionItem>,
            onItemClickListener: RecyclerItemListener
        ) {
            val sectionTitle = sec.sectionTitle()
            itemBinding.textView.text = sectionTitle

            itemBinding.llMain.setOnClickListener {
                itemBinding.checkbox.performClick()
            }

            itemBinding.checkbox.setOnCheckedChangeListener(null)

            itemBinding.checkbox.isChecked = checkedPos.contains(position)

            itemBinding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedPos.add(adapterPosition)
                    checkedPosItems.add(sec as SectionItem)
                } else {
                    checkedPos.remove(adapterPosition)
                    checkedPosItems.remove(sec as SectionItem)
                }
                onItemClickListener.onItemSelectedsss(
                    checkedPosItems as ArrayList<SectionItem>
                )
            }
        }
    }
}