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




function whatIsIt(a, b) {
    if (!moveMode.on) {
        if (!promptMode.on) { //если не вкл режим подсказки
            if (board[a][b] != 0) {
                if (board[a][b] == normalMode.nextMove || board[a][b] == normalMode.nextMove + 2) {
                    if (normalMode.redFlag) {  //если есть шашки, которые нужно бить
                        if (normalMode.checkerKill.includes("" + a + b)) {
                            markSquaresCaptureMove(a, b);
                            promptMode.on = true;
                            promptMode.checker[0] = a;
                            promptMode.checker[1] = b;
                            document.getElementById("" + a + b).style.backgroundColor = '#c3c35d';
                        }
                    }
                    else {
                        if (markSquaresMove(a, b)) {
                            promptMode.on = true;
                            promptMode.checker[0] = a;
                            promptMode.checker[1] = b;
                            document.getElementById("" + a + b).style.backgroundColor = '#c3c35d';
                        }
                    }

                }

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

        }

    }

}


function markSquaresMove(a, b) { //функция вовзращает true, если у шашки есть доступные поля для хода и подсвечивает эти поля
    let aPlus = a + 1, aMinus = a - 1, bPlus = b + 1, bMinus = b - 1, canMoveBool = false;

    while (aMinus > -1) {
        if (bMinus > -1) {
            if (board[aMinus][bMinus] == 0) {
                if (board[a][b] != 1) {
                    canMoveBool = true;
                    document.getElementById("" + aMinus + bMinus).style.backgroundColor = '#5dc37e';
                }
            }
        }
        if (bPlus < 8) {
            if (board[aMinus][bPlus] == 0) {
                if (board[a][b] != 2) {
                    canMoveBool = true;
                    document.getElementById("" + aMinus + bPlus).style.backgroundColor = '#5dc37e';
                }
            }

        }

        if (board[a][b] > 2) {
            aMinus--;
            bMinus--;
            bPlus++;
        }
        else {
            aMinus = -2;
        }
    }

    bPlus = b + 1;
    bMinus = b - 1;

    while (aPlus < 8) {
        if (bMinus > -1) {
            if (board[aPlus][bMinus] == 0) {
                if (board[a][b] != 1) {
                    canMoveBool = true;
                    document.getElementById("" + aPlus + bMinus).style.backgroundColor = '#5dc37e';
                }
            }
        }
        if (bPlus < 8) {
            if (board[aPlus][bPlus] == 0) {
                if (board[a][b] != 2) {
                    canMoveBool = true;
                    document.getElementById("" + aPlus + bPlus).style.backgroundColor = '#5dc37e';
                }
            }
        }
        if (board[a][b] > 2) {
            aPlus++;;
            bPlus++;
            bMinus--;
        }
        else {
            aPlus = 9;
        }
    }

    return canMoveBool;
}

function markSquaresCaptureMove(a, b) {
    let aPlus = a + 1, aMinus = a - 1, bPlus = b + 1, bMinus = b - 1;

    while (aMinus > -1) {
        if (bMinus > -1) {
            if (board[aMinus][bMinus] != 0 && (aMinus - 1 > -1 && bMinus - 1 > -1)) {
                if (!equals(aMinus, bMinus, a, b) && board[aMinus - 1][bMinus - 1] == 0) {
                    document.getElementById("" + (aMinus - 1) + (bMinus - 1)).style.backgroundColor = '#c35d5d';
                    if (board[a][b] > 2 && (aMinus - 2 > -1 && bMinus - 2 > -1)) {
                        let i = 2;
                        while (aMinus - i > -1 && bMinus - i > -1) {
                            if (board[aMinus - i][bMinus - i] == 0) {
                                document.getElementById("" + (aMinus - i) + (bMinus - i)).style.backgroundColor = '#c35d5d';
                                i++;
                            }
                            else { break; }
                        }
                    }
                }
                bMinus = -2;
            }
        }
        if (bPlus < 8) {
            if (board[aMinus][bPlus] != 0 && (aMinus - 1 > -1 && bPlus + 1 < 8)) {
                if (!equals(aMinus, bPlus, a, b) && board[aMinus - 1][bPlus + 1] == 0) {
                    document.getElementById("" + (aMinus - 1) + (bPlus + 1)).style.backgroundColor = '#c35d5d';
                    if (board[a][b] > 2 && (bPlus + 2 < 8 && aMinus - 2 > -1)) {
                        let i = 2;
                        while (bPlus + i < 8 && aMinus - i > -1) {
                            if (board[aMinus - i][bPlus + i] == 0) {
                                document.getElementById("" + (aMinus - i) + (bPlus + i)).style.backgroundColor = '#c35d5d';
                                i++;
                            }
                            else { break; }
                        }
                    }
                }
                bPlus = 9;
            }
        }

        if (board[a][b] > 2) {
            aMinus--;
            bMinus--;
            bPlus++;
        }
        else {
            aMinus = -2;
        }
    }

    bPlus = b + 1;
    bMinus = b - 1;

    while (aPlus < 8) {
        if (bMinus > -1) {
            if (board[aPlus][bMinus] != 0 && (aPlus + 1 < 8 && bMinus - 1 > -1)) {
                if (!equals(aPlus, bMinus, a, b) && board[aPlus + 1][bMinus - 1] == 0) {
                    document.getElementById("" + (aPlus + 1) + (bMinus - 1)).style.backgroundColor = '#c35d5d';
                    if (board[a][b] > 2 && (aPlus + 2 < 8 && bMinus - 2 > -1)) {
                        let i = 2;
                        while (aPlus + i < 8 && bMinus - i > -1) {
                            if (board[aPlus + i][bMinus - i] == 0) {
                                document.getElementById("" + (aPlus + i) + (bMinus - i)).style.backgroundColor = '#c35d5d';
                                i++;
                            }
                            else { break; }
                        }
                    }
                }
                bMinus = -2;
            }
        }
        if (bPlus < 8) {
            if (board[aPlus][bPlus] != 0 && (aPlus + 1 < 8 && bPlus + 1 < 8)) {
                if (!equals(aPlus, bPlus, a, b) && board[aPlus + 1][bPlus + 1] == 0) {
                    document.getElementById("" + (aPlus + 1) + (bPlus + 1)).style.backgroundColor = '#c35d5d';
                    if (board[a][b] > 2 && (aPlus + 2 < 8 && bPlus + 2 < 8)) {
                        let i = 2;
                        while (aPlus + i < 8 && bPlus + i < 8) {
                            if (board[aPlus + i][bPlus + i] == 0) {
                                document.getElementById("" + (aPlus + i) + (bPlus + i)).style.backgroundColor = '#c35d5d';
                                i++;
                            }
                            else { break; }

                        }
                    }
                }
                bPlus = 9;
            }
        }
        if (board[a][b] > 2) {
            aPlus++;;
            bPlus++;
            bMinus--;
        }
        else {
            aPlus = 9;
        }
    }
}



function canMoveNoPrompt(a, b) {
    let aPlus = a + 1, aMinus = a - 1, bPlus = b + 1, bMinus = b - 1, localRedFlag = false;

    while (aMinus > -1) {
        if (bMinus > -1) {
            if (board[aMinus][bMinus] != 0 && (aMinus - 1 > -1 && bMinus - 1 > -1)) {
                if (!equals(aMinus, bMinus, a, b) && board[aMinus - 1][bMinus - 1] == 0) {
                    localRedFlag = true;
                }
                bMinus = -2;
            }
        }
        if (bPlus < 8) {
            if (board[aMinus][bPlus] != 0 && (aMinus - 1 > -1 && bPlus + 1 < 8)) {
                if (!equals(aMinus, bPlus, a, b) && board[aMinus - 1][bPlus + 1] == 0) {
                    localRedFlag = true;
                }
                bPlus = 9;
            }
        }
        if (board[a][b] > 2) {
            aMinus--;
            bMinus--;
            bPlus++;
        }
        else {
            aMinus = -2;
        }
    }

    bPlus = b + 1;
    bMinus = b - 1;

    while (aPlus < 8) {
        if (bMinus > -1) {
            if (board[aPlus][bMinus] != 0 && (aPlus + 1 < 8 && bMinus - 1 > -1)) {
                if (!equals(aPlus, bMinus, a, b) && board[aPlus + 1][bMinus - 1] == 0) {
                    localRedFlag = true;
                }
                bMinus = -2;
            }
        }
        if (bPlus < 8) {
            if (board[aPlus][bPlus] != 0 && (aPlus + 1 < 8 && bPlus + 1 < 8)) {
                if (!equals(aPlus, bPlus, a, b) && board[aPlus + 1][bPlus + 1] == 0) {
                    localRedFlag = true;
                }
                bPlus = 9;
            }
        }
        if (board[a][b] > 2) {
            aPlus++;;
            bPlus++;
            bMinus--;
        }
        else {
            aPlus = 9;
        }
    }

    return localRedFlag;
}



function exitModePrompt() {
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
    let i = 1;
    if (a < newA) {
        if (b < newB) {
            while (board[a + i][b + i] == 0) {
                i++;
            }
            document.getElementById("" + (a + i) + (b + i)).textContent = '';
            moveMode.dead[0] = a + i;
            moveMode.dead[1] = b + i;
            moveMode.dead[2] = board[a + i][b + i];
            board[a + i][b + i] = 0;
        }
        else {
            while (board[a + i][b - i] == 0) {
                i++;
            }
            document.getElementById("" + (a + i) + (b - i)).textContent = '';
            moveMode.dead[0] = a + i;
            moveMode.dead[1] = b - i;
            moveMode.dead[2] = board[a + i][b - i];
            board[a + i][b - i] = 0;
        }

    }

    else {
        if (b < newB) {
            while (board[a - i][b + i] == 0) {
                i++;
            }
            document.getElementById("" + (a - i) + (b + i)).textContent = '';
            moveMode.dead[0] = a - i;
            moveMode.dead[1] = b + i;
            moveMode.dead[2] = board[a - i][b + i];
            board[a - i][b + i] = 0;
        }
        else {
            while (board[a - i][b - i] == 0) {
                i++;
            }
            document.getElementById("" + (a - i) + (b - i)).textContent = '';
            moveMode.dead[0] = a - i;
            moveMode.dead[1] = b - i;
            moveMode.dead[2] = board[a - i][b - i];
            board[a - i][b - i] = 0;
        }

    }

    makeMove(a, b, newA, newB);
}

function move(a, b, color) {
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


function equals(a, b, a2, b2) { //проверяет что шашки одинокового цвета
    if (board[a][b] == 1 || board[a][b] == 3) {
        if (board[a2][b2] == 1 || board[a2][b2] == 3) { return true; }
    }
    else if (board[a2][b2] == 2 || board[a2][b2] == 4) { return true; }
    return false;
}


function show(mode) {
    exitModePrompt();
    hiddenMoveMode();
    moveMode.on = false;
    promptMode.on = false;
    normalMode.oneMoveFlag = false;
    if (mode == 1) {
        for (let i = 0; i < 8; i++) {
            for (let j = 0; j < 8; j++) {
                board[i][j] = boardExample[i][j];
            }
        }
        normalMode.nextMove = 1;
        normalMode.num = 1;
        text = "1."
        whiteMode();
    }
    else {
        for (let i = 0; i < 8; i++) {
            for (let j = 0; j < 8; j++) {
                board[i][j] = boardExample1[i][j];
            }
        }
        normalMode.nextMove = 2;
        normalMode.num = 1;
        text = "1.x-x "
        blackMode();
    }
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (board[i][j] == 0) {
                document.getElementById("" + i + j).textContent = '';
            }
            else if (board[i][j] == 1) {
                document.getElementById("" + i + j).textContent = '⚪';
            }
            else if (board[i][j] == 2) {
                document.getElementById("" + i + j).textContent = '⚫';

            }
            else if (board[i][j] == 3) {
                document.getElementById("" + i + j).textContent = '🤍';
            }
            else {
                document.getElementById("" + i + j).textContent = '🖤';

            }
        }
    }
    checkRed();
    let div = document.getElementById("p1");
    let removed = div.removeChild(div.firstChild);
    while (removed != null) {
        removed = div.removeChild(div.firstChild);
    }
}




