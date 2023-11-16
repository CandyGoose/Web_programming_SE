import {useEffect, useRef} from "react";

const ChartView = (
    {width, height, minX, maxX, minY, maxY, radius, items, onClickChart}
) => {
    const canvasRef = useRef(null)

    // Dimensions
    const getWidth = () => { return width }
    const getHeight = () => { return height }
    const getScaleFactorX = () => { return getWidth() / (maxX - minX) }
    const getScaleFactorY = () => { return getHeight() / (maxY - minY)}

    // Position Extracting
    const getCursorPosition = (event) => {
        const rect = canvasRef.current.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        return { x: x, y: y };
    }

    // Coordinate - Position Converting
    const positionToCoordinate = (position) => {
        const offsetX = position.x / getScaleFactorX();
        const offsetY = position.y / getScaleFactorY();
        const x = minX + offsetX;
        const y = maxY - offsetY;
        return { x: x, y: y };
    }

    const coordinateToPosition = (coordinate) => {
        const offsetX = coordinate.x - minX;
        const offsetY = maxY - coordinate.y;
        const x = offsetX * getScaleFactorX();
        const y = offsetY * getScaleFactorY();
        return { x: x, y: y };
    }

    // Drawing
    const clearCanvas = (context) => {
        context.clearRect(0, 0, getWidth(), getWidth())
    }

    const drawBoard = (context) => {
        context.strokeStyle = '#000'
        context.beginPath();
        context.lineWidth = 0.5;
        for (let x = getScaleFactorX(); x < getWidth(); x += getScaleFactorX()) {
            context.moveTo(x, 0);
            context.lineTo(x, getHeight());
        }
        for (let y = getScaleFactorY(); y < getHeight(); y += getScaleFactorY()) {
            context.moveTo(0, y);
            context.lineTo(getWidth(), y);
        }
        context.stroke();


        context.lineWidth = 2;
        context.strokeStyle = 'black';

        // y axis
        context.beginPath();
        context.moveTo(getWidth() / 2, 2);
        context.lineTo(getWidth() / 2 - 10, 15);
        context.moveTo(getWidth() / 2, 2);
        context.lineTo(getWidth() / 2 + 10, 15);
        context.moveTo(getWidth() / 2, 2);
        context.lineTo(getWidth() / 2, getHeight());
        context.stroke();
        context.closePath();

        // x axis
        context.beginPath();
        context.moveTo(getWidth() - 2, getHeight() / 2);
        context.lineTo(getWidth() - 15, getHeight() / 2 - 10);
        context.moveTo(getWidth() - 2, getHeight() / 2);
        context.lineTo(getWidth() - 15, getHeight() / 2 + 10);
        context.moveTo(getWidth() - 2, getHeight() / 2);
        context.lineTo(0, getHeight() / 2);
        context.stroke();
        context.closePath();
    }

    const drawShape = (context, radius) => {
        context.beginPath()
        context.fillStyle = '#236BF155'
        context.strokeStyle = '#3E23F1'
        context.lineWidth = 1.0
        context.beginPath()

        let nextPos = coordinateToPosition({x: 0, y: 0})
        context.moveTo(nextPos.x, nextPos.y)

        nextPos = coordinateToPosition({ x: 0, y: -radius/2.0 })
        context.lineTo(nextPos.x, nextPos.y)

        nextPos = coordinateToPosition({ x: -radius, y: 0 })
        context.lineTo(nextPos.x, nextPos.y)


        nextPos = coordinateToPosition({ x: -radius/2.0, y: 0 })
        context.lineTo(nextPos.x, nextPos.y)

        nextPos = coordinateToPosition({ x: -radius/2.00, y: radius })
        context.lineTo(nextPos.x, nextPos.y)

        nextPos = coordinateToPosition({ x: 0, y: radius })
        context.lineTo(nextPos.x, nextPos.y)

        nextPos = coordinateToPosition({ x: 0, y: 0 })
        if (radius < 0) {
            context.arc(nextPos.x, nextPos.y, getScaleFactorX() * Math.abs(radius), Math.PI / 2, Math.PI);
        } else {
            context.arc(nextPos.x, nextPos.y, getScaleFactorX() * radius, -Math.PI / 2, 0);
        }

        nextPos = coordinateToPosition({ x: 0, y: 0 })
        context.lineTo(nextPos.x, nextPos.y)

        context.fill()
        context.stroke()
    }

    const drawItems = (context, items) => {
        context.beginPath()
        items.forEach(item => {
            const position = coordinateToPosition(item)
            const radius = 5
            context.fillStyle = item.color
            context.beginPath()
            context.arc(position.x, position.y, radius, 0, 2 * Math.PI)
            context.fill()
            context.closePath()
        })
    }

    useEffect(() => {
        const canvas = canvasRef.current
        const context = canvas.getContext('2d')

        clearCanvas(context)
        drawBoard(context)
        drawShape(context, radius)
        drawItems(context, items)
    }, [width, height, minX, maxX, minY, maxY, radius, items])

    const handleOnClick = (event) => {
        const position = getCursorPosition(event)
        const coordinate = positionToCoordinate(position)
        onClickChart(coordinate)
    }

    return <canvas
        width={width}
        height={height}
        ref={canvasRef}
        onClick={handleOnClick}
    />
}

export default ChartView