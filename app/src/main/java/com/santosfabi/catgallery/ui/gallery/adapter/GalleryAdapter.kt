    package com.santosfabi.catgallery.ui.gallery.adapter

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.ListAdapter
    import androidx.recyclerview.widget.RecyclerView
    import coil.load
    import coil.size.Scale
    import com.santosfabi.catgallery.data.model.PictureDetails
    import com.santosfabi.catgallery.databinding.ItemPicGalleryBinding

    class GalleryAdapter :
        ListAdapter<PictureDetails, GalleryAdapter.ViewHolder>(PictureItemDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemPicGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

        class ViewHolder(val binding: ItemPicGalleryBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: PictureDetails) {

                // Calculate the width and height for the image
                val screenWidth = binding.root.resources.displayMetrics.widthPixels
                val imageWidth = screenWidth / 4.5 // 4 images per row
                val imageHeight = (imageWidth * 1.5).toInt()

                binding.ivPicture.layoutParams.width = imageWidth.toInt()
                binding.ivPicture.layoutParams.height = imageHeight
                binding.ivPicture.requestLayout()

                binding.ivPicture.load(item.link) {
                    crossfade(true)
                    size(imageWidth.toInt(), imageHeight)
                    scale(Scale.FILL)
                }

                binding.ivPicture.contentDescription = item.description ?: "Cat"
            }
        }

        class PictureItemDiffCallback : DiffUtil.ItemCallback<PictureDetails>() {
            override fun areItemsTheSame(oldItem: PictureDetails, newItem: PictureDetails): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PictureDetails, newItem: PictureDetails): Boolean {
                return oldItem == newItem
            }
        }
    }
