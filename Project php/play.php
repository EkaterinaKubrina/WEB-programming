<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/css/style.css">
<title>Играть</title>
</head>
<body>
    <?php include('templates/header.php'); ?>
  <div class="checkers">
  <table class="checkers-board">
            <tbody>
                <tr>
                    <th></th>
                    <th>a</th>
                    <th>b</th>
                    <th>c</th>
                    <th>d</th>
                    <th>e</th>
                    <th>f</th>
                    <th>g</th>
                    <th>h</th>
                </tr>
                <tr>
                    <th>8</th>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                </tr>
                <tr>
                    <th>7</th>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                </tr>
                <tr>
                    <th>6</th>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                    <td class="light"></td>
                    <td class="dark">⚫</td>
                </tr>
                <tr>
                    <th>5</th>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                </tr>
                <tr>
                    <th>4</th>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                    <td class="light"></td>
                    <td class="dark"></td>
                </tr>
                <tr>
                    <th>3</th>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                </tr>
                <tr>
                    <th>2</th>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                </tr>
                <tr>
                    <th>1</th>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                    <td class="dark"></td>
                    <td class="light">⚪</td>
                </tr>
            </tbody>
        </table>

<div class="records">
<br>
<h1>Запись ходов</h1>
</div>


</div>


   <?php include('templates/footer.php'); ?>
</body>
</html>