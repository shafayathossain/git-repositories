package com.example.github.repositories.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.data.local.LocalDataStore
import com.example.github.repositories.data.model.RepositoryDTO

class RepositoryAdapter(
    val list: List<RepositoryDTO>,
    val callback: RepositoryAdapterCallback
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: View = itemView.findViewById(R.id.news_container)
        private val titleTxt: TextView = itemView.findViewById(R.id.title)
        private val imageVw: ImageView = itemView.findViewById(R.id.iv_bookmark)
        private val descriptionTxt: TextView = itemView.findViewById(R.id.description)
        private val authorTxt: TextView = itemView.findViewById(R.id.author)

        fun bindData() {
            val item = list[adapterPosition]
            titleTxt.text = item.nameInList
            descriptionTxt.text =
                if (item.description?.isNotBlank() == true && item.description!!.length > 150)
                    item.description?.take(150).plus("...")
                else
                    item.description
            authorTxt.text = item.owner.login
            imageVw.setImageResource(
                if (LocalDataStore.instance.getBookmarks().contains(item.id))
                    R.drawable.baseline_bookmark_black_24
                else
                    R.drawable.baseline_bookmark_border_black_24
            )
            container.setOnClickListener {
                callback.onItemClick(item)
            }
        }
    }

    interface RepositoryAdapterCallback {
        fun onItemClick(item: RepositoryDTO)
    }
}