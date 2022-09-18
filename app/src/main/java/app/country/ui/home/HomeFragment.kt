package app.country.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.country.R
import app.country.databinding.FragmentHomeBinding
import app.country.state.Status
import app.country.state.Theme
import app.country.ui.adapter.CountryAdapter
import app.country.util.changeTheme
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.navigation.koinNavGraphViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by koinNavGraphViewModel(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.themeButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Select Theme")
                setSingleChoiceItems(
                    arrayOf("Follow System", "Light", "Dark"), preference.getInt("theme", 0)
                ){ dialog, which ->
                    val themeCode = if (which == -1) 0 else which
                    preference.edit().putInt("theme", themeCode).apply()
                    changeTheme(themeCode)
                    dialog.dismiss()
                }
            }.show()
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_filterDialogFragment)
        }

        binding.retryButton.setOnClickListener {
            viewModel.postCountry()
        }

        val countryAdapter = CountryAdapter {
            viewModel.saveCountry(it) { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        binding.countriesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            binding.progressBar.isVisible = status == Status.LOADING
            binding.errorView.isVisible = status == Status.ERROR
            if (status == Status.ERROR) binding.errorMessage.text = viewModel.errorMessage
        }

        viewModel.countries.observe(viewLifecycleOwner) {
            countryAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}