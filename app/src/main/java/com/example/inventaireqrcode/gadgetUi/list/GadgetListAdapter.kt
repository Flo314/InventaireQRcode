package com.example.inventaireqrcode.gadgetUi.list

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inventaireqrcode.Gadget
import com.example.inventaireqrcode.GadgetNfc
import com.example.inventaireqrcode.GadgetQRCode
import com.example.inventaireqrcode.R
import com.example.inventaireqrcode.widgets.toFormattedString

class GadgetListAdapter(
    context: Context,
    private val gadgets: List<Gadget>,
    private val listener: GadgetListAdapterListener? = null
): RecyclerView.Adapter<GadgetListAdapter.ViewHolder>(),View.OnClickListener {

    private val qrcodeColor =  ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.qrcode_icon, null))
    private val nfcColor =  ColorStateList.valueOf(ResourcesCompat.getColor(context.resources, R.color.nfc_icon, null))

    interface GadgetListAdapterListener {
        fun onGadgetClicked(gadget: Gadget)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // !! = forcer cardview à ne pas être null
        val cardView = itemView.findViewById<CardView>(R.id.cardView)!!
        val iconView = itemView.findViewById<ImageView>(R.id.iconImageView)!!
        val urlTextView = itemView.findViewById<TextView>(R.id.urlTextView)!!
        val dateCreated = itemView.findViewById<TextView>(R.id.dateCreatedTextView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gadget, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gadget = gadgets[position]
        // bloc with -> permet de référencer this comme étant le viewholder
        with (holder) {
            cardView.tag = gadget
            cardView.setOnClickListener(this@GadgetListAdapter)

            when (gadget) {
                is GadgetQRCode -> {
                    urlTextView.text = gadget.url
                    iconView.setImageResource(R.drawable.ic_qrcode)
                    ImageViewCompat.setImageTintList(iconView, qrcodeColor)
                }
                is GadgetNfc -> {
                    urlTextView.text = gadget.url
                    iconView.setImageResource(R.drawable.ic_nfc)
                    ImageViewCompat.setImageTintList(iconView, nfcColor)
                }
            }

            dateCreated.text = gadget.dateCreated.toFormattedString()
        }
    }

    // liste des items dans l'adapter
    override fun getItemCount(): Int = gadgets.size

    // click sur cardview
    override fun onClick(v: View) {
        listener?.onGadgetClicked(v.tag as Gadget)
    }

}