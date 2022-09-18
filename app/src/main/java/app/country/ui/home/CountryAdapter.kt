package app.country.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.country.databinding.ItemCountryBinding
import app.country.model.Country

class CountryAdapter(
    private val onFavoriteButtonClick: (Country) -> Unit
): ListAdapter<Country, CountryAdapter.CountryViewHolder>(
    object : DiffUtil.ItemCallback<Country>(){
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class CountryViewHolder(
        private val binding: ItemCountryBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(country: Country) {
            binding.countryName.text = country.name
            binding.continentName.text = country.continent.name
            binding.favoriteButton.setOnClickListener {
                onFavoriteButtonClick(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}