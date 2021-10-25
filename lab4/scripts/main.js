var board;

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
[0, 0, 0, 1, 0, 2, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 1, 0, 0, 0, 0],
];

var promptMode = { //режим подсказки
    "on": false,
    "checker": [0, 0],
    "nextMove": 1
};


function whatIsIt(a, b) {
    if (!promptMode.on) {
        if (board[a][b] != 0) {
            if (board[a][b] == promptMode.nextMove || board[a][b] == promptMode.nextMove + 2) {
                if (canMove(a, b)) {
                    promptMode.on = true;
                    promptMode.checker[0] = a;
                    promptMode.checker[1] = b;
                    document.getElementById("" + a + b).style.backgroundColor = '#c3c35d';
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
            exitModePrompt();
            promptMode.on = false;
            if (promptMode.nextMove == 1) {
                promptMode.nextMove = 2;
            }
            else {
                promptMode.nextMove = 1;
            }
            makeMove(promptMode.checker[0], promptMode.checker[1], a, b);
        }

        else if (backgroundColor == "rgb(195, 93, 93)") { //красный
            exitModePrompt();
            promptMode.on = false;
            if (promptMode.nextMove == 1) {
                promptMode.nextMove = 2;
            }
            else {
                promptMode.nextMove = 1;
            }
            makeRedMove(promptMode.checker[0], promptMode.checker[1], a, b);
        }

    }


}


function canMove(a, b) { //функция вовзращает true, если у шашки есть доступные поля для хода и подсвечивает эти поля
    let aPlus = a + 1;
    let aMinus = a - 1;
    let bPlus = b + 1;
    let bMinus = b - 1;
    let redFlag = false;
    let canMoveBool = false;

    while (aMinus > -1) {
        if (bMinus > -1) {
            if (board[aMinus][bMinus] == 0) {
                if (board[a][b] != 1) {
                    canMoveBool = true;
                    document.getElementById("" + aMinus + bMinus).style.backgroundColor = '#5dc37e';
                }
            }
            else if (aMinus - 1 > -1 && bMinus - 1 > -1) {
                if (!equals(aMinus, bMinus, a, b) && board[aMinus - 1][bMinus - 1] == 0) {
                    canMoveBool = true;
                    redFlag = true;
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
            if (board[aMinus][bPlus] == 0) {
                if (board[a][b] != 2) {
                    canMoveBool = true;
                    document.getElementById("" + aMinus + bPlus).style.backgroundColor = '#5dc37e';
                }
            }
            else if (aMinus - 1 > -1 && bPlus + 1 < 8) {
                if (!equals(aMinus, bPlus, a, b) && board[aMinus - 1][bPlus + 1] == 0) {
                    canMoveBool = true;
                    redFlag = true;
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
            if (board[aPlus][bMinus] == 0) {
                if (board[a][b] != 1) {
                    canMoveBool = true;
                    document.getElementById("" + aPlus + bMinus).style.backgroundColor = '#5dc37e';
                }
            }
            else if (aPlus + 1 < 8 && bMinus - 1 > -1) {
                if (!equals(aPlus, bMinus, a, b) && board[aPlus + 1][bMinus - 1] == 0) {
                    canMoveBool = true;
                    redFlag = true;
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
            if (board[aPlus][bPlus] == 0) {
                if (board[a][b] != 2) {
                    canMoveBool = true;
                    document.getElementById("" + aPlus + bPlus).style.backgroundColor = '#5dc37e';
                }
            }
            else if (aPlus + 1 < 8 && bPlus + 1 < 8) {
                if (!equals(aPlus, bPlus, a, b) && board[aPlus + 1][bPlus + 1] == 0) {
                    canMoveBool = true;
                    redFlag = true;
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

    if (redFlag) {
        onlyRedMove();
    }

    return canMoveBool;
}


function exitModePrompt() {
    const darks = document.querySelectorAll('.dark');
    for (let i = 0; i < darks.length; i++) {
        darks[i].style.backgroundColor = '#4D4847';
    }
}

function onlyRedMove() { //оставляет доступными для хода только красные поля
    const darks = document.querySelectorAll('.dark');
    for (let i = 0; i < darks.length; i++) {
        if (darks[i].style.backgroundColor == "rgb(93, 195, 126)") {
            darks[i].style.backgroundColor = '#4D4847';
        }
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
            board[a + i][b + i] = 0;
        }
        else {
            while (board[a + i][b - i] == 0) {
                i++;
            }
            document.getElementById( "" + (a + i) + (b - i)).textContent = '';
            board[a + i][b - i] = 0;
        }

    }

    else {
        if (b < newB) {
            while (board[a - i][b + i] == 0) {
                i++;
            }
            document.getElementById("" + (a - i) + (b + i)).textContent = '';
            board[a - i][b + i] = 0;
        }
        else {
            while (board[a - i][b - i] == 0) {
                i++;
            }
            document.getElementById( "" + (a - i) + (b - i)).textContent = '';
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


function showExample() {
    board = [];
    board = boardExample1;
    promptMode.nextMove = 1;
    promptMode.on = false;
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (boardExample1[i][j] == 0) {
                document.getElementById("" + i + j).textContent = '';
            }
            else if (boardExample1[i][j] == 1) {
                document.getElementById("" + i + j).textContent = '⚪';
            }
            else if (boardExample1[i][j] == 2) {
                document.getElementById("" + i + j).textContent = '⚫';

            }
            else if (boardExample1[i][j] == 3) {
                document.getElementById("" + i + j).textContent = '🤍';
            }
            else {
                document.getElementById("" + i + j).textContent = '🖤';

            }
        }
    }
}

function show() {
    board = [];
    board = boardExample;
    promptMode.nextMove = 1;
    promptMode.on = false;
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if (board[i][j] == 0) {
                document.getElementById("" + i + j).textContent = '';
            }
            else if (board[i][j] == 1) {
                document.getElementById("" + i + j).textContent = '⚪';
            }
            else {
                document.getElementById("" + i + j).textContent = '⚫';

            }
        }
    }
}


