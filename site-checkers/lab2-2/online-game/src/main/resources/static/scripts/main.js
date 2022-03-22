var board = [[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
];;

const boardExample = [[1, 0, 1, 0, 0, 0, 2, 0],
[0, 1, 0, 0, 0, 2, 0, 2],
[1, 0, 1, 0, 0, 0, 2, 0],
[0, 1, 0, 0, 0, 2, 0, 2],
[1, 0, 1, 0, 0, 0, 2, 0],
[0, 1, 0, 0, 0, 2, 0, 2],
[1, 0, 1, 0, 0, 0, 2, 0],
[0, 1, 0, 0, 0, 2, 0, 2],
]; //0-пусто, 1 - белые, 2 -черные, 3 - белые дамки, 4 - черные дамки


const boardExample1 = [[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 2],
[4, 0, 0, 0, 2, 0, 2, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 2, 0],
[0, 0, 0, 1, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 1, 0, 2, 0, 0],
];


let text = "";
var idGame;
var statusGame;

var promptMode = { //режим подсказки
    "on": false,
    "checker": [0, 0],
};

var normalMode = {
    "redFlag": false,
    "checkerKill": [],
    "oneMoveFlag": false,
    "num": 1,
    "nextMove": 1
};

var moveMode = {
    "on": false,
    "from": [],
    "to": [],
    "red": false,
    "dead": [],
};

class MyError {
    constructor(message) {
        this.name = 'MyError';
        this.message = message || 'Сообщение по умолчанию';
        this.stack = (new Error()).stack;
    }
}
MyError.prototype = Object.create(Error.prototype);


function Timer(onstep, onmaxstep, maxstep, interval = 1000) {
    let bite = 0;
    let timerObj = null;
    function biter() {
        if (bite > maxstep) {
            clearInterval(timerObj);
            timerObj = null;
            onmaxstep();
        } else {
            onstep(bite);
        }
        bite += 1;
    }
    this.stop = function() {
        if (timerObj) {
            clearInterval(timerObj);
            timerObj = null;
        }
        return this;
    }
    this.start = function() {
        if (!timerObj) {
            this.stop();
            bite = 0;
            timerObj = setInterval(biter, interval);
        }
        return this;
    }
    this.reset = function(setmaxstep = maxstep, setinterval = interval) {
        maxstep = setmaxstep;
        interval = setinterval;
        return this.stop().start();
    }
}

let maxstep = 9;

function onstep(step) {
    const timerstep = maxstep - step;

    let minutes = Math.floor(timerstep / 60);
    let seconds = timerstep - (minutes * 60);

    if (minutes < 10) { minutes = "0"+minutes; }
    if (seconds < 10) { seconds = "0"+seconds; }

    const timer = document.querySelector('.timer');
    timer.querySelector('.min').textContent = minutes;
    timer.querySelector('.delimiter').textContent = ":";
    timer.querySelector('.sec').textContent = seconds;

    if (timerstep < 15) {
        timer.classList.add("red");
    } else if (timerstep > 15) {
        timer.classList.remove("red");
    }
}

function onmaxstep() {
    console.log("stop");
    end();
}

const timer = new Timer(onstep, onmaxstep, maxstep);



function doMove(a, b) {
    try {
        whatIsIt(a, b);
    }
    catch (e) {
        if (e.name == 'MyError') {
            console.log(e.message);
        }
        else {
            throw e;
        }
    }
}

function whatIsIt(a, b) {
    if (!moveMode.on) { //если не вкл режим хода
        if (!promptMode.on) { //если не вкл режим подсказки
            if (board[a][b] != 0) {
                if (board[a][b] == normalMode.nextMove || board[a][b] == normalMode.nextMove + 2) {
                    if (normalMode.redFlag) {  //если есть шашки, которые нужно бить
                        if (normalMode.checkerKill.includes("" + a + b)) {
                            markSquaresCaptureMove(a, b); //раскрасить шашки для хода
                            promptMode.on = true;
                            promptMode.checker[0] = a;
                            promptMode.checker[1] = b;
                            document.getElementById("" + a + b).style.backgroundColor = '#c3c35d';
                        }
                        else {
                            console.log("ЧекКил= " + normalMode.checkerKill)
                            console.log("Шашка= " + a + b)
                            throw new MyError("Выбрана шашка которая не может бить");
                        }
                    }
                    else {
                        if (markSquaresMove(a, b)) {
                            promptMode.on = true;
                            promptMode.checker[0] = a;
                            promptMode.checker[1] = b;
                            document.getElementById("" + a + b).style.backgroundColor = '#c3c35d';
                        }
                        else {
                            throw new MyError("Выбрана шашка которой не куда ходить");
                        }
                    }

                }

                else {
                    throw new MyError("Ход у шашки другого цвета");
                }

            }
            else {
                throw new MyError("Выбрана пустая клетка");
            }
        }

        else {
            const sq = document.getElementById("" + a + b);
            let backgroundColor = sq.style.backgroundColor;

            if (backgroundColor == "rgb(195, 195, 93)") { //желтый
                exitModePrompt(); //выход из режима подсказки
                promptMode.on = false;
            }

            else if (backgroundColor == "rgb(93, 195, 126)") { //зеленый
                visibleMoveMode();
                moveMode.on = true;
                moveMode.red = false;
                moveMode.from = [promptMode.checker[0], promptMode.checker[1], board[promptMode.checker[0]][promptMode.checker[1]]];
                makeMove(promptMode.checker[0], promptMode.checker[1], a, b);
                moveMode.to = [a, b, board[a][b]];
            }

            else if (backgroundColor == "rgb(195, 93, 93)") { //красный
                visibleMoveMode();
                moveMode.on = true;
                moveMode.red = true;
                moveMode.from = [promptMode.checker[0], promptMode.checker[1], board[promptMode.checker[0]][promptMode.checker[1]]];
                makeRedMove(promptMode.checker[0], promptMode.checker[1], a, b);
                moveMode.to = [a, b, board[a][b]];
            }

            else {
                throw new MyError("В режиме подсказки выбрана не подсвеченная клетка");
            }

        }


    }

}


function markSquaresMove(a, b) { //функция вовзращает true, если у шашки есть доступные поля для хода и подсвечивает эти поля
    let responseServer = get('/play/' + idGame + '/move/available?a=' + a + '&b=' + b);

    if (responseServer != "") {
        for (let i = 0; i < responseServer.length; i += 2) {
            document.getElementById("" + responseServer[i] + responseServer[i + 1]).style.backgroundColor = '#5dc37e';
        }
        return true;
    }
    return false;
}

function markSquaresCaptureMove(a, b) { //функция вовзращает true, если у шашки есть доступные поля для хода и подсвечивает эти поля (Красные ходы)
    let responseServer = get('/play/' + idGame + '/move/red?a=' + a + '&b=' + b);

    if (responseServer != "") {
        for (let i = 0; i < responseServer.length; i += 2) {
            document.getElementById("" + responseServer[i] + responseServer[i + 1]).style.backgroundColor = '#c35d5d';
        }
        return true;
    }
    return false;
}

function squaresCaptureMove(a, b) { //функция вовзращает true, если у шашки есть доступные поля для хода (Красные ходы)
    let responseServer = get('/play/' + idGame + '/move/red?a=' + a + '&b=' + b);

    if (responseServer != "") {
        return true;
    }
    return false;
}


function exitModePrompt() { //выход из режима подсказки
    const darks = document.querySelectorAll('.dark');
    for (let i = 0; i < darks.length; i++) {
        darks[i].style.backgroundColor = '#4D4847';
    }
}


function makeMove(a, b, newA, newB) { //тихий ход
    document.getElementById("" + a + b).textContent = '';
    move(newA, newB, board[a][b]);
    board[a][b] = 0;
}

function makeRedMove(a, b, newA, newB) { //ударный ход
    var i, j = 0;

    if (a < newA) {
        if (b < newB) {
            i = 1;
            j = 1;
        }
        else {
            i = 1;
            j = -1;
        }
    }
    else {
        if (b < newB) {
            i = -1;
            j = 1;
        }
        else {
            i = -1;
            j = -1;
        }
    }

    let i1 = i;
    let j1 = j;
    while (board[a + i1][b + j1] == 0) {
        i1 += i;
        j1 += j;
    }
    document.getElementById("" + (a + i1) + (b + j1)).textContent = '';
    moveMode.dead[0] = a + i1;
    moveMode.dead[1] = b + j1;
    moveMode.dead[2] = board[a + i1][b + j1];
    board[a + i1][b + j1] = 0;

    makeMove(a, b, newA, newB);
}


function move(a, b, color) { //ставит шашку
    let sq = document.getElementById("" + a + b);
    if (color == 1 || color == 3) {
        if (b != 7 && color == 1) {
            board[a][b] = 1;
            sq.textContent = '⚪';
        } else {
            sq.textContent = '🤍';
            board[a][b] = 3;
        }
    }
    else {
        if (b != 0 && color == 2) {
            board[a][b] = 2;
            sq.textContent = '⚫';
        }
        else {
            sq.textContent = '🖤';
            board[a][b] = 4;
        }

    }

}


function show(mode) { //показывает нужную расстановку, mode = 1 - начало, mode = 2 - пример
    exitModePrompt(); //выход из режима подсказки
    hiddenMoveMode(); //кнопки отменить и подтвердить прозрачные
    moveMode.on = false;
    promptMode.on = false;
    normalMode.oneMoveFlag = false;

    if (mode == 1) {
        paint(boardExample);
        changeArrangement(boardExample);
        normalMode.nextMove = 1;
        normalMode.num = 1;
        whiteMode();

        let body = JSON.stringify({ "board": board, "records": text, "status": 1 });
        idGame = post(body, 201, "/play?mode=1");
    }
    else {
        paint(boardExample1);
        changeArrangement(boardExample1);
        normalMode.nextMove = 2;
        normalMode.num = 1;
        blackMode();

        let body = JSON.stringify({ "board": board, "records": text, "status": 1 });
        idGame = post(body, 201, "/play?mode=2");
    }


    checkRed();
    clearRecordsMove();
    maxstep = 120;
    timer.reset(maxstep);
}

function paint(boardExample) {
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (boardExample[i][j] == 0) {
                document.getElementById("" + i + j).textContent = '';
            }
            else if (boardExample[i][j] == 1) {
                document.getElementById("" + i + j).textContent = '⚪';
            }
            else if (boardExample[i][j] == 2) {
                document.getElementById("" + i + j).textContent = '⚫';
            }
            else if (boardExample[i][j] == 3) {
                document.getElementById("" + i + j).textContent = '🤍';
            }
            else {
                document.getElementById("" + i + j).textContent = '🖤';
            }
        }
    }
}

