package ie.setu.mediapp.view.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mediapp.Model.Appointment
import ie.setu.mediapp.Model.SlotsList
import ie.setu.mediapp.R

class ItemBookingAdapter(private val items: MutableList<Appointment>, private val onSelectAction: (Int) -> Unit) :
    RecyclerView.Adapter<ItemBookingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(apSlot: String,pos:Int,status:String,apDate:String,drName:String) {//,catName: String,phone: String,place: String
            itemView.findViewById<TextView>(R.id.itemText).text = "Dr : "+drName
            itemView.findViewById<TextView>(R.id.slotText).text = "Slot : "+apSlot
            itemView.findViewById<TextView>(R.id.statusText).text = "Status : "+status
            itemView.findViewById<TextView>(R.id.dateText).text = "Date : "+apDate

            if (status != "pending")
            {
                itemView.findViewById<Button>(R.id.selection_action).isEnabled = false
                itemView.findViewById<Button>(R.id.actionView).isVisible = false
            }
            else
            {
                itemView.findViewById<Button>(R.id.selection_action).isEnabled = true
                itemView.findViewById<Button>(R.id.actionView).isVisible = true
            }

            itemView.findViewById<Button>(R.id.selection_action).setOnClickListener {
                // Call the onButtonClick function and pass the current item
                onSelectAction(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_booking_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item.apSlot,position,item.status,item.apDate,item.drName)//,item.catName,item.phone,item.place
        // Add any other click listeners or actions for the item here

    }

    override fun getItemCount(): Int {
        return items.size
    }



}