package com.santosfabi.catgallery.ui.gallery.adapter

import androidx.test.runner.AndroidJUnit4
import com.santosfabi.catgallery.data.model.PictureDetails
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class GalleryAdapterTest {

    private lateinit var adapter: GalleryAdapter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        adapter = GalleryAdapter()
    }

    // Test: Check if items with the same ID are correctly identified as the same
    @Test
    fun pictureItemDiffCallback_areItemsTheSame() {
        val oldItem = PictureDetails("0", "url1", "Cats", false)
        val newItem = PictureDetails("0", "url2", "Dogs", true)
        val diffCallback = GalleryAdapter.PictureItemDiffCallback()

        val result = diffCallback.areItemsTheSame(oldItem, newItem)

        assertEquals(true, result)
    }

    // Test: Check if items with the same ID but different content are identified as different
    @Test
    fun pictureItemDiffCallback_areContentsTheSame() {
        val oldItem = PictureDetails("0", "url", "Cats", false)
        val newItem = PictureDetails("0", "url", "Dogs", true)
        val diffCallback = GalleryAdapter.PictureItemDiffCallback()

        val result = diffCallback.areContentsTheSame(oldItem, newItem)

        assertEquals(false, result)
    }

    // Test: Check if the adapter's item count matches the provided list size
    @Test
    fun getItemCount_returnsCorrectCount() {
        val itemList = listOf(
            PictureDetails("0", "url1", "Cats", false),
            PictureDetails("1", "url2", "Cat jumping", true)
        )
        adapter.submitList(itemList)
        assertEquals(itemList.size, adapter.itemCount)
    }

    // Test: Check if the adapter behaves correctly with an empty list
    @Test
    fun adapter_behavesWithEmptyList() {
        val emptyList: List<PictureDetails> = emptyList()
        adapter.submitList(emptyList)

        assertEquals(0, adapter.itemCount)
    }

    // Test: Check if the adapter behaves correctly with a non-empty list
    @Test
    fun adapter_behavesWithNonEmptyList() {
        val itemList = listOf(
            PictureDetails("0", "url1", "Cats", false),
            PictureDetails("1", "url2", "Blue Cat", true)
        )
        adapter.submitList(itemList)

        assertEquals(itemList.size, adapter.itemCount)
    }
}