// Previous table data 
document.addEventListener('DOMContentLoaded', () => {
    let xhr = new XMLHttpRequest();
    xhr.onloadend = () => {
        if (xhr.status === 200) {
            const tbody = document.querySelector('.main__table tbody');
            if (tbody) {
                tbody.innerHTML = xhr.response;
            } else {
                console.error("Element '.main__table tbody' not found.");
            }
        } else {
            console.log("status: ", xhr.status);
            if (xhr.status >= 400 && xhr.status < 600) {
                alert(`An error has occurred: ${xhr.status} - ${xhr.statusText}`);
            }
        }
    };
    xhr.open("GET", "php/init.php");
    xhr.send();
});

const clearBtn = document.querySelector('.form__big-btn[type="reset"]');
clearBtn.addEventListener("click", e => {
    e.preventDefault();

    let xhr = new XMLHttpRequest();
    xhr.onloadend = () => {
        if (xhr.status === 200) {
            const tbody = document.querySelector('.main__table tbody');
            if (tbody) {
                tbody.innerHTML = '';
            } else {
                console.error("Element '.main__table tbody' not found.");
            }
        } else {
            console.log("status: ", xhr.status);
            if (xhr.status >= 400 && xhr.status < 600) {
                alert(`An error has occurred: ${xhr.status} - ${xhr.statusText}`);
            }
        }
    };
    xhr.open("POST", "php/clear.php");
    xhr.send();
});

const returnButton = document.getElementById('returnButton');
returnButton.addEventListener('click', () => {
    window.location.href = 'index.html';
});
