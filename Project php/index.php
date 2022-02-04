<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/css/style.css">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<title>Вентиляционные системы</title>
</head>
<body>
    <?php include('templates/header.php'); ?>

    <div class="main-image">
    <p class = "main-header" align="right" >ВЕНТИЛЯЦИОННЫЕ</p>
    <p class = "main-header" align="right" >ИНЖЕНЕРНЫЕ</p>
    <p class = "main-header" align="right" >СИСТЕМЫ</p>
    <div class = "windowRequest" align="right" >
                <form action="check.php" method="post">
                <button type="submit" name="send" class="request" >Оставить заявку</button><br>
                <input type="text" name="nameUser" placeholder="Ваше имя*" class="formName" required><br>
                <input type="email" name="email" placeholder="Email*" class="formEmail" required><br>
                <textarea name="message" placeholder="Введите ваше сообщение*" class="formMessage" required  minlength=8"></textarea><br>
                </form>
    </div>
    </div> 


   <div class="main-image3" align="center" >
    <p class = "main-text-header2"  align="center" > МЫ РАБОТАЕМ С ЛЮБЫМ ТИПОМ ПОМЕЩЕНИЙ! </p>
                <img src="images/appart.jpg" />
		<img src="images/homes.jpg" />
		<img src="images/restoraunt.jpg" /><br>
                <img src="images/making.jpg" />
		<img src="images/office.jpg" />
		<img src="images/school.jpg" />
    </div> 


    <div class="blue-block">
    <p class = "main-text-header" align="center" > ПОЧЕМУ ВЫБИРАЮТ НАС?</p>

    <div class="main-image2">
		<img src="images/four.jpg" /><br>
    </div> 
    <table>
               <tr> <td><img src="images/achievement.svg" /></td>
                <td><p class="text-block">Гарантия качества</p> </td> </tr>
               <tr> <td><img src="images/coins.svg" /></td>
                <td><p class="text-block">Цены от производителя</p></td>  </tr>
               <tr> <td><img src="images/graduation.svg" /></td>
                <td><p class="text-block">Квалифицированные работники</p></td> </tr>
    </table>
    </div> 


    <div class="main-block1">
    <br><p class = "main-text-header2" align="center" > ЗАЧЕМ НУЖНА ВЕНТИЛЯЦИЯ?</p>
        <div class="main-text"><p>Если процесс дыхания – это удаление углекислого газа из организма в помещение, то процесс вентиляции – это удаление углекислого газа из помещения на улицу. Поэтому для хорошего самочувствия очень важно иметь хорошую вентиляцию и регулярно проветривать помещения, особенно в школах, офисах и детских садах. Ведь чем больше людей в помещении, тем активнее идет выделение углекислого газа. Это естественный физиологический процесс. При хорошей вентиляции помещения помимо углекислого газа из него уходят бактерии и вирусы, которые могут вызывать массовые заболевания.  
                </p><br><br>
                <p>Свежий воздух в помещении – это основа хорошего самочувствия. А наличие свежего воздуха в помещении зависит от качества вентиляции.</p><br><br>
                <p>Потребность в хорошей вентиляции очевидна. Почему в Европе в до сих пор так плохо организована вентиляция? Одной из причин этого является широко распространенное мнение, будто хорошая вентиляция стоит дорого. На самом деле все обстоит по-другому: хорошая вентиляция как раз способствует снижению расходов. Простота монтажа и эксплуатации, утилизация тепла и регулирование расхода воздуха в соответствии с реальной потребностью – вот три основных фактора, благодаря которым снижаются затраты на электроэнергию. К сожалению, объем средств, выделяемых на строительство новых и реконструкцию старых задний, невелик. Будущие владельцы хотят сэкономить, забывая, что при этом наносится вред внутренней среде помещений. Немногие осознают, что стандартные требования к качеству воздуха в помещении занижены. Еще реже предпринимаются какие-либо меры по улучшению микроклимата в помещении, кроме соблюдения стандартных требований. Конечно, иногда и при таком подходе м                ожет быть достигнуто приемлемое                    качество воздуха, но с очень малым запасом. Например, если в конференц-зале, рассчитанном на десять человек, вдруг окажется пятнадцать человек (весьма распространенный случай), то качество воздуха в нем немедленно ухудшится. Пятнадцать человек будут испытывать дискомфорт, их работоспособность резко упадает.</p>
                </p>
        </div> 
    <br><br>
    </div> 

	<?php include('templates/footer.php'); ?>
</body>
</html>