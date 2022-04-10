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
import com.example.warmrice.databinding.FragmentRequestHelpBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class RequestHelpFragment : Fragment() {

    private lateinit var binding: FragmentRequestHelpBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRequestHelpBinding.inflate(inflater, container, false)

        validateHelpNameListener()
        validateHelpIdentificationNumberListener()
        validateHelpEmailAddressListener()
        validateHelpPhoneNumberListener()
        validateHelpAddressListener()
        validateHelpPostalOrZipCodeListener()
        validateTypeOfHelpRequiredListener()

        binding.helpResetButton.setOnClickListener { resetRequestForHelp() }
        binding.helpSubmitButton.setOnClickListener{ submitRequestForHelp() }

        return binding.root

    }


    //NAME VALIDATION
    private fun validateHelpNameListener() {

        binding.helpNameInput.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.helpNameInputLayout.helperText = validMessageForHelpNameInput()

            }

        }

    }


    private fun validMessageForHelpNameInput(): String?
    {
        val helpNameInputFromUser = binding.helpNameInput.getText().toString()

        if(!helpNameInputFromUser.matches("^[A-Z][a-zA-Z]{1,}(?: [A-Z][a-zA-Z]{1,})(?: [A-Z][a-zA-Z]{1,})?(?: [A-Z][a-zA-Z]{1,})?\$".toRegex()))
        {
            binding.helpNameInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.name_validation_failed_message)
        }
        else
        {
            binding.helpNameInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //IDENTIFICATION NUMBER VALIDATION
    private fun validateHelpIdentificationNumberListener()
    {
        binding.helpIdentificationNumberInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.helpIdentificationNumberInputLayout.helperText = validMessageForHelpIdentificationNumberInput()
            }
        }
    }

    private fun validMessageForHelpIdentificationNumberInput(): String?
    {
        val helpIdentificationNumberInputFromUser = binding.helpIdentificationNumberInput.getText().toString()

        if(!helpIdentificationNumberInputFromUser.matches("^\\d{6}\\-\\d{2}\\-\\d{4}\$".toRegex()))
        {
            binding.helpIdentificationNumberInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.identification_number_validation_failed_message)
        }
        else {
            binding.helpIdentificationNumberInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //EMAIL ADDRESS VALIDATION
    private fun validateHelpEmailAddressListener()
    {
        binding.helpEmailAddressInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.helpEmailAddressInputLayout.helperText = validMessageForHelpEmailAddressInput()
            }
        }
    }

    private fun validMessageForHelpEmailAddressInput(): String?
    {
        val helpEmailAddressInputFromUser = binding.helpEmailAddressInput.getText().toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(helpEmailAddressInputFromUser).matches())
        {
            binding.helpEmailAddressInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.email_address_validation_failed_message)
        }
        else {
            binding.helpEmailAddressInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //PHONE NUMBER VALIDATION
    private fun validateHelpPhoneNumberListener()
    {
        binding.helpPhoneNumberInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.helpPhoneNumberInputLayout.helperText = validMessageForHelpPhoneNumberInput()
            }
        }
    }

    private fun validMessageForHelpPhoneNumberInput(): String?
    {
        val helpPhoneNumberInputFromUser = binding.helpPhoneNumberInput.getText().toString()

        if(!helpPhoneNumberInputFromUser.matches("^\\d{3}\\-\\d{7,8}\$".toRegex()))
        {
            binding.helpPhoneNumberInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.phone_number_validation_failed_message)
        }
        else {
            binding.helpPhoneNumberInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //ADDRESS VALIDATION
    private fun validateHelpAddressListener()
    {

        binding.helpAddressInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.helpAddressInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                binding.helpAddressInputLayout.helperText = null
            }
        }
    }


    //POSTAL / ZIP CODE VALIDATION
    private fun validateHelpPostalOrZipCodeListener()
    {
        binding.helpPostalOrZipCodeInput.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.helpPostalOrZipCodeInputLayout.helperText = validMessageForHelpPostalOrZipCodeInput()
            }

        }
    }

    private fun validMessageForHelpPostalOrZipCodeInput(): String?
    {
        val helpStateInputFromUser = binding.helpStateInput.selectedItem as String
        val helpPostalOrZipCodeInputFromUser = binding.helpPostalOrZipCodeInput.getText().toString()
        var helpStateValue = 0
        var helpValidMessage = getString(R.string.johor_postal_or_zip_code_validation)

        when(helpStateInputFromUser) {
            "Johor"             -> if(!helpPostalOrZipCodeInputFromUser.matches("^[8][0-6][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Kedah"             -> if(!helpPostalOrZipCodeInputFromUser.matches("^[0][5-9][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Kelantan"          -> if(!helpPostalOrZipCodeInputFromUser.matches("^[1][5-8][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Kuala Lumpur"      -> if((helpPostalOrZipCodeInputFromUser.toInt() in 50000..60999))                                                               { helpStateValue = 1 }
                                   else if (helpPostalOrZipCodeInputFromUser.matches("^[6][8][0-9]{3}$".toRegex()))                                             { helpStateValue = 1 }
                                   else                                                                                                                         { helpStateValue = 0 }
            "Labuan"            -> if(!helpPostalOrZipCodeInputFromUser.matches("^[8][7][0-9]{3}$".toRegex()))                                                  { helpStateValue = 0 } else { helpStateValue = 1 }
            "Melaka"            -> if(!helpPostalOrZipCodeInputFromUser.matches("^[7][5-8][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Negeri Sembilan"   -> if(!helpPostalOrZipCodeInputFromUser.matches("^[7][0-3][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Pahang"            -> if(!helpPostalOrZipCodeInputFromUser.matches("^[2][6-8][0-9]{3}$".toRegex()))                                                { helpStateValue = 0 } else { helpStateValue = 1 }
            "Perak"             -> if(!helpPostalOrZipCodeInputFromUser.matches("^[3][0-6,9][0-9]{3}?$".toRegex()))                                             { helpStateValue = 0 } else { helpStateValue = 1 }
            "Perlis"            -> if(!helpPostalOrZipCodeInputFromUser.matches("^[0][1][0-9]{3}?$".toRegex()))                                                 { helpStateValue = 0 } else { helpStateValue = 1 }
            "Pulau Pinang"      -> if(!helpPostalOrZipCodeInputFromUser.matches("^[1][0-4][0-9]{3}?$".toRegex()))                                               { helpStateValue = 0 } else { helpStateValue = 1 }
            "Putrajaya"         -> if(!helpPostalOrZipCodeInputFromUser.matches("^[6][2][0-9]{3}?$".toRegex()))                                                 { helpStateValue = 0 } else { helpStateValue = 1 }
            "Sabah"             -> if(helpPostalOrZipCodeInputFromUser.toInt() in 88001..91999)                                                                 { helpStateValue = 1 } else { helpStateValue = 0 }
            "Sarawak"           -> if(!helpPostalOrZipCodeInputFromUser.matches("^[9][3-8][0-9]{3}?$".toRegex()))                                               { helpStateValue = 0 } else { helpStateValue = 1 }
            "Selangor"          -> if((helpPostalOrZipCodeInputFromUser.toInt() in 40000..48999))                                                               { helpStateValue = 1 }
                                   else if ((helpPostalOrZipCodeInputFromUser.toInt() in 62000..64999))                                                         { helpStateValue = 1 }
                                   else                                                                                                                         { helpStateValue = 0 }
            "Terengganu"        -> if(!helpPostalOrZipCodeInputFromUser.matches("^[2][0-4][0-9]{3}?$".toRegex()))                                               { helpStateValue = 0 } else { helpStateValue = 1 }
        }


        if(helpStateValue.equals(0))
        {
            binding.helpPostalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            when(helpStateInputFromUser){
                "Johor"             -> helpValidMessage = getString(R.string.johor_postal_or_zip_code_validation)
                "Kedah"             -> helpValidMessage = getString(R.string.kedah_postal_or_zip_code_validation)
                "Kelantan"          -> helpValidMessage = getString(R.string.kelantan_postal_or_zip_code_validation)
                "Kuala Lumpur"      -> helpValidMessage = getString(R.string.kuala_lumpur_postal_or_zip_code_validation)
                "Labuan"            -> helpValidMessage = getString(R.string.labuan_postal_or_zip_code_validation)
                "Melaka"            -> helpValidMessage = getString(R.string.melaka_postal_or_zip_code_validation)
                "Negeri Sembilan"   -> helpValidMessage = getString(R.string.negeri_sembilan_postal_or_zip_code_validation)
                "Pahang"            -> helpValidMessage = getString(R.string.pahang_postal_or_zip_code_validation)
                "Perak"             -> helpValidMessage = getString(R.string.perak_postal_or_zip_code_validation)
                "Perlis"            -> helpValidMessage = getString(R.string.perlis_postal_or_zip_code_validation)
                "Pulau Pinang"      -> helpValidMessage = getString(R.string.pulau_pinang_postal_or_zip_code_validation)
                "Putrajaya"         -> helpValidMessage = getString(R.string.putrajaya_postal_or_zip_code_validation)
                "Sabah"             -> helpValidMessage = getString(R.string.sabah_postal_or_zip_code_validation)
                "Sarawak"           -> helpValidMessage = getString(R.string.sarawak_postal_or_zip_code_validation)
                "Selangor"          -> helpValidMessage = getString(R.string.selangor_postal_or_zip_code_validation)
                "Terengganu"        -> helpValidMessage = getString(R.string.terengganu_postal_or_zip_code_validation)
            }
            return helpValidMessage
        }
        else if(helpStateValue.equals(1))
        {
            binding.helpPostalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
        else
        {
            binding.helpPostalOrZipCodeInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }

    }


    //TYPE OF HELP REQUIRED
    private fun validateTypeOfHelpRequiredListener()
    {
        binding.helpOthers.setOnCheckedChangeListener { _, isChecked ->
            if(binding.helpOthers.isChecked)
            {
                binding.helpOthersInputLayout.visibility = View.VISIBLE
                binding.helpOthersInputLayout.helperText = getString(R.string.required_keyword)

                binding.helpOthersInput.setOnFocusChangeListener { _, focused ->
                    if (!focused) {
                        if (binding.helpOthersInput.getText().toString().isNullOrBlank()) {
                            binding.helpOthersInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
                            binding.helpOthersInputLayout.helperText = getString(R.string.checked_box_validation_message)
                        } else {
                            binding.helpOthersInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                            binding.helpOthersInputLayout.helperText = null
                        }

                    }
                }

            }
            else
            {
                binding.helpOthersInputLayout.visibility = View.GONE
                binding.helpOthersInputLayout.helperText = null
            }
        }
    }


    //RESET
    private fun resetRequestForHelp() {
        with(binding) {
            helpNameInput.getText()?.clear()
            helpIdentificationNumberInput.getText()?.clear()
            helpEmailAddressInput.getText()?.clear()
            helpPhoneNumberInput.getText()?.clear()
            helpAddressInput.getText()?.clear()
            helpStateInput.setSelection(0)
            helpPostalOrZipCodeInput.getText()?.clear()
            helpClothes.isChecked
            helpFoods.setChecked(false)
            helpOthers.setChecked(false)
            helpOthersInput.getText()?.clear()

        }
    }


    //SUBMIT
    private fun submitRequestForHelp() {

        var helpNameInputStatus = 0
        var helpIdentificationNumberInputStatus = 0
        var helpEmailAddressInputStatus = 0
        var helpPhoneNumberInputStatus = 0
        var helpAddressInputStatus = 0
        var helpPostalOrZipCodeInputStatus = 0
        var helpTypeOfHelpRequired = 0

        helpNameInputStatus = helpCheckNameInputStatus()
        helpIdentificationNumberInputStatus = helpCheckIdentificationNumberInputStatus()
        helpEmailAddressInputStatus = helpCheckEmailAddressInputStatus()
        helpPhoneNumberInputStatus = helpCheckPhoneNumberInputStatus()
        helpAddressInputStatus = helpCheckAddressInputStatus()
        helpPostalOrZipCodeInputStatus = helpCheckPostalOrZipCodeInputStatus()
        helpTypeOfHelpRequired = helpCheckTypeOfHelpRequiredStatus()

        var helpErrorStatusInNameInput = 0
        var helpErrorStatusInIdentificationNumberInput = 0
        var helpErrorStatusInEmailAddressInput = 0
        var helpErrorStatusInPhoneNumberInput = 0
        var helpErrorStatusInPostalOrZipCodeInput = 0

        helpErrorStatusInNameInput = helpCheckErrorNameInputStatus()
        helpErrorStatusInIdentificationNumberInput = helpCheckErrorIdentificationNumberInputStatus()
        helpErrorStatusInEmailAddressInput = helpCheckErrorEmailAddressInputStatus()
        helpErrorStatusInPhoneNumberInput = helpCheckErrorPhoneNumberInputStatus()
        helpErrorStatusInPostalOrZipCodeInput = helpCheckErrorPostalOrZipCodeInputStatus()


        if(helpNameInputStatus.equals(1) && helpErrorStatusInNameInput.equals(1)
            && helpIdentificationNumberInputStatus.equals(1) && helpErrorStatusInIdentificationNumberInput.equals(1)
            && helpEmailAddressInputStatus.equals(1) && helpErrorStatusInEmailAddressInput.equals(1)
            && helpPhoneNumberInputStatus.equals(1) && helpErrorStatusInPhoneNumberInput.equals(1)
            && helpAddressInputStatus.equals(1) && helpPostalOrZipCodeInputStatus.equals(1) && helpErrorStatusInPostalOrZipCodeInput.equals(1)
            && helpTypeOfHelpRequired.equals(1) )
        {
            saveRequestForHelpData()
        }

    }


    //SAVE THE DATA INTO FIRESTORE (FIREBASE)
    private fun saveRequestForHelpData() {

        val requestHelpDatabase = FirebaseFirestore.getInstance()
        val requestHelp: MutableMap<String, Any> = HashMap()
        requestHelp["requestForHelpNameInput"] = binding.helpNameInput.getText().toString().trim()
        requestHelp["requestForHelpIdentificationNumberInput"] = binding.helpIdentificationNumberInput.getText().toString().trim()
        requestHelp["requestForHelpEmailAddressInput"] = binding.helpEmailAddressInput.getText().toString().trim()
        requestHelp["requestForHelpPhoneNumberInput"] = binding.helpPhoneNumberInput.getText().toString().trim()
        requestHelp["requestForHelpAddressInput"] = binding.helpAddressInput.getText().toString().trim()
        requestHelp["requestForHelpStateInput"]  = binding.helpStateInput.selectedItem as String
        requestHelp["requestForHelpPostalOrZipCodeInput"] = binding.helpPostalOrZipCodeInput.getText().toString().trim()
        requestHelp["requestForHelpTypeOfHelpRequired"]  = getTypeOfHelpRequired()
        requestHelp["requestForHelpDate"] = Calendar.getInstance().time

        requestHelpDatabase.collection("request_for_help")
            .add(requestHelp)
            .addOnSuccessListener {
                toastRequestForHelp(getString(R.string.request_for_help_data_added))
            }

    }


    //DISPLAY THE TEXT IF SAVE INTO RECORD SUCCESSFULLY
    private fun toastRequestForHelp(text: String)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        nav.navigateUp()
    }


    //RETURN THE TYPE OF HELP REQUIRED
    private fun getTypeOfHelpRequired(): String {

        var typeOfHelpUserInput = ""

        if (binding.helpClothes.isChecked) {
            typeOfHelpUserInput += " " + getString(R.string.checked_help_clothes)
        }
        if (binding.helpFoods.isChecked) {
            typeOfHelpUserInput += " " + getString(R.string.checked_help_foods)
        }
        if (binding.helpOthers.isChecked) {
            typeOfHelpUserInput += " " + binding.helpOthersInput.getText().toString()
        }


        return typeOfHelpUserInput
    }


    //CHECK WHETHER THE NAME INPUT IS EMPTY OR NOT
    private fun helpCheckNameInputStatus(): Int {
        if(binding.helpNameInput.getText().toString().isNullOrEmpty())
        {
            binding.helpNameInput.setError(getString(R.string.name_required_message))
            binding.helpNameInput.requestFocus()
            validateHelpNameListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE IDENTIFICATION NUMBER INPUT IS EMPTY OR NOT
    private fun helpCheckIdentificationNumberInputStatus(): Int {
        if(binding.helpIdentificationNumberInput.getText().toString().isNullOrEmpty())
        {
            binding.helpIdentificationNumberInput.setError(getString(R.string.identification_number_required_message))
            binding.helpIdentificationNumberInput.requestFocus()
            validateHelpIdentificationNumberListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE EMAIL ADDRESS INPUT IS EMPTY OR NOT
    private fun helpCheckEmailAddressInputStatus(): Int {
        if(binding.helpEmailAddressInput.getText().toString().isNullOrEmpty())
        {
            binding.helpEmailAddressInput.setError(getString(R.string.email_address_required_message))
            binding.helpEmailAddressInput.requestFocus()
            validateHelpEmailAddressListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE PHONE NUMBER INPUT IS EMPTY OR NOT
    private fun helpCheckPhoneNumberInputStatus(): Int {
        if(binding.helpPhoneNumberInput.getText().toString().isNullOrEmpty())
        {
            binding.helpPhoneNumberInput.setError(getString(R.string.phone_number_required_message))
            binding.helpPhoneNumberInput.requestFocus()
            validateHelpPhoneNumberListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE ADDRESS INPUT IS EMPTY OR NOT
    private fun helpCheckAddressInputStatus(): Int {
        if(binding.helpAddressInput.getText().toString().isNullOrEmpty())
        {
            binding.helpAddressInput.setError(getString(R.string.address_required_message))
            binding.helpAddressInput.requestFocus()
            validateHelpAddressListener()
            return 0

        }
        else { return 1 }
    }

    //CHECK WHETHER THE POSTAL / ZIP CODE INPUT IS EMPTY OR NOT
    private fun helpCheckPostalOrZipCodeInputStatus(): Int {
        if(binding.helpPostalOrZipCodeInput.getText().toString().isNullOrEmpty())
        {
            binding.helpPostalOrZipCodeInput.setError(getString(R.string.postal_or_zip_code_required_message))
            binding.helpPostalOrZipCodeInput.requestFocus()
            validateHelpPostalOrZipCodeListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK THE TYPE OF HELP REQUIRED INPUT GOT EMPTY OR NOT ESPECIALLY THE BOX
    private fun helpCheckTypeOfHelpRequiredStatus(): Int {
        var helpRequiredStatus = 0

        if(binding.helpClothes.isChecked) { helpRequiredStatus = 1 }
        if(binding.helpFoods.isChecked)   { helpRequiredStatus = 1 }
        if(binding.helpOthers.isChecked)
        {
            if(binding.helpOthersInput.getText().toString().isNullOrEmpty())
            {
                binding.helpOthersInput.setError(getString(R.string.radio_box_required_message))
                binding.helpOthersInput.requestFocus()
                validateTypeOfHelpRequiredListener()
                helpRequiredStatus = 0
            }
            else { helpRequiredStatus = 1 }
        }

        return helpRequiredStatus
    }


    //CHECK ERROR STATUS OF NAME INPUT
    private fun helpCheckErrorNameInputStatus(): Int {
        if(binding.helpNameInputLayout.helperText.toString().equals(getString(R.string.name_validation_failed_message)))
        {
            binding.helpNameInput.requestFocus()
            validateHelpNameListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF IDENTIFICATION NUMBER INPUT
    private fun helpCheckErrorIdentificationNumberInputStatus(): Int {
        if(binding.helpIdentificationNumberInputLayout.helperText.toString().equals(getString(R.string.identification_number_validation_failed_message)))
        {
            binding.helpIdentificationNumberInput.requestFocus()
            validateHelpIdentificationNumberListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF EMAIL ADDRESS INPUT
    private fun helpCheckErrorEmailAddressInputStatus(): Int {
        if(binding.helpEmailAddressInputLayout.helperText.toString().equals(getString(R.string.email_address_validation_failed_message)))
        {
            binding.helpEmailAddressInput.requestFocus()
            validateHelpEmailAddressListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF PHONE NUMBER INPUT
    private fun helpCheckErrorPhoneNumberInputStatus(): Int {
        if(binding.helpPhoneNumberInputLayout.helperText.toString().equals(getString(R.string.phone_number_validation_failed_message)))
        {
            binding.helpPhoneNumberInput.requestFocus()
            validateHelpPhoneNumberListener()
            return 0
        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF POSTAL / ZIP CODE INPUT
    private fun helpCheckErrorPostalOrZipCodeInputStatus(): Int {
        if(binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.johor_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kedah_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kelantan_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.kuala_lumpur_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.labuan_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.melaka_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.negeri_sembilan_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.pahang_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.perak_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.perlis_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.pulau_pinang_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.putrajaya_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.sabah_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.sarawak_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.selangor_postal_or_zip_code_validation))
            || binding.helpPostalOrZipCodeInputLayout.helperText.toString().equals(getString(R.string.terengganu_postal_or_zip_code_validation)))
        {
            binding.helpPostalOrZipCodeInput.requestFocus()
            validateHelpPostalOrZipCodeListener()
            return 0
        }
        else { return 1 }
    }
}