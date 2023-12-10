package com.founder.easy_route_assistant.presentation.detail_route

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.founder.easy_route_assistant.databinding.FragmentRouteDetailBinding

class RouteDetailFragment : Fragment() {
    private var _binding: FragmentRouteDetailBinding? = null
    private val binding get() = _binding!!

    private var imagePath1: String? = null
    private var imagePath2: String? = null
    private var imagePath3: String? = null
    private var imagePath4: String? = null
    private var description1: String? = null
    private var description2: String? = null
    private var description3: String? = null
    private var description4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDescriptions()
        pressBackBtn()
    }

    private fun getBundle() {
        arguments?.let {
            imagePath1 = it.getString("imgPath1")
            description1 = it.getString("description1")
            imagePath2 = it.getString("imgPath2")
            description2 = it.getString("description2")
            imagePath3 = it.getString("imgPath3")
            description3 = it.getString("description3")
            imagePath4 = it.getString("imgPath4")
            description4 = it.getString("description4")
        }
    }

    private fun setDescriptions() {
        setFirstDescriptions()
        setSecondDescriptions()
        setThirdDescriptions()
        setFourthDescriptions()
    }

    private fun setFirstDescriptions() {
        if (imagePath1 != null && description1 != null) {
            Glide.with(this)
                .asBitmap()
                .load(ImageSource.uri(imagePath1!!))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imvItemDetailViewImg.setImage(ImageSource.bitmap(resource))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            val description = description1!!.trimMargin()
            val descriptionsList = description
                .substring(1, description.length - 1)  // [ 와 ] 제거
                .split(",")  // , 기준으로 문자열 나누기
                .map { it.trim() }  // 각 문자열 앞뒤 공백 제거

            binding.tvItemDetailViewDescription.text = descriptionsList.joinToString("\n")
        }
    }

    private fun setSecondDescriptions() {
        if (imagePath2 != null && description2 != null) {
            Glide.with(this)
                .asBitmap()
                .load(ImageSource.uri(imagePath2!!))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imvItemDetailViewImg2.setImage(ImageSource.bitmap(resource))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            val description = description2!!.trimMargin()
            val descriptionsList = description
                .substring(1, description.length - 1)  // [ 와 ] 제거
                .split(",")  // , 기준으로 문자열 나누기
                .map { it.trim() }  // 각 문자열 앞뒤 공백 제거

            binding.tvItemDetailViewDescription2.text = descriptionsList.joinToString("\n")
        }
    }

    private fun setThirdDescriptions() {
        if (imagePath3 != null && description3 != null) {
            Glide.with(this)
                .asBitmap()
                .load(ImageSource.uri(imagePath3!!))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imvItemDetailViewImg3.setImage(ImageSource.bitmap(resource))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            val description = description3!!.trimMargin()
            val descriptionsList = description
                .substring(1, description.length - 1)  // [ 와 ] 제거
                .split(",")  // , 기준으로 문자열 나누기
                .map { it.trim() }  // 각 문자열 앞뒤 공백 제거

            binding.tvItemDetailViewDescription3.text = descriptionsList.joinToString("\n")
        }
    }

    private fun setFourthDescriptions() {
        if (imagePath4 != null && description4 != null) {
            Glide.with(this)
                .asBitmap()
                .load(ImageSource.uri(imagePath4!!))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imvItemDetailViewImg2.setImage(ImageSource.bitmap(resource))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            val description = description4!!.trimMargin()
            val descriptionsList = description
                .substring(1, description.length - 1)  // [ 와 ] 제거
                .split(",")  // , 기준으로 문자열 나누기
                .map { it.trim() }  // 각 문자열 앞뒤 공백 제거

            binding.tvItemDetailViewDescription4.text = descriptionsList.joinToString("\n")
        }
    }

    private fun pressBackBtn() {
        binding.btnItemDetailViewExit.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
    }

}