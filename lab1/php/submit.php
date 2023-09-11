<?php

$x = isset($_GET['x']) ? floatval($_GET['x']) : null;
$r = isset($_GET['r']) ? floatval($_GET['r']) : null;
$y = isset($_GET['y']) ? str_replace(',', '.', $_GET['y']) : null;
if ($y !== null) {
    $decimalPosition = strpos($y, '.');
    if ($decimalPosition !== false) {
        $y = substr($y, 0, $decimalPosition + 16);
    }
}

session_start();

date_default_timezone_set('Europe/Moscow');
$current_time = date("d-m-Y H:i:s");

if (!validate_values($x, $y, $r)) {
    http_response_code(412);
    echo("x={$x}, y={$y}, r={$r}");
    echo("<br>Wrong numbers");
    return;
}

$result = check_area($x, $y, $r) ? "<span class='hit'>Hit</span>" : "<span class='miss'>Miss</span>";

$exec_time = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 5) . ' ms';

$_SESSION['tdata'][] = [$x, $y, $r, $current_time, $exec_time, $result];

function check_area($x, $y, $r) {
    return 
        ($x >= 0 and $y <= 0 and $r >= $x - 2 * $y) // triangle
            or
        ($x >= 0 and $x <= $r / 2 and $y >= 0 and $y <= $r) // square
            or
        ($x <= 0 and $y >= 0 and (pow($x,2) + pow($y,2) <= pow($r / 2, 2))); // circle
}

function validate_values($x, $y, $r) {
    return in_array($x, [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2])
        and (is_numeric($y) and $y > -3 and $y < 5)
        and in_array($r, [1, 1.5, 2, 2.5, 3]);
}
    
foreach ($_SESSION["tdata"] as $rdata) {
    echo <<<HTML
    <tr>
        <td>$rdata[0]</td>
        <td>$rdata[1]</td>
        <td>$rdata[2]</td>
        <td>$rdata[3]</td>
        <td>$rdata[4]</td>
        <td>$rdata[5]</td>
    </tr>
HTML;
} ?>