function changeArrangement(boardParam) { //изменить расстановку
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            board[i][j] = boardParam[i][j];
        }
    }
}

function post(body, statusResponse, url) { //Пост запрос к серверу
    let xhr = new XMLHttpRequest();
    xhr.open('POST', url, false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.send(body);
    if (xhr.status != statusResponse) {
        alert(xhr.status + ': ' + xhr.statusText);
    } else {
        return xhr.responseText;
    }
}

function get(url) { //Гет запрос к серверу
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.send();
    if (xhr.status != 200) {
        alert(xhr.status + ': ' + xhr.statusText);
    } else {
        return xhr.responseText;
    }
}

function put(body, statusResponse, url) { //Пут запрос к серверу
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', url, false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.send(body);
    if (xhr.status != statusResponse) {
        alert(xhr.status + ': ' + xhr.statusText);
    } else {
        return xhr.responseText;
    }
}

function end() { //сдаться
    timer.stop();
    statusGame = put(null, 200, '/play/' + idGame );
    addMove();
    alert("Партия была закончена. Спасибо за игру!");

}



function checkRed() { //обновляет список шашек, которые должны побить в этом ходе
    normalMode.checkerKill = [];
    let responseServer = get('/play/' + idGame + '/move');
    console.log("Шашки, которые могут бить: " + responseServer);
    if (responseServer != "[]") {
        let str = responseServer.replace(/[^0-9]/g, '');
        for(let i = 0; i< str.length; i+=2){
            normalMode.checkerKill.push(str[i] + str[i+1]);
        }
        normalMode.redFlag = true;
    }
    else {
        normalMode.redFlag = false;
    }

}

function confirmMove() { //подтвердить ход
    exitModePrompt(); //выход из режима подсказки

    let body = JSON.stringify({
        "newA": moveMode.to[0], "newB": moveMode.to[1],
        "a": moveMode.from[0], "b": moveMode.from[1], "checker": moveMode.from[2], "redMove": moveMode.red
    });

    let moveContinue = put(body, 200, "/play/" + idGame + "/move");

    if (moveContinue == 2) {
        normalMode.oneMoveFlag = true;
        normalMode.checkerKill = [];
        normalMode.redFlag = true;
        normalMode.checkerKill.push("" + moveMode.to[0] + moveMode.to[1]);
        markSquaresCaptureMove(moveMode.to[0], moveMode.to[1]);
        promptMode.on = true;
        promptMode.checker[0] = moveMode.to[0];
        promptMode.checker[1] = moveMode.to[1];
        document.getElementById("" + moveMode.to[0] + moveMode.to[1]).style.backgroundColor = '#c3c35d';
        maxstep = 120;
        timer.reset(maxstep);
        addMove();
    }
    else if (moveContinue == 1){
        normalMode.oneMoveFlag = false;
        promptMode.on = false;
        if (normalMode.nextMove == 1) {
            normalMode.nextMove = 2;
            blackMode();
        }
        else {
            normalMode.nextMove = 1;
            whiteMode();
            normalMode.num++;
        }
        checkRed();
        maxstep = 120;
        timer.reset(maxstep);
        addMove();
    }
    else{
    end();
    }

    hiddenMoveMode();
    moveMode.on = false;

}

function backMove() { //отменить ход
    if (moveMode.red) {
        move(moveMode.dead[0], moveMode.dead[1], moveMode.dead[2]);
    }
    move(moveMode.from[0], moveMode.from[1], moveMode.from[2]);
    board[moveMode.to[0]][moveMode.to[1]] = 0;
    document.getElementById("" + moveMode.to[0] + moveMode.to[1]).textContent = '';
    hiddenMoveMode();
    moveMode.on = false;

}

function hiddenMoveMode() { //делает прозрачным кнопки отменить и подтвердить
    document.getElementById("c1").style.visibility = "hidden";
    document.getElementById("c2").style.visibility = "hidden";
}

function visibleMoveMode() { //делает видимым кнопки отменить и подтвердить
    document.getElementById("c1").style.visibility = "visible";
    document.getElementById("c2").style.visibility = "visible";
}

function whiteMode() { //указывает чей ход
    document.getElementById("colorMove").textContent = 'ход ⚪';

}

function blackMode() { //указывает чей ход
    document.getElementById("colorMove").textContent = 'ход ⚫';

}

function addMove() { //добавляет ход в запись ходов
        let responseServer = get('/play/' + idGame);
        clearRecordsMove();
        let arrayString = responseServer.split('\n');
        for(let i = 0; i < arrayString.length; i++){
           addText(arrayString[i]);
        }


}

function addText(t) { //вспомогательная функция для добавления хода на страницу
    let p = document.createElement("h6");
    let t1 = document.createTextNode(t);
    p.appendChild(t1);
    document.getElementById("p1").appendChild(p);
}

function clearRecordsMove() { //Очищает поле записи партии
    let div = document.getElementById("p1");
    while (div.childElementCount != 0) {
        div.removeChild(div.firstChild);
    }
}


function numOfLetter(x) {
    if (x == 'a') {
        return 0;
    }
    else if (x == 'b') {
        return 1;
    }
    else if (x == 'c') {
        return 2;
    }
    else if (x == 'd') {
        return 3;
    }
    else if (x == 'e') {
        return 4;
    }
    else if (x == 'f') {
        return 5;
    }
    else if (x == 'g') {
        return 6;
    }
    else if (x == 'h') {
        return 7;
    }
}


function newPose(f) {
    document.getElementById("error").textContent = "";
    show(1);
    let str = f.message.value;
    let strMas = str.split("\n");
    let n;
    let delimiter;

    for (let i = 0; i < strMas.length; i++) {
        try {
            if (strMas[i].startsWith("" + normalMode.num + ".")) {
                n = 2;

                whatIsIt(numOfLetter(strMas[i].substring(n, n + 1)), parseInt(strMas[i].substring(n + 1, n + 2)) - 1);

                delimiter = strMas[i].substring(n + 2, n + 3);

                if (delimiter == ':') {
                    whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                    confirmMove();
                    while (moveMode.red && squaresCaptureMove(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1)) {
                        n += 3;
                        whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                        confirmMove();
                    }
                }

                else if (delimiter == "-") {
                    whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                    confirmMove();
                }

            }
            else if (strMas[i] == "" || strMas[i] == " " || strMas[i] == "\n") {
                break;
            }
            else {
                document.getElementById("error").textContent = "Ошибка! Проверьте нумерацию ходов. (Строка " + (i + 1) + ")";
                break;
            }

            if (strMas[i].substring(n + 5, n + 6) == " " && strMas[i].substring(n + 6, n + 7) != "") {
                n = n + 6;

                whatIsIt(numOfLetter(strMas[i].substring(n, n + 1)), parseInt(strMas[i].substring(n + 1, n + 2)) - 1);

                delimiter = strMas[i].substring(n + 2, n + 3);

                if (delimiter == ':') {
                    whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                    confirmMove();
                    while (moveMode.red && squaresCaptureMove(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1)) {
                        n += 3;
                        whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                        confirmMove();
                    }
                }

                else if (delimiter == "-") {
                    whatIsIt(numOfLetter(strMas[i].substring(n + 3, n + 4)), parseInt(strMas[i].substring(n + 4, n + 5)) - 1);
                    confirmMove();
                }

            }
            else if (strMas[i].substring(n + 5, n + 6) == "" || strMas[i].substring(n + 5, n + 6) == "\n" || strMas[i].substring(n + 5, n + 6) == " ") {
                break;
            }
            else {
                document.getElementById("error").textContent = "Ошибка! Между ходами белых и черных шашек должен стоять пробел. (Строка " + (i + 1) + ")";
                break;
            }
        }
        catch (e) {
            document.getElementById("error").textContent = "Ошибка! " + e.message + " (Строка " + (i + 1) + ")";
            console.log(e);
        }
    }

}