function checkRed() { //обновляет список шашек, которые должны побить в этом ходе
    normalMode.redFlag = false;
    normalMode.checkerKill = [];

    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (board[i][j] == normalMode.nextMove || board[i][j] == normalMode.nextMove + 2) {
                if (canMoveNoPrompt(i, j)) {
                    normalMode.redFlag = true;
                    normalMode.checkerKill.push("" + i + j);
                }
            }
        }
    }

}

function confirmMove() {
    exitModePrompt();
    if (moveMode.red && canMoveNoPrompt(moveMode.to[0], moveMode.to[1])) {
        normalMode.oneMoveFlag = true;
        addMove();
        normalMode.checkerKill = [];
        normalMode.redFlag = true;
        normalMode.checkerKill.push("" + moveMode.to[0] + moveMode.to[1]);
        markSquaresCaptureMove(moveMode.to[0], moveMode.to[1]);
        promptMode.on = true;
        promptMode.checker[0] = moveMode.to[0];
        promptMode.checker[1] = moveMode.to[1];
        document.getElementById("" + moveMode.to[0] + moveMode.to[1]).style.backgroundColor = '#c3c35d';
    }
    else {
        normalMode.oneMoveFlag = false;
        addMove();
        promptMode.on = false;
        if (normalMode.nextMove == 1) {
            normalMode.nextMove = 2;
            blackMode();
        }
        else {
            normalMode.nextMove = 1;
            whiteMode();
        }
        checkRed();
    }

    hiddenMoveMode();
    moveMode.on = false;

    if (!board.toString().includes(1) && !board.toString().includes(3)) {
        addText("Белые сдались");
    }
    else if (!board.toString().includes(2) && !board.toString().includes(4)) {
        if (normalMode.nextMove == 2) { addText(text); }
        addText("Черные сдались");
    }
}

