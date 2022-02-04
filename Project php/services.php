<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/css/style.css">
<script src="//web-ptica.ru/VRV-files/fancybox3/script-zvonka.js"></script>
<link rel="stylesheet" href="/css/style_s.css">
<title> Наши услуги </title>
</head>
<body>
    <?php include('templates/header.php'); ?>
    
    <div class="blue-block">
   <div class = "blocks-service" >
                        <img src="images/kond.jpg" />
                        <h6> Кондиционеры </h6>
                        <p> . . . . . . . . . . . . . . . . . . . . . .  . . . . . . . . . . . . . . . . от 12.990 </p>
   </div>
   
   
   
   <div class = "blocks-service" >
                        <img src="images/ustanov.png" />
                        <h6> Установка </h6>
                        <p> . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .  . . . . . . . . . . от 6.990 </p>
   </div>

   
   <div class = "blocks-service" >
                        <img src="images/vent.jpg" />
                        <h6> Вентиляция </h6>
                        <p> . . . . . . . . . . . . . </p>
                        <a href="#modal" class="button openPrice">Узнать_индивидуальную_стоимость</a> 

   </div>
   
   
   <div class = "blocks-service" >
                        <img src="images/proe.jpg" />
                        <h6> Проектирование - бесплатно, при установке вентиляции!  </h6>
   </div>
   <br><br>
   </div>
   
   <div id="modal" class="modal">
    <div>
       <div class="text" align="center">
           <div id="blok_tel">
               <form action="check_contacts.php" method="post">
                   <h>Заказать обратный звонок:</h> <br>
                <input type="text" name="nameUser" placeholder="Ваше имя" class="formNameC" required><br>
                <input type="tel" pattern="+7-[0-9]{11}" name="number" placeholder="+7 xxx xxx xx xx" class="formNumber" required><br>
                <button type="submit" name="send" class="call" >Перезвоните мне!</button>
                </form>
            </div>
        </div>
        <a href="#close" title="Закрыть">Закрыть</a>
    </div>        
</div>

   <?php include('templates/footer.php'); ?>


</body>
</html>