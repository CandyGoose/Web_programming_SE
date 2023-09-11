const canvas = document.getElementById("graph"),
    ctx = canvas.getContext('2d');

canvas.height = canvas.width;
let w = canvas.width, h = canvas.height;

const hatchWidth = 20 / 2;
const hatchGap = 56;

let rValue = 'R';
function redrawGraph(r) {
    ctx.clearRect(0, 0, w, h);

    ctx.lineWidth = 2;
    ctx.strokeStyle = 'black';

    // y axis
    ctx.beginPath();
    ctx.moveTo(w / 2, 0);
    ctx.lineTo(w / 2 - 10, 15);
    ctx.moveTo(w / 2, 0);
    ctx.lineTo(w / 2 + 10, 15);
    ctx.moveTo(w / 2, 0);
    ctx.lineTo(w / 2, h);
    ctx.stroke();
    ctx.closePath();

    // x axis
    ctx.beginPath();
    ctx.moveTo(w, h / 2);
    ctx.lineTo(w - 15, h / 2 - 10);
    ctx.moveTo(w, h / 2);
    ctx.lineTo(w - 15, h / 2 + 10);
    ctx.moveTo(w, h / 2);
    ctx.lineTo(0, h / 2);
    ctx.stroke();
    ctx.closePath();

    ctx.beginPath();
    ctx.moveTo(w / 2 - hatchWidth, h / 2 - hatchGap);
    ctx.lineTo(w / 2 + hatchWidth, h / 2 - hatchGap);
    ctx.moveTo(w / 2 - hatchWidth, h / 2 - hatchGap * 2);
    ctx.lineTo(w / 2 + hatchWidth, h / 2 - hatchGap * 2);
    ctx.moveTo(w / 2 - hatchWidth, h / 2 + hatchGap);
    ctx.lineTo(w / 2 + hatchWidth, h / 2 + hatchGap);
    ctx.moveTo(w / 2 - hatchWidth, h / 2 + hatchGap * 2);
    ctx.lineTo(w / 2 + hatchWidth, h / 2 + hatchGap * 2);
    ctx.moveTo(w / 2 - hatchGap, h / 2 - hatchWidth);
    ctx.lineTo(w / 2 - hatchGap, h / 2 + hatchWidth);
    ctx.moveTo(w / 2 - hatchGap * 2, h / 2 - hatchWidth);
    ctx.lineTo(w / 2 - hatchGap * 2, h / 2 + hatchWidth);
    ctx.moveTo(w / 2 + hatchGap, h / 2 - hatchWidth);
    ctx.lineTo(w / 2 + hatchGap, h / 2 + hatchWidth);
    ctx.moveTo(w / 2 + hatchGap * 2, h / 2 - hatchWidth);
    ctx.lineTo(w / 2 + hatchGap * 2, h / 2 + hatchWidth);
    ctx.stroke();
    ctx.closePath();

    ctx.fillStyle = '#236BF155';
    ctx.beginPath();
    ctx.moveTo(w / 2, h / 2);
    ctx.lineTo(w / 2 + hatchGap * 2, h / 2);
    ctx.lineTo(w / 2, h / 2 + hatchGap);
    ctx.lineTo(w / 2, h / 2);
    ctx.lineTo(w / 2 - hatchGap, h / 2);
    ctx.arc(w / 2, h / 2, hatchGap, Math.PI, - Math.PI / 2, false);
    ctx.lineTo(w / 2, h / 2);
    ctx.lineTo(w / 2, h / 2 - hatchGap * 2);
    ctx.lineTo(w / 2 + hatchGap, h / 2 - hatchGap * 2);
    ctx.lineTo(w / 2 + hatchGap, h / 2);
    ctx.lineTo(w / 2, h / 2);
    ctx.fill();
    ctx.strokeStyle = '#3E23F1'
    ctx.stroke();
    ctx.closePath();

    const fontSize = hatchGap / 3.5
    ctx.fillStyle = 'black'

    ctx.font = `500 ${fontSize * 1.4}px Ferrum`;
    ctx.fillText('y', w / 2 - hatchWidth * 2.8, 15)
    ctx.fillText('x', w - 20, h / 2 - hatchWidth * 2.4)

    let label1, label2;
    if (isNaN(r)) {
        label1 = r + '/2'
        label2 = r
    } else {
        label1 = r / 2
        label2 = r
    }
    rValue = label2

    ctx.font = `500 ${fontSize}px Ferrum`;
    ctx.fillText(label1, w / 2 + hatchGap - 3, h / 2 + hatchWidth * 2.8);
    ctx.fillText(label2, w / 2 + hatchGap * 2 - 3, h / 2 + hatchWidth * 2.8);
    ctx.fillText('-' + label1, w / 2 - hatchGap - 7, h / 2 + hatchWidth * 2.8);
    ctx.fillText('-' + label2, w / 2 - hatchGap * 2 - 7, h / 2 + hatchWidth * 2.8);

    ctx.fillText(label1, w / 2 + hatchWidth * 2, h / 2 - hatchGap + 3);
    ctx.fillText(label2, w / 2 + hatchWidth * 2, h / 2 - hatchGap * 2 + 3);
    ctx.fillText('-' + label1, w / 2 + hatchWidth * 2, h / 2 + hatchGap + 3);
    ctx.fillText('-' + label2, w / 2 + hatchWidth * 2, h / 2 + hatchGap * 2 + 3);
}

redrawGraph(rValue);

function printDotOnGraph(xCenter, yCenter, isHit) {
    redrawGraph(rValue);
    ctx.fillStyle = isHit ? '#1AFF00' : '#ff0000'
    let x = w / 2 + xCenter * hatchGap * (2 / rValue);
    let y = h / 2 - yCenter * hatchGap * (2 / rValue);
    const radius = 3; 
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, 2 * Math.PI); 
    ctx.fill();
    ctx.closePath();
}