function backMove() {
    if (moveMode.red) {
        move(moveMode.dead[0], moveMode.dead[1], moveMode.dead[2]);
    }
    move(moveMode.from[0], moveMode.from[1], moveMode.from[2]);
    board[moveMode.to[0]][moveMode.to[1]] = 0;
    document.getElementById("" + moveMode.to[0] + moveMode.to[1]).textContent = '';
    hiddenMoveMode();
    moveMode.on = false;

}

function hiddenMoveMode() {
    document.getElementById("c1").style.visibility = "hidden";
    document.getElementById("c2").style.visibility = "hidden";
}

function visibleMoveMode() {
    document.getElementById("c1").style.visibility = "visible";
    document.getElementById("c2").style.visibility = "visible";
}

function whiteMode() {
    document.getElementById("colorMove").textContent = 'ход ⚪';
}

function blackMode() {
    document.getElementById("colorMove").textContent = 'ход ⚫';

}

function addMove() {
    if (normalMode.oneMoveFlag) {
        text += "" + latterOfNum(moveMode.from[0]) + (moveMode.from[1] + 1);
        text += ":"
    }
    else {
        if (normalMode.nextMove == 1 || normalMode.nextMove == 1) {
            text += "" + latterOfNum(moveMode.from[0]) + (moveMode.from[1] + 1);
            if (moveMode.red) {
                text += ":"
            }
            else {
                text += "-"
            }
            text += "" + latterOfNum(moveMode.to[0]) + (moveMode.to[1] + 1) + " ";
        }
        else {
            text += "" + latterOfNum(moveMode.from[0]) + (moveMode.from[1] + 1);
            if (moveMode.red) {
                text += ":"
            }
            else {
                text += "-"
            }
            text += "" + latterOfNum(moveMode.to[0]) + (moveMode.to[1] + 1);
            addText(text);
            normalMode.num++;
            text = normalMode.num + "."
        }
    }
}

function addText(t) {
    let p = document.createElement("p");
    let t1 = document.createTextNode(t);
    p.appendChild(t1);
    document.getElementById("p1").appendChild(p);
}

function latterOfNum(x) {
    if (x == 0) {
        return 'a';
    }
    else if (x == 1) {
        return 'b';
    }
    else if (x == 2) {
        return 'c';
    }
    else if (x == 3) {
        return 'd';
    }
    else if (x == 4) {
        return 'e';
    }
    else if (x == 5) {
        return 'f';
    }
    else if (x == 6) {
        return 'g';
    }
    else if (x == 7) {
        return 'h';
    }
}


