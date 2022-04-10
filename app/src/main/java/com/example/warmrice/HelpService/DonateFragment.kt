package com.example.warmrice.HelpService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentDonateBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class DonateFragment : Fragment() {

    private lateinit var binding: FragmentDonateBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDonateBinding.inflate(inflater, container, false)

        validateDonateCreditOrDebitCardListener()
        validateDonateCCVListener()
        validateDonateValidThruListener()
        validateDonateAmountListener()
        validateLeaveCommentListener()

        binding.donationButton.setOnClickListener { donate() }

        return binding.root
    }


    //CREDIT CARD NUMBER VALIDATION
    private fun validateDonateCreditOrDebitCardListener() {

        binding.creditOrDebitCardNumberInput.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.creditOrDebitCardNumberInputLayout.helperText = validMessageForCreditOrDebitCardNumberInput()
            }

        }

    }


    private fun validMessageForCreditOrDebitCardNumberInput(): String? {
        val donationCardNumberInputFromUser = binding.creditOrDebitCardNumberInput.getText().toString()
        var s1 = 0
        var s2 = 0
        val decimal = 10
        val reverse = StringBuffer(donationCardNumberInputFromUser).reverse().toString()
        for (i in reverse.indices) {
            val digit = Character.digit(reverse[i], decimal)
            when {
                i % 2 == 0 -> s1 += digit
                else -> {
                    s2 += 2 * digit
                    when {
                        digit >= 5 -> s2 -= 9
                    }
                }
            }
        }

        if ((s1 + s2) % 10 == 0 && reverse.length >= 13) {
            binding.creditOrDebitCardNumberInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        } else {
            binding.creditOrDebitCardNumberInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.donation_credit_or_debit_card_number_validation_message)
        }

    }


    //CCV VALIDATION
    private fun validateDonateCCVListener() {

        binding.ccvInput.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.ccvInputLayout.helperText = validMessageForCCVInput()
            }

        }

    }

    private fun validMessageForCCVInput(): String? {
        val donationCCVInputFromUser = binding.ccvInput.getText().toString()

        if (!donationCCVInputFromUser.matches("^[0-9]{3}$".toRegex())) {
            binding.ccvInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.donation_ccv_validation_message)
        } else {
            binding.ccvInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }
    }


    //VALID THRU VALIDATION
    private fun validateDonateValidThruListener() {


        binding.validThruInput.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.validThruInputLayout.helperText = validMessageForValidThruInput()
            }

        }

    }

    private fun validMessageForValidThruInput(): String? {
        val validThruInputFromUser = binding.validThruInput.getText().toString()
        var validThruValue = 0
        var validMonthValue = 0
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR) % 100
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val currentYearInTwoDigit = currentYear % 1000 //ex: 2020 / 1000 = 2..20 , so currentYearInTwoDigit = 20

        //CHECK FORAMT CORRECT OR NOT
        if (!validThruInputFromUser.matches("^[0-9][0-9]/[0-9][0-9]$".toRegex())) {
            validThruValue = 0

        } else {
                if (Integer.parseInt(validThruInputFromUser.substring(3, 5)) > currentYearInTwoDigit) {                     //EX 23 > 22
                    validThruValue = 1                                                                                      //VALID THRU IS VALID

                    if (Integer.parseInt(validThruInputFromUser.substring(0, 2)) > 12 )
                    {
                        validThruValue = 3      //INVALID MONTH INPUT
                    }

                } else if (Integer.parseInt(validThruInputFromUser.substring(3, 5)).equals(currentYearInTwoDigit)) {        //EX 22(YEAR) = 22(YEAR)
                    if (Integer.parseInt(validThruInputFromUser.substring(0, 2)) >= 0 && Integer.parseInt(validThruInputFromUser.substring(0, 2)) <= 12 )
                    {
                        if (Integer.parseInt(validThruInputFromUser.substring(0, 2)) < currentMonth) {                      //EX 03(MONTH) < 04(MONTH)
                            validThruValue = 2                                                                              //EXPIRED CARD
                        } else {
                            validThruValue = 1                                                                              //VALID THRU IS VALID
                        }

                    }
                    else {                                                                                                  //EX 13 (MONTH)
                        validThruValue = 3                                                                                  //INVALID MONTH INPUT
                    }

                } else {
                    if (Integer.parseInt(validThruInputFromUser.substring(0, 2)) > 12 )
                    {
                        validThruValue = 3          //INVALID MONTH INPUT
                    }

            }

        }


        //POSSIBLE CONCLUSION
        //WRONG FORMAT FOR VALID THRU INPUT
        if (validThruValue.equals(0)) {
            binding.validThruInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.donation_valid_thru_format_message)

        }
        //VALID THRU INPUT
        else if (validThruValue.equals(1))
        {
            binding.validThruInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null

        }
        //EXPIRED CARD
        else if (validThruValue.equals(2))
        {
            binding.validThruInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.donation_valid_thru_validation_message)

        }
        //NOT EXPIRED BUT INVALID MONTH INPUT
        else if (validThruValue.equals(3)) {
            binding.validThruInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
            return getString(R.string.donation_invalid_month)

        }
        else
        {
            binding.validThruInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
            return null
        }

    }


    //AMOUNT DONATED VALIDATION
    private fun validateDonateAmountListener()
    {
            binding.radOtherAmountInput.setOnCheckedChangeListener { _, isChecked ->
                if(binding.radOtherAmountInput.isChecked)
                {
                    binding.radAmountDonateInputLayout.visibility = View.VISIBLE
                    binding.radAmountDonateInputLayout.helperText = getString(R.string.required_keyword)

                    binding.radAmountDonateInput.setOnFocusChangeListener { _, focused ->
                        if (!focused) {
                            if (binding.radAmountDonateInput.getText().toString().isNullOrEmpty()) {
                                binding.radAmountDonateInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
                                binding.radAmountDonateInputLayout.helperText = getString(R.string.donation_others_validation_keyword)

                            } else {
                                binding.radAmountDonateInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                                binding.radAmountDonateInputLayout.helperText = null

                            }

                        }
                    }
                }
                else
                {
                    binding.radAmountDonateInputLayout.visibility = View.GONE
                    binding.radAmountDonateInputLayout.helperText = null
                }
            }
    }


    //LEAVE A COMMENT
    private fun validateLeaveCommentListener()
    {
        binding.leaveComment.setOnCheckedChangeListener { _, isChecked ->
            if(binding.leaveComment.isChecked)
            {
                binding.leaveCommentInputLayout.visibility = View.VISIBLE
                binding.leaveCommentInputLayout.helperText = getString(R.string.required_keyword)

                binding.leaveCommentInput.setOnFocusChangeListener{ _, focused ->
                    if(!focused)
                        if (binding.leaveCommentInput.getText().toString().isNullOrBlank()) {
                            binding.leaveCommentInputLayout.setEndIconDrawable(R.drawable.outline_error_black_24dp)
                            binding.leaveCommentInputLayout.helperText = getString(R.string.comment_validation_message)

                        } else {
                            binding.leaveCommentInputLayout.setEndIconDrawable(R.drawable.outline_check_circle_black_18)
                            binding.leaveCommentInputLayout.helperText = null

                        }

                }
            }
            else
            {
                binding.leaveCommentInputLayout.visibility = View.GONE
                binding.leaveCommentInputLayout.helperText = null
            }
        }
    }

    //DONATE BUTTON
    private fun donate() {
        var donateCreditOrDebitCardInputStatus = 0
        var donateCCVInputStatus = 0
        var donateValidThruInputStatus = 0
        var donateAmountDonateStatus = 0
        var donateCommentStatus = 0

        donateCreditOrDebitCardInputStatus = donateCheckCreditOrDebitCardNumberInputStatus()
        donateCCVInputStatus = donateCheckCCVInputStatus()
        donateValidThruInputStatus = donateCheckValidThruInputStatus()
        donateAmountDonateStatus = donateCheckAmountDonateStatus()
        donateCommentStatus = donateCheckCommentStatus()

        var donateErrorStatusCreditOrDebitCardInput = 0
        var donateErrorStatusCCVInput = 0
        var donateErrorStatusValidThruInput = 0

        donateErrorStatusCreditOrDebitCardInput = donateCheckErrorCreditOrDebitCardInputStatus()
        donateErrorStatusCCVInput = donateCheckErrorCCVInputStatus()
        donateErrorStatusValidThruInput = donateCheckErrorValidThruInputStatus()

        if(donateCreditOrDebitCardInputStatus.equals(1) && donateErrorStatusCreditOrDebitCardInput.equals(1)
            && donateCCVInputStatus.equals(1) && donateErrorStatusCCVInput.equals(1)
            && donateValidThruInputStatus.equals(1) && donateErrorStatusValidThruInput.equals(1)
            && donateAmountDonateStatus.equals(1) && donateCommentStatus.equals(1))
        {
            saveDonateData()
        }
    }


    //SAVE THE DATA INTO FIRESTORE (FIREBASE)
    private fun saveDonateData() {

        val donationDatabase = FirebaseFirestore.getInstance()
        val donationDetail: MutableMap<String, Any> = HashMap()
        donationDetail["donationCreditOrDebitCardNumberInput"] = binding.creditOrDebitCardNumberInput.getText().toString().trim()
        donationDetail["donationCCVInput"] = binding.ccvInput.getText().toString().trim()
        donationDetail["donationAmountDonatedInput"] = getAmountDonation()
        donationDetail["donationLeaveComment"] = getCommentInput()
        donationDetail["donationDate"] = Calendar.getInstance().time


        donationDatabase.collection("donation")
            .add(donationDetail)
            .addOnSuccessListener {
                toastDonate(getString(R.string.donate_data_added))
            }


    }


    //DISPLAY THE TEXT IF SAVE INTO RECORD SUCCESSFULLY
    private fun toastDonate(text: String)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        nav.navigateUp()
    }


    //RETURN THE AMOUNT OF DONATION
    private fun getAmountDonation(): String {
        return when(binding.rgrpAmountDonated.checkedRadioButtonId) {
            R.id.radTenRinggitInput             -> getString(R.string.donation_ten_ringgit_header)
            R.id.radTwentyRinggitInput          -> getString(R.string.donation_twenty_ringgit_header)
            R.id.radFiftyRinggitInput           -> getString(R.string.donation_fifty_ringgit_header)
            R.id.radOneHundredRinggitInput      -> getString(R.string.donation_one_hundred_ringgit_header)
            R.id.radTwoHundredRinggitInput      -> getString(R.string.donation_two_hundred_ringgit_header)
            else                                -> binding.radAmountDonateInput.getText().toString()
        }

    }


    //RETURN THE COMMENT
    private fun getCommentInput(): String {
        if(binding.leaveComment.isChecked) {
            return binding.leaveCommentInput.getText().toString()

        }
        else { return "" }

    }


    //CHECK WHETHER THE CREDIT / DEBIT CARD NUMBER INPUT IS EMPTY OR NOT
    private fun donateCheckCreditOrDebitCardNumberInputStatus(): Int {
        if(binding.creditOrDebitCardNumberInput.getText().toString().isNullOrEmpty())
        {
            binding.creditOrDebitCardNumberInput.setError(getString(R.string.credit_or_debit_card_number_required_message))
            binding.creditOrDebitCardNumberInput.requestFocus()
            validateDonateCreditOrDebitCardListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE CCV INPUT IS EMPTY OR NOT
    private fun donateCheckCCVInputStatus(): Int {
        if(binding.ccvInput.getText().toString().isNullOrEmpty())
        {
            binding.ccvInput.setError(getString(R.string.ccv_required_message))
            binding.ccvInput.requestFocus()
            validateDonateCCVListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE VALID THRU INPUT IS EMPTY OR NOT
    private fun donateCheckValidThruInputStatus(): Int {
        if(binding.validThruInput.getText().toString().isNullOrEmpty())
        {
            binding.validThruInput.setError(getString(R.string.valid_thru_required_message))
            binding.validThruInput.requestFocus()
            validateDonateValidThruListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK WHETHER THE AMOUNT DONATED INPUT GOT EMPTY OR NOT ESPECIALLY THE OTHER AMOUNT BOX
    private fun donateCheckAmountDonateStatus(): Int {
        var amountStatus = 0

        when(binding.rgrpAmountDonated.checkedRadioButtonId) {
            R.id.radTenRinggitInput             -> amountStatus = 1
            R.id.radTwentyRinggitInput          -> amountStatus = 1
            R.id.radFiftyRinggitInput           -> amountStatus = 1
            R.id.radOneHundredRinggitInput      -> amountStatus = 1
            R.id.radTwoHundredRinggitInput      -> amountStatus = 1
            else                                -> if (binding.radAmountDonateInput.getText().toString().isNullOrEmpty())
                                                   {
                                                        binding.radAmountDonateInput.setError(getString(R.string.amount_donated_required_message))
                                                        binding.radAmountDonateInput.requestFocus()
                                                        validateDonateAmountListener()
                                                        amountStatus = 0

                                                   }
                                                   else { amountStatus = 1 }
        }

        return amountStatus
    }


    //CHECK THE COMMENT INPUT GOT EMPTY OR NOT IF IT IS CHECK
    private fun donateCheckCommentStatus(): Int {
        var commentStatus = 0

        if(binding.leaveComment.isChecked)
        {
            if(binding.leaveCommentInput.getText().toString().isNullOrEmpty())
            {
                binding.leaveCommentInput.setError(getString(R.string.comment_required_message))
                binding.leaveCommentInput.requestFocus()
                validateLeaveCommentListener()
                commentStatus = 0

            }
            else { commentStatus = 1 }

        }
        else { commentStatus = 1 }

        return commentStatus
    }


    //CHECK ERROR STATUS OF CREDIT / DEBIT CARD NUMBER INPUT
    private fun donateCheckErrorCreditOrDebitCardInputStatus(): Int {
        if(binding.creditOrDebitCardNumberInputLayout.helperText.toString().equals(getString(R.string.donation_credit_or_debit_card_number_validation_message)))
        {
            binding.creditOrDebitCardNumberInput.requestFocus()
            validateDonateCreditOrDebitCardListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF CCV INPUT
    private fun donateCheckErrorCCVInputStatus(): Int {
        if(binding.ccvInputLayout.helperText.toString().equals(getString(R.string.donation_ccv_validation_message)))
        {
            binding.ccvInput.requestFocus()
            validateDonateCCVListener()
            return 0

        }
        else { return 1 }
    }


    //CHECK ERROR STATUS OF VALID THRU INPUT
    private fun donateCheckErrorValidThruInputStatus(): Int {
        if(binding.validThruInputLayout.helperText.toString().equals(getString(R.string.donation_valid_thru_validation_message))
            || binding.validThruInputLayout.helperText.toString().equals(getString(R.string.donation_valid_thru_format_message))
            || binding.validThruInputLayout.helperText.toString().equals(getString(R.string.donation_invalid_month)))
        {
            binding.validThruInput.requestFocus()
            validateDonateValidThruListener()
            return 0

        }
        else { return 1 }
    }



}