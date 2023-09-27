let xValid = false, yValid = false, rValid = false;
const xValidValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
const rValidValues = [1, 2, 3, 4, 5];

// Function to validate if a value is in the validValues array
function validateSelection(value, validValues) {
    return validValues.includes(value);
}

// Attach click event listeners to x buttons
let selectedXBtn;
const errorMessageBox = document.getElementById('error-message');
document.addEventListener("DOMContentLoaded", function () {
    const xBtns = document.querySelectorAll('.button-group__button');

    xBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const selectedValue = parseFloat(btn.value);
            xBtns.forEach(otherBtn => {
                otherBtn.classList.remove('active');
            });
            if (selectedValue !== selectedXBtn && selectedValue.toString() === btn.value && validateSelection(selectedValue, xValidValues)) {
                btn.classList.add('active');
                selectedXBtn = selectedValue;
                xValid = true;
                errorMessageBox.textContent = '';
            } else {
                btn.classList.remove('active');
                selectedXBtn = undefined;
                xValid = false;
                errorMessageBox.textContent = 'Check the value.';
            }
            toggleSubmitBtn();
        });
    });
});

// Get reference to the y input field
const yInput = document.querySelector('input[name="y"]');
yInput.addEventListener('input', () => {
    yValid = false;

    const regex = /^[0-9.,]+$/;
    if (!regex.test(yInput.value)) {
        yInput.setCustomValidity('Check the value.');
        yInput.reportValidity();
        toggleSubmitBtn();
        return;
    }

    const yValue = parseFloat(yInput.value.trim().replace(',', '.'));
    if (isNaN(yValue)) {
        yInput.setCustomValidity('Check the value.');
    } else if (yValue < -3 || yValue > 5) {
        yInput.setCustomValidity('The value must be in the interval (-3 ... 5).');
    } else {
        yValid = true;
        yInput.setCustomValidity('');
    }
    yInput.reportValidity();
    toggleSubmitBtn();
});

// Get references to the radio buttons for r
const rRadios = document.querySelectorAll('input[name="r"]');
rRadios.forEach(rRadio => {
    rRadio.addEventListener('change', () => {
        const selectedValue = parseInt(rRadio.value);
        if (validateSelection(selectedValue, rValidValues) && selectedValue.toString() === rRadio.value) {
            rValid = true;
            rRadio.setCustomValidity('');
        } else {
            rValid = false;
            rRadio.setCustomValidity('Check the value.');
        }
        rRadio.reportValidity();
        toggleSubmitBtn();
    });
});

// Function to toggle the submit button state based on validation
const submitBtn = document.querySelector('[type="submit"]');
function toggleSubmitBtn() {
    submitBtn.disabled = !(xValid && yValid && rValid)
}