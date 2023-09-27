const sc = new SquareCanvas("#graph");
const tm = document.querySelector("#history")
const form = document.querySelector("#request-form");

// Gather data form our form
function gather() {
    let data = formDataToObject(new FormData(form));
    data.y = data.y.replace(",", ".")
    if (Array.isArray(data.r)) {
        data.r = data.r[0];
    }
    return data
}

// Draw the graph with labels
function draw({r}) {
    sc.updateArea();
    sc.ctx.lineWidth = 3;

    // Define functions for upper and lower parts of the "batman" curve
    function batman_upper(x) {
        x = Math.abs(x);
        if (x < 0.5) {
            return 2.25;
        } else if (0.5 <= x && x < 0.75) {
            return 3 * x + 0.75;
        } else if (0.75 <= x && x < 1.0) {
            return 9 - 8 * x;
        } else if (1 <= x && x < 3) {
            return (1.5 - 0.5 * x - 3 * Math.sqrt(10) / 7 * (Math.sqrt(3 - x ** 2 + 2 * x) - 2));
        } else if (3 <= x && x <= 7) {
            return 3 * Math.sqrt(-((x / 7) ** 2) + 1);
        }
    }

    function batman_lower(x) {
        x = Math.abs(x);
        if (0 <= x && x < 4) {
            return (Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x ** 2 +
                Math.sqrt(1 - (Math.abs(x - 2) - 1) ** 2) - 3);
        } else if (4 <= x && x <= 7) {
            return -3 * Math.sqrt(-((x / 7) ** 2) + 1);
        }
    }

    const xValues = Array.from({ length: 1400 }, (_, i) => -7 + (i / 100));
    const yUpperValues = xValues.map(batman_upper);
    const yLowerValues = xValues.map(batman_lower);
    const xyMax = 8.8;

    sc.ctx.beginPath();
    sc.ctx.moveTo(sc.canvas.width / 9.5, sc.canvas.height / 2);

    for (let i = 0; i < xValues.length; i++) {
        const x = (xValues[i] + xyMax) / (2 * xyMax) * sc.canvas.width;
        const y = (1 - (yUpperValues[i] + xyMax) / (2 * xyMax)) * sc.canvas.height;
        sc.ctx.lineTo(x, y);
    }

    for (let i = xValues.length - 1; i >= 0; i--) {
        const x = (xValues[i] + xyMax) / (2 * xyMax) * sc.canvas.width;
        const y = (1 - (yLowerValues[i] + xyMax) / (2 * xyMax)) * sc.canvas.height;
        sc.ctx.lineTo(x, y);
    }
    sc.ctx.fillStyle = '#70296355';
    sc.ctx.fill();
    sc.ctx.strokeStyle = '#702963'
    sc.ctx.stroke();
    sc.ctx.closePath();
    sc.ctx.lineWidth = 1;

    let R = (isNaN(r / 1)) ? "R" : r;
    let R2 = (isNaN(r / 2)) ? "R/2" : r / 2;

    sc.line(0, 50, 100, 50); // Ox
    sc.line(50, 0, 50, 100); // Oy

    sc.line(10, 48.5, 10, 51.5); // | -R
    sc.fillText(`-${R}`, 11, 48.5, 0.8);

    sc.line(30, 48.5, 30, 51.5); // | -R/2
    sc.fillText(`-${R2}`, 31, 48.5, 0.8);

    sc.line(90, 48.5, 90, 51.5); // | R
    sc.fillText(`${R}`, 91, 48.5, 0.8);

    sc.line(70, 48.5, 70, 51.5); // | R/2
    sc.fillText(`${R2}`, 71, 48.5, 0.8);

    sc.line(48.5, 10, 51.5, 10); // - R
    sc.fillText(`${R}`, 52, 11, 0.8);

    sc.line(48.5, 30, 51.5, 30); // - R/2
    sc.fillText(`${R2}`, 52, 31, 0.8);

    sc.line(48.5, 70, 51.5, 70); // - -R/2
    sc.fillText(`-${R2}`, 52, 71, 0.8);

    sc.line(48.5, 90, 51.5, 90); // - -R
    sc.fillText(`-${R}`, 52, 91, 0.8);

    sc.line(48.5, 3, 50, 0);  // /\
    sc.line(51.5, 3, 50, 0);  // ||
    sc.fillText("y", 45, 4);

    sc.line(97, 51.5, 100, 50);
    sc.line(97, 48.5, 100, 50); // ->
    sc.fillText("x", 95, 47);
    dotArray.forEach((dot, index) => {
        sc.fillText(`${dotArray.length-index}`, dot.x+0.5, dot.y-0.5, 0.8);
        sc.dot(dot.x, dot.y, "#000000")
    })
}

