package ie.setu.mediapp.Model

data class Appointments(
    val apId: String,
    val apDate: String,
    val apSlot: String,
    val apSlotId: String,
    val ptName: String,
    val ptId: String,
    val drName: String,
    val drId: String,
    val status: String
)