package app.country.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.country.R
import app.country.databinding.FragmentDialogFilterBinding
import app.country.enum.Continent
import app.country.enum.Filter
import app.country.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.navigation.koinNavGraphViewModel

class FilterDialogFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentDialogFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by koinNavGraphViewModel(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedContinent.forEach { binding.chipGroup.check(it.id) }
        binding.radioGroup.check(viewModel.filter.id)

        binding.saveFilterButton.setOnClickListener {

            var filter = Filter.COUNTRY
            if (binding.radioGroup.checkedRadioButtonId == R.id.byContinentName) {
                filter = Filter.CONTINENT
            }
            viewModel.filter = filter

            viewModel.selectedContinent.apply {
                clear()
                if (binding.africa.isChecked) add(Continent.AFRICA)
                if (binding.antartica.isChecked) add(Continent.ANTARTICA)
                if (binding.asia.isChecked) add(Continent.ASIA)
                if (binding.europe.isChecked) add(Continent.EUROPE)
                if (binding.northAmerica.isChecked) add(Continent.NORTH_AMERICA)
                if (binding.oceania.isChecked) add(Continent.OCEANIA)
                if (binding.southAmerica.isChecked) add(Continent.SOUTH_AMERICA)
            }

            viewModel.postCountry()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}