let dotArray = [];

// Validate dot (depending on source), then sends "GET" request, after which updates the content of table
function submit({x, y, r}, graphMode = false) {
    const check = (e, condition) => {
        (condition) ? e.style.color = "black" : e.style.color = "red";
        return condition;
    }
    let valid = check(document.querySelector("#r-label"), r && !isNaN(r));
    if (!graphMode) {
        valid = check(document.querySelector("#y-label"), y !== '' && !isNaN(y) && y.slice(0, 17) > -3 && y.slice(0, 17) < 5) && valid;
        valid = check(document.querySelector("#x-label"), x !== '' && !isNaN(x)) && valid;
    } else {
        document.querySelector("#y-label").style.color = "black";
        document.querySelector("#x-label").style.color = "black";
    }
    if (valid) {
        const queryString = `x=${encodeURIComponent(x)}&y=${encodeURIComponent(y)}&r=${encodeURIComponent(r)}&function=check&dot=${graphMode}`;
        const url = `${contextPath}?${queryString}`;
        if (!graphMode) {
            dotArray.push({ x: parseFloat(x)*40/r+50, y: -parseFloat(y)*40/r+50 });
        }
        get_request(url, (html) => {
            tm.innerHTML = html;
            window.location.href = "result.jsp";
        });
    }
    return valid;
}


// Sends request on click
sc.onclick = (e) => {
    if (rValid) {
        rRadios.forEach(rRadio => rRadio.setCustomValidity(''));
        let dot = gather();
        dot.x = (e.x / 100 - 0.5) * 10 / 4 * dot.r;
        dot.y = (-e.y / 100 + 0.5) * 10 / 4 * dot.r;
        if (submit(dot, true)) {
            dotArray.push({x: e.x, y: e.y})
        }
    } else {
        rRadios.forEach(rRadio => rRadio.setCustomValidity('Check the value.'));
    }
    rRadios[0].reportValidity();
}


let oldR = 1
// Draw on load
window.addEventListener("load", () => {
    let dataFromLocalStorage = localStorage.getItem("dots");
    if (dataFromLocalStorage) {
        let parsedData = JSON.parse(dataFromLocalStorage);
        if (parsedData.hasOwnProperty("r")) {
            oldR = parsedData.r;
        }
        if (parsedData.hasOwnProperty("dots")) {
            dotArray = parsedData.dots;
        }
    }
    draw(gather());
});

// Save data on page unload
window.addEventListener("unload", () => {
    const dataToSave = {
        r: oldR,
        dots: dotArray
    };
    localStorage.setItem("dots", JSON.stringify(dataToSave));
})

// Redraw on resize
window.addEventListener("resize", () => draw(gather()));

// Handle changes to the radius (r) input
form.querySelectorAll("input[name=r]").forEach(input => {
    input.addEventListener("change", () => {
        if (rValid) {
            const newR = input.value;
            dotArray.forEach(dot => {
                dot.x = (dot.x-50) * oldR / newR + 50;
                dot.y = (dot.y-50) * oldR / newR + 50;
            });
            oldR = newR;
            draw(gather());
        }
    });
});

// Send "clear" request
document.querySelector("#clear-request")
    .addEventListener("click", () =>
        post_request(`${contextPath}`, (content) => {
            tm.innerHTML = content
            dotArray = []
            draw(gather())
        }, {function: "clear"}));

// Send "check" submit on submit
form.addEventListener("submit", (e) => {
    e.preventDefault();
    submit(gather())
})
