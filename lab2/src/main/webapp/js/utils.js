// Define a class for handling a square canvas
class SquareCanvas {

    onclick;

    constructor(target) {
        this.canvas = document.querySelector(target);
        if (this.canvas && this.canvas.getContext) {
            this.ctx = this.canvas.getContext("2d");
            this.ctx.scale(devicePixelRatio, devicePixelRatio);
            this.updateArea();

            this.canvas.addEventListener("click", (e) => {
                let rect = this.canvas.getBoundingClientRect();
                if (this.onclick)
                    this.onclick({x: e.offsetX / rect.width * 100, y: e.offsetY / rect.height * 100});
            })
        }
    }

    updateArea() {
        this.clear();
        let rect = this.canvas.getBoundingClientRect();
        this.canvas.width = rect.width * devicePixelRatio;
        this.canvas.height = rect.width * devicePixelRatio;
        this.canvas.style.height = rect.width + 'px';
        this.scale = this.canvas.width / 100;
    }

    clear() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }

    dot(x, y, color) {
        let prevFS = this.ctx.fillStyle;
        let prevSS = this.ctx.strokeStyle;

        this.ctx.fillStyle = color;
        this.ctx.strokeStyle = color;

        this.ctx.beginPath();
        this.ctx.arc(x * this.scale, y * this.scale, 2, 0, 2 * Math.PI);
        this.ctx.fill();

        this.ctx.fillStyle = prevFS;
        this.ctx.strokeStyle = prevSS;
    }

    line(x1, y1, x2, y2) {
        this.ctx.beginPath();
        this.ctx.moveTo(x1 * this.scale, y1 * this.scale);
        this.ctx.lineTo(x2 * this.scale, y2 * this.scale);
        this.ctx.stroke();
    }

    lineTo({x, y}) {
        this.ctx.lineTo(x * this.scale, y * this.scale);
    }

    fillText(text, x, y, scale = 1) {
        this.ctx.font = `${this.scale * 4 * scale}pt Ferrum, Morice, fantasy`;
        this.ctx.fillText(text, x * this.scale, y * this.scale);
    }
}

// Converts FormData to object
function formDataToObject(formData = new FormData()) {
    let object = {};
    formData.forEach((value, key) => {
        if(!Reflect.has(object, key)){
            object[key] = value;
            return;
        }
        if(!Array.isArray(object[key])){
            object[key] = [object[key]];
        }
        object[key].push(value);
    });
    return object;
}

// Converts object to FormData
function objectToFormData(object) {
    const formData = new FormData();
    Object.keys(object).forEach(key => formData.append(key, object[key]));
    return formData;
}

// GET XMLHttpRequest wrapper
function get_request(link, event) {
    let xhr = new XMLHttpRequest();
    if (event) {
        xhr.onloadend = () => {
            if (xhr.status === 200) {
                event(xhr.response);
                draw(gather())
            } else {
                console.log("status: ", xhr.status);
                if (xhr.status >= 400 && xhr.status < 600) {
                    errorMessageBox.textContent = `An error has occurred: status ${xhr.status} - ${xhr.statusText}`;
                } else if (xhr.status === 0) {
                    errorMessageBox.textContent = `An error has occurred: Server is down`;
                }
                dotArray.pop()
            }
        }
    }
    xhr.open("GET", link);
    xhr.send();
}

// POST XMLHttpRequest wrapper
function post_request(link, event, data = {}) {
    let xhr = new XMLHttpRequest()
    if (event)
        xhr.onloadend = () => event(xhr.response);
    xhr.open("POST", link)

    let formData = objectToFormData(data);
    xhr.send(formData)
}
