package com.example.warmrice.HelpService

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentRegisterVolunteerBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class RegisterVolunteerFragment : Fragment() {

    private lateinit var binding: FragmentRegisterVolunteerBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterVolunteerBinding.inflate(inflater, container, false)

        validateNameListener()
        validateIdentificationNumberListener()
        validateEmailAddressListener()
        validatePhoneNumberListener()
        validateAddressListener()
        validatePostalOrZipCodeListener()
        validateReasonToVolunteerListener()

        binding.submitButtonRegistrationForVolunteer.setOnClickListener{ submitRegistrationForVolunteer() }
        binding.resetButtonRegistrationForVolunteer.setOnClickListener { resetRegistrationForVolunteer() }

        return binding.root
    }

    //NAME VALIDATION
    private fun validateNameListener()
    {
        binding.nameInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.nameInputLayout.helperText = validMessageForNameInput()
            }
        }
    }

    private fun validMessageForNameInput(): String?
    {
        val nameInputFromUser = binding.nameInput.getText().toString()

        if(!nameInputFromUser.matches("^[A-Z][a-zA-Z]{1,}(?: [A-Z][a-zA-Z]{1,})(?: [A-Z][a-zA-Z]{1,})?(?: [A-Z][a-zA-Z]{1,})?\$".toRegex()))
        {
            binding.nameInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.name_validation_failed_message)
        }
        else {
            binding.nameInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //IDENTIFICATION NUMBER VALIDATION
    private fun validateIdentificationNumberListener()
    {
        binding.identificationNumberInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.identificationNumberInputLayout.helperText = validMessageForIdentificationNumberInput()
            }
        }
    }

    private fun validMessageForIdentificationNumberInput(): String?
    {
        val identificationNumberInputFromUser = binding.identificationNumberInput.getText().toString()

        if(!identificationNumberInputFromUser.matches("^\\d{6}\\-\\d{2}\\-\\d{4}\$".toRegex()))
        {
            binding.identificationNumberInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.identification_number_validation_failed_message)
        }
        else {
            binding.identificationNumberInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //EMAIL ADDRESS VALIDATION
    private fun validateEmailAddressListener()
    {
        binding.emailAddressInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.emailAddressInputLayout.helperText = validMessageForEmailAddressInput()
            }
        }
    }

    private fun validMessageForEmailAddressInput(): String?
    {
        val emailAddressInputFromUser = binding.emailAddressInput.getText().toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(emailAddressInputFromUser).matches())
        {
            binding.emailAddressInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.email_address_validation_failed_message)
        }
        else {
            binding.emailAddressInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //PHONE NUMBER VALIDATION
    private fun validatePhoneNumberListener()
    {
        binding.phoneNumberInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.phoneNumberInputLayout.helperText = validMessageForPhoneNumberInput()
            }
        }
    }

    private fun validMessageForPhoneNumberInput(): String?
    {
        val phoneNumberInputFromUser = binding.phoneNumberInput.getText().toString()

        if(!phoneNumberInputFromUser.matches("^\\d{3}\\-\\d{7,8}\$".toRegex()))
        {
            binding.phoneNumberInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.phone_number_validation_failed_message)
        }
        else {
            binding.phoneNumberInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //ADDRESS VALIDATION
    private fun validateAddressListener()
    {

        binding.addressInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.addressInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                binding.addressInputLayout.helperText = null

                if(binding.addressInput.getText().toString().isEmpty())
                {
                    binding.addressInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
                    binding.addressInputLayout.helperText = getString(R.string.address_not_empty_message)
                }
            }
        }
    }


    //POSTAL / ZIP CODE VALIDATION
    private fun validatePostalOrZipCodeListener()
    {
        binding.postalOrZipCodeInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.postalOrZipCodeInputLayout.helperText = validMessageForPostalOrZipCodeInput()
            }
        }
    }

    private fun validMessageForPostalOrZipCodeInput(): String?
    {
        val stateInputFromUser = binding.stateInput.selectedItem as String
        val postalOrZipCodeInputFromUser = binding.postalOrZipCodeInput.getText().toString()
        var stateValue = 0
        var validMessage = getString(R.string.johor_postal_or_zip_code_validation)

        when(stateInputFromUser) {
            "Johor"             -> if(!postalOrZipCodeInputFromUser.matches("^[8][0-6][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Kedah"             -> if(!postalOrZipCodeInputFromUser.matches("^[0][5-9][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Kelantan"          -> if(!postalOrZipCodeInputFromUser.matches("^[1][5-8][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Kuala Lumpur"      -> if((postalOrZipCodeInputFromUser.toInt() in 50000..60999))                                                               { stateValue = 1 }
                                   else if (postalOrZipCodeInputFromUser.matches("^[6][8][0-9]{3}$".toRegex()))                                             { stateValue = 1 }
                                   else                                                                                                                     { stateValue = 0 }
            "Labuan"            -> if(!postalOrZipCodeInputFromUser.matches("^[8][7][0-9]{3}$".toRegex()))                                                  { stateValue = 0 } else { stateValue = 1 }
            "Melaka"            -> if(!postalOrZipCodeInputFromUser.matches("^[7][5-8][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Negeri Sembilan"   -> if(!postalOrZipCodeInputFromUser.matches("^[7][0-3][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Pahang"            -> if(!postalOrZipCodeInputFromUser.matches("^[2][6-8][0-9]{3}$".toRegex()))                                                { stateValue = 0 } else { stateValue = 1 }
            "Perak"             -> if(!postalOrZipCodeInputFromUser.matches("^[3][0-6,9][0-9]{3}?$".toRegex()))                                             { stateValue = 0 } else { stateValue = 1 }
            "Perlis"            -> if(!postalOrZipCodeInputFromUser.matches("^[0][1][0-9]{3}?$".toRegex()))                                                 { stateValue = 0 } else { stateValue = 1 }
            "Pulau Pinang"      -> if(!postalOrZipCodeInputFromUser.matches("^[1][0-4][0-9]{3}?$".toRegex()))                                               { stateValue = 0 } else { stateValue = 1 }
            "Putrajaya"         -> if(!postalOrZipCodeInputFromUser.matches("^[6][2][0-9]{3}?$".toRegex()))                                                 { stateValue = 0 } else { stateValue = 1 }
            "Sabah"             -> if(postalOrZipCodeInputFromUser.toInt() in 88001..91999)                                                                 { stateValue = 1 } else { stateValue = 0 }
            "Sarawak"           -> if(!postalOrZipCodeInputFromUser.matches("^[9][3-8][0-9]{3}?$".toRegex()))                                               { stateValue = 0 } else { stateValue = 1 }
            "Selangor"          -> if((postalOrZipCodeInputFromUser.toInt() in 40000..48999))                                                               { stateValue = 1 }
                                   else if ((postalOrZipCodeInputFromUser.toInt() in 62000..64999))                                                         { stateValue = 1 }
                                   else                                                                                                                     { stateValue = 0 }
            "Terengganu"        -> if(!postalOrZipCodeInputFromUser.matches("^[2][0-4][0-9]{3}?$".toRegex()))                                               { stateValue = 0 } else { stateValue = 1 }
        }


        if(stateValue.equals(0))
        {
            binding.postalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            when(stateInputFromUser){
                "Johor"             -> validMessage = getString(R.string.johor_postal_or_zip_code_validation)
                "Kedah"             -> validMessage = getString(R.string.kedah_postal_or_zip_code_validation)
                "Kelantan"          -> validMessage = getString(R.string.kelantan_postal_or_zip_code_validation)
                "Kuala Lumpur"      -> validMessage = getString(R.string.kuala_lumpur_postal_or_zip_code_validation)
                "Labuan"            -> validMessage = getString(R.string.labuan_postal_or_zip_code_validation)
                "Melaka"            -> validMessage = getString(R.string.melaka_postal_or_zip_code_validation)
                "Negeri Sembilan"   -> validMessage = getString(R.string.negeri_sembilan_postal_or_zip_code_validation)
                "Pahang"            -> validMessage = getString(R.string.pahang_postal_or_zip_code_validation)
                "Perak"             -> validMessage = getString(R.string.perak_postal_or_zip_code_validation)
                "Perlis"            -> validMessage = getString(R.string.perlis_postal_or_zip_code_validation)
                "Pulau Pinang"      -> validMessage = getString(R.string.pulau_pinang_postal_or_zip_code_validation)
                "Putrajaya"         -> validMessage = getString(R.string.putrajaya_postal_or_zip_code_validation)
                "Sabah"             -> validMessage = getString(R.string.sabah_postal_or_zip_code_validation)
                "Sarawak"           -> validMessage = getString(R.string.sarawak_postal_or_zip_code_validation)
                "Selangor"          -> validMessage = getString(R.string.selangor_postal_or_zip_code_validation)
                "Terengganu"        -> validMessage = getString(R.string.terengganu_postal_or_zip_code_validation)
            }
            return validMessage
        }
        else if(stateValue.equals(1))
        {
            binding.postalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
        else
        {
            binding.postalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }

    }


    //REASON TO VOLUNTEER
    private fun validateReasonToVolunteerListener()
    {
        binding.others.setOnCheckedChangeListener { _, isChecked ->
            if(binding.others.isChecked)
            {
                binding.radOthersInputLayout.visibility = View.VISIBLE
                binding.radOthersInputLayout.helperText = getString(R.string.required_keyword)

                binding.radOthersInput.setOnFocusChangeListener { _, focused ->
                    if (!focused) {
                        if (binding.radOthersInput.getText().toString().isNullOrEmpty()) {
                            binding.radOthersInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
                            binding.radOthersInputLayout.helperText = getString(R.string.radio_box_validation_message)
                        } else {
                            binding.radOthersInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                            binding.radOthersInputLayout.helperText = null
                        }

                    }
                }

            }
            else
            {
                binding.radOthersInputLayout.visibility = View.GONE
                binding.radOthersInputLayout.helperText = null
            }
        }
    }


    //RESET
    private fun resetRegistrationForVolunteer() {
        with(binding) {
            nameInput.getText()?.clear()
            identificationNumberInput.getText()?.clear()
            emailAddressInput.getText()?.clear()
            phoneNumberInput.getText()?.clear()
            addressInput.getText()?.clear()
            stateInput.setSelection(0)
            postalOrZipCodeInput.getText()?.clear()
            rgrpReasonToVolunteer.check(R.id.toLearnNewSkills)
            radOthersInput.getText()?.clear()

        }
    }


    //SUBMIT
    private fun submitRegistrationForVolunteer() {

        var nameInputStatus = 0
        var identificationNumberInputStatus = 0
        var emailAddressInputStatus = 0
        var phoneNumberInputStatus = 0
        var addressInputStatus = 0
        var postalOrZipCodeInputStatus = 0
        var reasonToVolunteerStatus = 0

        nameInputStatus = checkNameInputStatus()
        identificationNumberInputStatus = checkIdentificationNumberInputStatus()
        emailAddressInputStatus = checkEmailAddressInputStatus()
        phoneNumberInputStatus = checkPhoneNumberInputStatus()
        addressInputStatus = checkAddressInputStatus()
        postalOrZipCodeInputStatus = checkPostalOrZipCodeInputStatus()
        reasonToVolunteerStatus = checkReasonToVolunteerStatus()

        var errorStatusInNameInput = 0
        var errorStatusInIdentificationNumberInput = 0
        var errorStatusInEmailAddressInput = 0
        var errorStatusInPhoneNumberInput = 0
        var errorStatusInPostalOrZipCodeInput = 0

        errorStatusInNameInput = checkErrorNameInputStatus()
        errorStatusInIdentificationNumberInput = checkErrorIdentificationNumberInputStatus()
        errorStatusInEmailAddressInput = checkErrorEmailAddressInputStatus()
        errorStatusInPhoneNumberInput = checkErrorPhoneNumberInputStatus()
        errorStatusInPostalOrZipCodeInput = checkErrorPostalOrZipCodeInputStatus()


        if(nameInputStatus.equals(1) && errorStatusInNameInput.equals(1)
            && identificationNumberInputStatus.equals(1) && errorStatusInIdentificationNumberInput.equals(1)
            && emailAddressInputStatus.equals(1) && errorStatusInEmailAddressInput.equals(1)
            && phoneNumberInputStatus.equals(1) && errorStatusInPhoneNumberInput.equals(1)
            && addressInputStatus.equals(1) && postalOrZipCodeInputStatus.equals(1) && errorStatusInPostalOrZipCodeInput.equals(1)
            && reasonToVolunteerStatus.equals(1) )
        {
            saveRegisterVolunteerData()

        }

    }


    //SAVE THE DATA INTO FIRESTORE (FIREBASE)
    private fun saveRegisterVolunteerData() {

        val registerVolunteerDatabase = FirebaseFirestore.getInstance()
        val voluntaryRegistration: MutableMap<String, Any> = HashMap()

        voluntaryRegistration["registerForVolunteerNameInput"] = binding.nameInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerIdentificationNumberInput"] = binding.identificationNumberInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerEmailAddressInput"] = binding.emailAddressInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerPhoneNumberInput"] = binding.phoneNumberInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerAddressInput"] = binding.addressInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerStateInput"] = binding.stateInput.selectedItem as String
        voluntaryRegistration["registerForVolunteerPostalOrZipCodeInput"] = binding.postalOrZipCodeInput.getText().toString().trim()
        voluntaryRegistration["registerForVolunteerReasonToVolunteer"] = getReasonToVolunteer()
        voluntaryRegistration["registerForVolunteerDate"] = Calendar.getInstance().time

        registerVolunteerDatabase.collection("voluntary_registration")
            .add(voluntaryRegistration)
            .addOnSuccessListener {
                toastRegisterVolunteer(getString(R.string.register_volunteer_data_added))
            }

    }


    private fun toastRegisterVolunteer(text: String)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        nav.navigateUp()
    }


    //RETURN THE STRING WHY VOLUNTEER
    private fun getReasonToVolunteer(): String{
        return when(binding.rgrpReasonToVolunteer.checkedRadioButtonId) {
            R.id.toLearnNewSkills               -> getString(R.string.radTo_learn_new_skills)
            R.id.toMeetWithNewCommunity         -> getString(R.string.radTo_meet_with_new_community)
            R.id.toGainExperienceOfVolunteering -> getString(R.string.radTo_gain_experience_of_volunteering)
            else                                -> binding.radOthersInput.getText().toString()
        }
    }


    //CHECK WHETHER THE NAME INPUT IS EMPTY OR NOT
    private fun checkNameInputStatus(): Int {
        if(binding.nameInput.getText().toString().isNullOrEmpty())
        {
            binding.nameInput.setError(getString(R.string.name_required_message))
            binding.nameInput.requestFocus()
            validateNameListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE IDENTIFICATION NUMBER INPUT IS EMPTY OR NOT
    private fun checkIdentificationNumberInputStatus(): Int {
        if(binding.identificationNumberInput.getText().toString().isNullOrEmpty())
        {
            binding.identificationNumberInput.setError(getString(R.string.identification_number_required_message))
            binding.identificationNumberInput.requestFocus()
            validateIdentificationNumberListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE EMAIL ADDRESS INPUT IS EMPTY OR NOT
    private fun checkEmailAddressInputStatus(): Int {
        if(binding.emailAddressInput.getText().toString().isNullOrEmpty())
        {
            binding.emailAddressInput.setError(getString(R.string.email_address_required_message))
            binding.emailAddressInput.requestFocus()
            validateEmailAddressListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE PHONE NUMBER INPUT IS EMPTY OR NOT
    private fun checkPhoneNumberInputStatus(): Int {
        if(binding.phoneNumberInput.getText().toString().isNullOrEmpty())
        {
            binding.phoneNumberInput.setError(getString(R.string.phone_number_required_message))
            binding.phoneNumberInput.requestFocus()
            validatePhoneNumberListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE ADDRESS INPUT IS EMPTY OR NOT
    private fun checkAddressInputStatus(): Int {
        if(binding.addressInput.getText().toString().isNullOrEmpty())
        {
            binding.addressInput.setError(getString(R.string.address_required_message))
            binding.addressInput.requestFocus()
            validateAddressListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE POSTAL / ZIP CODE INPUT IS EMPTY OR NOT
    private fun checkPostalOrZipCodeInputStatus(): Int {
        if(binding.postalOrZipCodeInput.getText().toString().isNullOrEmpty())
        {
            binding.postalOrZipCodeInput.setError(getString(R.string.postal_or_zip_code_required_message))
            binding.postalOrZipCodeInput.requestFocus()
            validatePostalOrZipCodeListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK THE REASON TO VOLUNTEER INPUT GOT EMPTY OR NOT ESPECIALLY OTHER BOX
    private fun checkReasonToVolunteerStatus(): Int {
        var reasonStatus = 0

        when(binding.rgrpReasonToVolunteer.checkedRadioButtonId) {
            R.id.toLearnNewSkills               -> reasonStatus = 1
            R.id.toMeetWithNewCommunity         -> reasonStatus = 1
            R.id.toGainExperienceOfVolunteering -> reasonStatus = 1
            else                                -> if(binding.radOthersInput.getText().toString().isNullOrEmpty())
            {
                binding.radOthersInput.setError(getString(R.string.radio_box_required_message))
                binding.radOthersInput.requestFocus()
                validateReasonToVolunteerListener()
                reasonStatus = 0
            }
            else { reasonStatus = 1 }
        }

        return reasonStatus
    }


    //CHECK ERROR STATUS OF NAME INPUT
    private fun checkErrorNameInputStatus(): Int {
        if(binding.nameInputLayout.helperText.toString().equals(getString(R.string.name_validation_failed_message)))
        {
            binding.nameInput.requestFocus()
            validateNameListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF IDENTIFICATION NUMBER INPUT
    private fun checkErrorIdentificationNumberInputStatus(): Int {
        if(binding.identificationNumberInputLayout.helperText.toString().equals(getString(R.string.identification_number_validation_failed_message)))
        {
            binding.identificationNumberInput.requestFocus()
            validateIdentificationNumberListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF EMAIL ADDRESS INPUT
    private fun checkErrorEmailAddressInputStatus(): Int {
        if(binding.emailAddressInputLayout.helperText.toString().equals(getString(R.string.email_address_validation_failed_message)))
        {
            binding.emailAddressInput.requestFocus()
            validateEmailAddressListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF PHONE NUMBER INPUT
    private fun checkErrorPhoneNumberInputStatus(): Int {
        if(binding.phoneNumberInputLayout.helperText.toString().equals(getString(R.string.phone_number_validation_failed_message)))
        {
            binding.phoneNumberInput.requestFocus()
            validatePhoneNumberListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF POSTAL / ZIP CODE INPUT
    private fun checkErrorPostalOrZipCodeInputStatus(): Int {
        if(binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.johor_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kedah_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kelantan_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kuala_lumpur_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.labuan_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.melaka_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.negeri_sembilan_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.pahang_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.perak_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.perlis_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.pulau_pinang_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.putrajaya_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.sabah_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.sarawak_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.selangor_postal_or_zip_code_validation))
            || binding.postalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.terengganu_postal_or_zip_code_validation)))
        {
            binding.postalOrZipCodeInput.requestFocus()
            validatePostalOrZipCodeListener()
            return 0
        }
        else { return 1 }
    }


}