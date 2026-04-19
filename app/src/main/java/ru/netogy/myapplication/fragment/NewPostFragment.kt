package ru.netogy.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netogy.myapplication.databinding.FragmentNewPostBinding
import ru.netogy.myapplication.util.StringArg
import ru.netogy.myapplication.viewmodel.PostViewModel
import kotlin.getValue


class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    val viewModel: PostViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        arguments?.textArg?.let(binding.edit::setText)
        binding.edit.setText(arguments?.getString("content"))
//        if (arguments?.getString("draft") != "") {
//            binding.edit.setText(arguments?.getString("draft"))
//        } else binding.edit.setText(arguments?.getString("content"))
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                viewModel.saveDraft(binding.edit.text.toString())
//                findNavController().navigateUp()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (!text.isBlank()) {
                viewModel.save(text)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}
